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
import javax.jms.TextMessage;

import org.rgn.messaging.poc.reqrepl.service.MessageProcessor;
import org.rgn.messaging.poc.reqrepl.service.RequestVO;
import org.rgn.messaging.poc.reqrepl.service.RequestVO2;
import org.rgn.messaging.poc.reqrepl.service.ResponseVO;
import org.rgn.messaging.poc.springremoting.service.HelloService;
import org.rgn.messaging.poc.springremoting.service.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.ProducerCallback;

public class SimpleJmsSender {

	public static void main(String[] args) throws JMSException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"app-context-jms-ns.xml");

		HelloService helloService = ctx.getBean("helloServiceProxy",
				HelloService.class);

		for (int i = 0; i < 5; i++) {
			Person p = new Person(i, "John", "Silva");

			System.out.println("Enviando " + p);

			String response = helloService.sayHello(p);

			System.out.println(i + ")  Resultado espringue: " + response);
			response = helloService.sayGoodBye(p);

			System.out.println(i + ")  Resultado espringue: " + response);

		}

		//
		// testRequestReply(ctx);
		//
		// testReqRepWithAdapter(ctx);
		//
		// testDynamicProxy(ctx);

	}

	/**
	 * 
	 * Dynamic proxy and
	 * {@link org.rgn.messaging.poc.reqrepl.infra.jms.ClientJmsRequestResponseInterceptor}
	 * in action
	 * 
	 * @param ctx
	 */
	private static void testDynamicProxy(ApplicationContext ctx) {
		System.out.println("\n3) testDynamicProxy()\n");
		MessageProcessor delegate = ctx.getBean("messageClientDelegate",
				MessageProcessor.class);

		ResponseVO responseVO = delegate.processa(new RequestVO("sssssss"));

		System.out.println(responseVO);

		responseVO = delegate.processa(new RequestVO2("aaaaassss"));

		System.out.println(responseVO);

		// responseVO = delegate.processa();
		//
		// System.out.println(responseVO);

	}

	/**
	 * 
	 * First test with
	 * {@link org.springframework.jms.listener.adapter.MessageListenerAdapter}
	 * adapter (calling queue2Dest)
	 * 
	 * @param ctx
	 */
	private static void testReqRepWithAdapter(ApplicationContext ctx) {
		System.out.println("\n2) testReqRepWithAdapter()\n");
		JmsTemplate jmsTemplate = ctx.getBean("jmsTemplate", JmsTemplate.class);
		final Queue destQueue2 = ctx.getBean("queue2Dest", Queue.class);

		Serializable requestVO = new RequestVO("req-VO-0001");

		ResponseVO response = processaCall(jmsTemplate, destQueue2, requestVO);
		System.out.println(response);

		requestVO = new RequestVO2("req-VO-0002");

		response = processaCall(jmsTemplate, destQueue2, requestVO);
		System.out.println(response);

	}

	private static ResponseVO processaCall(JmsTemplate jmsTemplate,
			final Queue destQueue2, final Serializable requestVO) {
		ResponseVO response = jmsTemplate.execute(destQueue2,
				new ProducerCallback<ResponseVO>() {

					public ResponseVO doInJms(Session session,
							MessageProducer producer) throws JMSException {

						producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

						TemporaryQueue tempQueue = session
								.createTemporaryQueue();

						System.out.println("Dest QUEUE2=> "
								+ ((Queue) destQueue2).getQueueName());
						System.out.println("NEW tempQUEUE2 => "
								+ tempQueue.getQueueName());

						ObjectMessage message = session
								.createObjectMessage(requestVO);
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
						return (ResponseVO) consumedMessage.getObject();
					}
				});
		return response;
	}

	/**
	 * 
	 * First test without
	 * {@link org.springframework.jms.listener.adapter.MessageListenerAdapter}
	 * (calling queue2Dest)
	 * 
	 * @param ctx
	 * @throws JMSException
	 */
	private static void testRequestReply(ApplicationContext ctx)
			throws JMSException {
		System.out.println("\n1) testRequestReply()\n");
		JmsTemplate jmsTemplate = ctx.getBean("jmsTemplate", JmsTemplate.class);
		final Queue destQueue = ctx.getBean("queue1Dest", Queue.class);

		final String messageObject = "Estringue de req";

		TextMessage reply = jmsTemplate.execute(destQueue,
				new ProducerCallback<TextMessage>() {

					public TextMessage doInJms(Session session,
							MessageProducer producer) throws JMSException {
						producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

						TemporaryQueue tempQueue = session
								.createTemporaryQueue();

						System.out.println("Dest QUEUE=> "
								+ ((Queue) destQueue).getQueueName());
						System.out.println("NEW tempQUEUE=> "
								+ tempQueue.getQueueName());

						TextMessage message = session
								.createTextMessage(messageObject);
						message.setJMSReplyTo(tempQueue);

						producer.send(message);

						// String correlationId = UUID.randomUUID().toString();
						// message.getJMSMessageID(); setado apenas apos o
						// "send()"
						String correlationId = message.getJMSMessageID();
						// mandamos para a destination default configurada para
						// o
						// JmsTemplate
						// message.setJMSCorrelationID(correlationId);

						System.out.println("New msg ID: " + correlationId);

						MessageConsumer consumer = session.createConsumer(
								tempQueue, String.format(
										"JMSCorrelationID = '%s'",
										correlationId));

						long receiveTimeout = 10000;
						TextMessage consumedMessage = (TextMessage) consumer
								.receive(receiveTimeout);

						consumer.close();
						tempQueue.delete();
						return consumedMessage;
					}

				});

		System.out.println("REPLY===> " + reply.getText());
	}

}
