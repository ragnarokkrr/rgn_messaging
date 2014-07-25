package org.rgn.messaging.poc.reqrepl;

import java.io.Serializable;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TemporaryQueue;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.ProducerCallback;

public class ClientJmstRequestResponseInterceptor implements MethodInterceptor {

	private JmsTemplate jmsTemplate;
	private Queue destQueue;

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setDestQueue(Queue destQueue) {
		this.destQueue = destQueue;
	}

	public ClientJmstRequestResponseInterceptor() {

	}

	public ClientJmstRequestResponseInterceptor(JmsTemplate jmsTemplate,
			Queue destQueue) {
		super();
		this.jmsTemplate = jmsTemplate;
		this.destQueue = destQueue;
	}

	@SuppressWarnings("unchecked")
	public Object invoke(MethodInvocation invocation) throws Throwable {

		final Serializable arg = (Serializable) invocation.getArguments()[0];

		Object response = jmsTemplate.execute(destQueue,
				new ProducerCallback<Object>() {

					public Object doInJms(Session session,
							MessageProducer producer) throws JMSException {

						producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

						TemporaryQueue tempQueue = session
								.createTemporaryQueue();

						System.out.println("Dest QUEUE2=> "
								+ ((Queue) destQueue).getQueueName());
						System.out.println("NEW tempQUEUE2 => "
								+ tempQueue.getQueueName());

						ObjectMessage message = session
								.createObjectMessage(arg);
						message.setJMSReplyTo(tempQueue);

						producer.send(message);

						message.setJMSReplyTo(tempQueue);

						producer.send(message);

						MessageConsumer consumer = session
								.createConsumer(tempQueue);

						long receiveTimeout = 10000;
						ObjectMessage consumedMessage = (ObjectMessage) consumer
								.receive(receiveTimeout);

						consumer.close();
						tempQueue.delete();

						return consumedMessage.getObject();
					}
				});

		return response;
	}

}
