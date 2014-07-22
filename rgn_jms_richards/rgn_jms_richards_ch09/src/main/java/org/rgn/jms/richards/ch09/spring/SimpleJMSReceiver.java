package org.rgn.jms.richards.ch09.spring;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SimpleJMSReceiver implements MessageListener {

	public void onMessage(Message msg) {
		try {
			if (msg instanceof TextMessage) {
				System.out.println("SimpleJMSReceiver =>" + ((TextMessage) msg).getText());
			} else {
				System.out.println("Message not supported");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}

	}

}
