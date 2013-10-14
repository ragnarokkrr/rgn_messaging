package org.rgn.jms.richards.ch04.p2p;

import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.MapMessage;
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

public class QBorrower {

	private static final int _30_SECS = 30000;
	private QueueConnection qConnect;
	private QueueSession qSession;
	private Queue requestQ;
	private Queue responseQ;

	public QBorrower(String queuecf, String requestQueue, String responseQueue) {

		try {
			// Connect to the provider and the JMS connection
			Context ctx = new InitialContext();
			QueueConnectionFactory qFactory = (QueueConnectionFactory) ctx
					.lookup(queuecf);
			qConnect = qFactory.createQueueConnection();

			// Create the JMS Session
			qSession = qConnect.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// Lookup the request and response queues
			requestQ = (Queue) ctx.lookup(requestQueue);
			responseQ = (Queue) ctx.lookup(responseQueue);

			// Now that setup is complete, start the Connection
			qConnect.start();

		} catch (NamingException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (JMSException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	private void sendLoanRequest(double salary, double loanAmt) {
		try {
			// Create JMS message
			MapMessage msg = qSession.createMapMessage();
			msg.setDouble("Salary", salary);
			msg.setDouble("LoanAmount", loanAmt);
			msg.setJMSReplyTo(responseQ);
			
			// Create the sender and send the message
			QueueSender qSender = qSession.createSender(requestQ);
			qSender.send(msg);
			
			// Wait to see if the loan request was accepted or declined
			String filter =
				String.format("JMSCorrelationID = '%s'"
					, msg.getJMSMessageID());	
			QueueReceiver qReceiver = 
					qSession.createReceiver(responseQ, filter);
			TextMessage tmsg = (TextMessage) qReceiver.receive(_30_SECS);
			
			if(tmsg == null){
				System.out.println("QLender not responding");
			}else{
				System.out.println("Loan request was " + tmsg.getText());
			}
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
	
	
	protected void requestLoan(){
		Scanner stdin = new Scanner(System.in);
		System.out.println("QBorrower Application Started");
		System.out.println("Press enter to quite application");
		System.out.println("Enter: Salary, Loan_Amount");
		System.out.println("\ne.g. 50000, 120000");
		
		while(true){
			System.out.print("> ");
			String loanRequest = stdin.nextLine();
			if(loanRequest == null || 
					loanRequest.trim().length() <=0){
				exit();
			}
			//Parse the deal description
			double salary = Double.valueOf(loanRequest.split(",")[0].trim());
			double loanAmt = Double.valueOf(loanRequest.split(",")[1].trim());
			
			sendLoanRequest(salary, loanAmt);
		}
	}
}
