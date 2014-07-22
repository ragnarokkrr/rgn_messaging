package org.rgn.jms.richards.ch09.spring;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;

public class SpringMessageListener implements
		SessionAwareMessageListener<TextMessage> {

	public void onMessage(TextMessage msg, Session session) throws JMSException {
		String text = msg.getText();
		System.out.println(" SpringMessageListener text=> " + text);

		MessageProducer sender = session.createProducer(msg.getJMSReplyTo());

		TextMessage resMsg = session.createTextMessage();
		resMsg.setJMSCorrelationID(msg.getJMSCorrelationID());
		resMsg.setText("Message " + msg.getJMSCorrelationID() + " received");

		sender.send(resMsg);

	}

}
