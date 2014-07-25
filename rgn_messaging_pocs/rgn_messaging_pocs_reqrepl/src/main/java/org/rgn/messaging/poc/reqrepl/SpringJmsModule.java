package org.rgn.messaging.poc.reqrepl;

import java.util.Properties;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.destination.JndiDestinationResolver;

@Configuration
public class SpringJmsModule {
	@Inject
	@Qualifier("jndiEnvironment")
	private Properties jndiEnvironment;

	@Inject
	@Qualifier("jndiQueueConnectionFactory")
	private ConnectionFactory jndiQueueConnectionFactory;

	@Inject
	@Qualifier("messageDelegate")
	private MessageDelegate messageDelegate;

	@Inject
	@Qualifier("queue2Dest")
	private Queue queue2Dest;

	@Inject
	private BeanFactory beanFactory;

	@Bean(name = "queueConnectionFactory")
	public CachingConnectionFactory queueConnectionFactory() {
		CachingConnectionFactory queueConnectionFactory = new CachingConnectionFactory();
		queueConnectionFactory
				.setTargetConnectionFactory(jndiQueueConnectionFactory);
		queueConnectionFactory.setSessionCacheSize(1);
		return queueConnectionFactory;
	}

	@Bean(name = "destinationResolver")
	public JndiDestinationResolver destinationResolver() {
		JndiDestinationResolver destinationResolver = new JndiDestinationResolver();
		destinationResolver.setJndiEnvironment(jndiEnvironment);
		destinationResolver.setCache(true);
		destinationResolver.setFallbackToDynamicDestination(false);

		return destinationResolver;
	}

	@Bean(name = "jmsTemplate")
	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();

		jmsTemplate.setConnectionFactory(queueConnectionFactory());
		jmsTemplate.setDestinationResolver(destinationResolver());
		jmsTemplate.setReceiveTimeout(30000);
		jmsTemplate.setPubSubDomain(true);

		return jmsTemplate;
	}

	@Bean(name = "messageListenerAdapter")
	public MessageListenerAdapter MessageListenerAdapter() {
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(
				messageDelegate);

		messageListenerAdapter.setDefaultListenerMethod("processa");
		return messageListenerAdapter;

	}

	@Bean(name = "clientJmstRequestResponseInterceptor")
	public ClientJmstRequestResponseInterceptor clienJmstRequestResponseInterceptor() {
		ClientJmstRequestResponseInterceptor interceptor = new ClientJmstRequestResponseInterceptor(
				jmsTemplate(), queue2Dest);

		return interceptor;
	}
}
