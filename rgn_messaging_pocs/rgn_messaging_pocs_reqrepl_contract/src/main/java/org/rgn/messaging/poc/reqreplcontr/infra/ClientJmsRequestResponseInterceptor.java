package org.rgn.messaging.poc.reqreplcontr.infra;

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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.ProducerCallback;

/**
 * @author nilseu.padilha
 *
 */
public class ClientJmsRequestResponseInterceptor implements MethodInterceptor,
		InitializingBean {

	private JmsTemplate jmsTemplate;

	private Queue destQueue;

	@SuppressWarnings("rawtypes")
	private Class[] proxyInterfaces;

	@SuppressWarnings("rawtypes")
	public void setProxyInterfaces(Class[] proxyInterfaces) {
		this.proxyInterfaces = proxyInterfaces;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setDestQueue(Queue destQueue) {
		this.destQueue = destQueue;
	}

	public ClientJmsRequestResponseInterceptor() {
	}

	public ClientJmsRequestResponseInterceptor(JmsTemplate jmsTemplate,
			Queue destQueue) {
		super();
		this.jmsTemplate = jmsTemplate;
		this.destQueue = destQueue;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {

		boolean hasArgs = invocation.getArguments().length > 0;
		final Serializable args = hasArgs ? (Serializable) invocation
				.getArguments()[0] : "NO_PARAMS";

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
								.createObjectMessage(args);
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

	public void afterPropertiesSet() throws Exception {

	}

}
