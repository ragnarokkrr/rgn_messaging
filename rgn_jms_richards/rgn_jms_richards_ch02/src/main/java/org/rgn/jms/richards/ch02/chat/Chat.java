package org.rgn.jms.richards.ch02.chat;

import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;

import org.apache.activemq.broker.PublishedAddressPolicy;

public class Chat implements javax.jms.MessageListener {

	private TopicConnection connection;
	private TopicSession pubSession;
	private TopicPublisher publisher;
	private TopicSubscriber subscriber;
	private String username;

	public Chat(String topicFactory, String topicName, String username)
			throws Exception {
		// JNDI initial context from jndi.properties
		InitialContext ctx = new InitialContext();

		// lookup connection factory and create the connection
		TopicConnectionFactory conFactory = (TopicConnectionFactory) ctx
				.lookup(topicFactory);

		TopicConnection connection = conFactory.createTopicConnection();

		// Create two JMS sessions
		TopicSession pubSession = connection.createTopicSession(false,
				Session.AUTO_ACKNOWLEDGE);
		TopicSession subSession = connection.createTopicSession(false,
				Session.AUTO_ACKNOWLEDGE);

		// Lookup a JMS topic
		Topic chatTopic = (Topic) ctx.lookup(topicName);

		// Create a JMS publisher and subscriber. The additional parameters
		// on the createSubbscriber are a message selector (null) and a true
		// value for the noLocal flag indicating that messages produced from
		// from this publisher should not be consumed by this publisher
		TopicPublisher publisher = pubSession.createPublisher(chatTopic);
		TopicSubscriber subscriber = subSession.createSubscriber(chatTopic,
				null, true);

		// Set a JMS message listener
		subscriber.setMessageListener(this);

		// initialize application variables
		this.connection = connection;
		this.pubSession = pubSession;
		this.publisher = publisher;
		this.subscriber = subscriber;
		this.username = username;

		// Start JMS connection; allows messages to be delivered
		connection.start();
	}

	/*
	 * Receives messages from Topic Subscriber
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			System.out.println("\treceived> " + textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create and send message using publisher
	 * 
	 * @param text
	 * @throws JMSException
	 */
	protected void writeMessage(String text) throws JMSException {
		TextMessage message = pubSession.createTextMessage();
		message.setText(username + ": " + text);
		publisher.publish(message);
	}

	/**
	 * close the JMS connection
	 * 
	 * @throws JMSException
	 */
	public void close() throws JMSException {
		connection.close();
	}

	

	public void performChat() throws JMSException {
		Scanner reader  = new Scanner(System.in);
		
		while(true){
			System.out.print("type message:" );
			String s = reader.nextLine();
			if("exit".equalsIgnoreCase(s)){
				close();
				reader.close();
				System.exit(0);
			}else{
				writeMessage(s);
			}
		}
	}

}
