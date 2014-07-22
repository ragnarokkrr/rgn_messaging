package org.rgn.jms.richards.ch09.spring;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.MessagePostProcessor;

public class SimpleJmsSender {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"app-context.xml");
 
		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		Destination queue = (Destination) ctx.getBean("queue1Dest");
		
		
		MessageCreator mc = new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				TextMessage msg = session.createTextMessage();
				msg.setJMSPriority(9);
				msg.setText("This is easy!!");

				return msg;
			}
		};

		jmsTemplate.send(mc);

		jmsTemplate.convertAndSend("This is even easier!");

		MessagePostProcessor postProcessor = new MessagePostProcessor() {
			public Message postProcessMessage(Message message)
					throws JMSException {
				message.setJMSPriority(9);
				return message;
			}
		};
		jmsTemplate.convertAndSend((Object) "This is even easier on PostProcessor!",
				postProcessor);

		
		jmsTemplate.send("queue1", new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage();
				
				message.setJMSPriority(9);
				message.setText("Easy like a sunday morning");
				
				return message;
			}
		});
		

		jmsTemplate.send(queue, new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage();
				
				message.setJMSPriority(9);
				message.setText("Easy like a sunday noon");
				
				return message;
			}
		});

		
		
		System.out.println("My Only friend, the end");

	}

}
