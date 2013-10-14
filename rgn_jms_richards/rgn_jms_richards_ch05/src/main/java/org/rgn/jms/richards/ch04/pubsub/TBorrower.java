package org.rgn.jms.richards.ch04.pubsub;

import java.util.Scanner;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TBorrower implements MessageListener {

	private Double currentRate;
	private TopicConnection tConnect;
	private TopicSession tSession;
	private Topic topic;

	public TBorrower(String topiccf, String topicName, String rate) {
		try {
			currentRate = Double.valueOf(rate);

			// Connect to the provider and get the JMS connection
			Context ctx = new InitialContext();

			TopicConnectionFactory tFactory = 
					(TopicConnectionFactory) ctx.lookup(topiccf);
		
			tConnect = tFactory.createTopicConnection();
			
			// Create JMS Session
			tSession = tConnect.createTopicSession(false
					, Session.AUTO_ACKNOWLEDGE);
			
			//lookup the request and response queues
			topic = (Topic)ctx.lookup(topicName);
			
			// Create the message listener
			TopicSubscriber subscriber = tSession.createSubscriber(topic);
			subscriber.setMessageListener(this);
			
			// Now that setup is complete, start the Connection
			tConnect.start();
			
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
			//Get the data from the message
			BytesMessage msg = (BytesMessage) message;
			double newRate = msg.readDouble();
			
			// If the rate is at least 1 point lower than the current rate, then
			// recommend refinancing
			if((currentRate - newRate) >= 1.0){
				System.out.printf("New rate = %.2f - Consider refinancing loan\n"
						, newRate);
			}else{
				System.out.printf("New rate =%.2f - keep existing loan\n"
						, newRate);
			}
			
			System.out.println("\nwaiting for rate updates...");
		} catch (JMSException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	protected void exit(){
		try {
			tConnect.close();
		} catch (JMSException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	protected void performRateEval(){
		Scanner stdin = new Scanner(System.in);
		
		System.out.println("TBorrower Application started");
		System.out.println("Press enter to quit application");
		stdin.nextLine();
		stdin.close();
		exit();
	}

}
