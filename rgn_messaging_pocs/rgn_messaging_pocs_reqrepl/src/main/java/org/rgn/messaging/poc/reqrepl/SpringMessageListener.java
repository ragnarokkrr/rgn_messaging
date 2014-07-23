package org.rgn.messaging.poc.reqrepl;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;

public class SpringMessageListener implements
		SessionAwareMessageListener<TextMessage> {

	public void onMessage(TextMessage msg, Session session) throws JMSException {
		String text = msg.getText();
		String correlation = msg.getJMSMessageID();
		System.out.println(" SpringMessageListener text=> " + text);
		System.out.println(" ReplyQueue: "
				+ ((Queue) msg.getJMSReplyTo()).getQueueName());
		String reply = String.format("Reply ---> (correlation:'%s', msg:'%s')",
				correlation, text);

		MessageProducer sender = session.createProducer(msg.getJMSReplyTo());

		TextMessage resMsg = session.createTextMessage();
		resMsg.setJMSCorrelationID(msg.getJMSMessageID());
		resMsg.setText(reply);

		sender.send(resMsg);

	}

}
