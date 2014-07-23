package org.rgn.messaging.poc.reqrepl;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.jms.Queue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.ProducerCallback;

public class SimpleJmsSender {

	public static void main(String[] args) throws JMSException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"app-context.xml");

		JmsTemplate jmsTemplate = (JmsTemplate) ctx.getBean("jmsTemplate");
		final Destination destQueue = (Destination) ctx.getBean("queue1Dest");

		final String messageObject = "Estringue de req";

		TextMessage reply = jmsTemplate.execute(destQueue, new ProducerCallback<TextMessage>() {

			public TextMessage doInJms(Session session, MessageProducer producer)
					throws JMSException {
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				
				TemporaryQueue tempQueue = session.createTemporaryQueue();
				
				System.out.println("Dest QUEUE=> " + ((Queue)destQueue).getQueueName());
				System.out.println("NEW tempQUEUE=> " + tempQueue.getQueueName());

				TextMessage message = session
						.createTextMessage(messageObject);
				message.setJMSReplyTo(tempQueue);


				producer.send(message);
				
				//String correlationId = UUID.randomUUID().toString();
				//message.getJMSMessageID(); setado apenas apos o "send()"
				String correlationId = message.getJMSMessageID();
				// mandamos para a destination default configurada para o
				// JmsTemplate
				//message.setJMSCorrelationID(correlationId);
				
				System.out.println("New msg ID: " + correlationId); 

				MessageConsumer consumer = session.createConsumer(tempQueue,
						String.format("JMSCorrelationID = '%s'", correlationId));
				
				
				long receiveTimeout = 10000;
				TextMessage consumedMessage = (TextMessage)consumer.receive(receiveTimeout);
				
				consumer.close();
				tempQueue.delete();
				return consumedMessage;
			}

//			private MessageConsumer createMessageConsumer(Session session,
//					String correlationId, TemporaryQueue queue)
//					throws JMSException {
//				return session.createConsumer(queue,
//						String.format("JMSCorrelationID = '%s'", correlationId));
//			}
		});
		
		
		System.out.println("REPLY===> " + reply.getText());

	}

}
