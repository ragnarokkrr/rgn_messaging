package org.rgn.jms.richards.ch04.pubsub;

import java.util.Scanner;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TLender {

	private TopicConnection tConnect;
	private TopicSession tSession;
	private Topic topic;

	public TLender(String topiccf, String topicName) {

		try {
			// Connect to the provider and get the JMS Connection
			Context ctx = new InitialContext();

			TopicConnectionFactory tFactory = (TopicConnectionFactory) ctx
					.lookup(topiccf);

			tConnect = tFactory.createTopicConnection();

			// Create the JMS Session
			tSession = tConnect.createTopicSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// lookup the request and response queues
			topic = (Topic) ctx.lookup(topicName);

			// Now that setup is complete, start the Connection
			tConnect.start();

		} catch (NamingException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (JMSException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	protected void publishRate(double newRate) {

		try {
			// Create JMS message
			BytesMessage msg = tSession.createBytesMessage();
			msg.writeDouble(newRate);
			
			// Create the publisher and publish the message
			TopicPublisher publisher = tSession.createPublisher(topic);
			publisher.publish(msg);
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
			System.exit(1);
		}
	}
	
	protected void perform(){
		Scanner stdin = new Scanner(System.in);
		
		System.out.println("TLender Application Started");
		System.out.println("Press enter to quit application");
		System.out.println("Enter: Rate");
		System.out.println("\ne.g. 6.8");
		
		while(true){
			System.out.print("> ");
			String rate = stdin.nextLine();
			if(rate == null ||
					rate.trim().length() <= 0){
				stdin.close();
				exit();
			}
			
			//Parse the deal description
			double newRate = Double.valueOf(rate);
			publishRate(newRate);
		}
	}
}
