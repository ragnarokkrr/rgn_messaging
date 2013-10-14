package org.rgn.jms.richards.ch04.p2p;

import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QLender implements MessageListener {

	private QueueConnection qConnect;
	private QueueSession qSession;
	private Queue requestQ;

	public QLender(String queuecf, String requestQueue) {
		try {
			// Connect to the provider and get the JMS connection
			Context ctx = new InitialContext();
			QueueConnectionFactory qFactory = (QueueConnectionFactory) ctx
					.lookup(queuecf);

			qConnect = qFactory.createQueueConnection();

			// Create the JMS Session
			qSession = qConnect.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// Lookup the request queue
			requestQ = (Queue) ctx.lookup(requestQueue);

			// Now that setup is complete, start the Connection
			qConnect.start();

			// Create the Message Listener
			QueueReceiver qReceiver = qSession.createReceiver(requestQ);
			qReceiver.setMessageListener(this);

			System.out.println("Waiting for loan requests...");
		} catch (NamingException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (JMSException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void onMessage(Message message) {
		try {

			boolean accepted = false;

			// Get the data from message
			MapMessage msg = (MapMessage) message;
			double salary = msg.getDouble("Salary");
			double loanAmt = msg.getDouble("LoanAmount");
			
			// Determine whether to accept or decline the loan
			if(loanAmt < 200000){
				accepted = (salary / loanAmt) > .25;
			}else{
				accepted = (salary / loanAmt) > .33;
			}
			
			System.out.printf("Percent = %.2f, loan is %s\n"
					, salary / loanAmt
					, accepted ? "Accepted" : "Declined");
			
			// Send the results back to the borrower
			TextMessage responseMsg = qSession.createTextMessage();
			responseMsg.setText(accepted ? "Accepted" : "Declined");
			responseMsg.setJMSCorrelationID(message.getJMSMessageID());
			
			// Create the sender and send the message
			QueueSender qSender = 
				qSession.createSender((Queue)message.getJMSReplyTo());
			
			qSender.send(responseMsg);
			
			System.out.println("\nWaiting for loan requests...");
			
		} catch (JMSException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void exit(){
		try {
			qConnect.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	protected void perform(){
		Scanner stdin = new Scanner(System.in);
		System.out.println("QLender application started");
		System.out.println("Press enter to quit application");
		stdin.nextLine();
		exit();
	}
	
}
