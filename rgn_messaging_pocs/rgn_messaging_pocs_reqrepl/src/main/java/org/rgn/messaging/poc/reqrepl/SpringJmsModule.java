package org.rgn.messaging.poc.reqrepl;

import java.util.Properties;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.rgn.messaging.poc.reqrepl.infra.jms.ClientJmsRequestResponseInterceptor;
import org.rgn.messaging.poc.reqrepl.service.MessageProcessor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.destination.JndiDestinationResolver;

@Configuration
public class SpringJmsModule {

	@Inject
	@Qualifier("jndiQueueConnectionFactory")
	private ConnectionFactory jndiQueueConnectionFactory;

	@Inject
	@Qualifier("jndiEnvironment")
	private Properties jndiEnvironment;

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
	public MessageListenerAdapter MessageListenerAdapter(
			@Qualifier("messageProcessor") MessageProcessor messageProcessor) {
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(
				messageProcessor);

		messageListenerAdapter.setDefaultListenerMethod("processa");

		return messageListenerAdapter;

	}

	@Bean(name = "clientJmsRequestResponseInterceptor")
	public ClientJmsRequestResponseInterceptor clienJmstRequestResponseInterceptor(
			@Qualifier("queue2Dest") Queue queue2Dest) {
		ClientJmsRequestResponseInterceptor interceptor = new ClientJmsRequestResponseInterceptor(
				jmsTemplate(), queue2Dest);

		return interceptor;
	}

	@Bean(name = "messageClientDelegate")
	public MessageProcessor messageClientDelegate(
			ApplicationContext applicationContext)
			throws ClassNotFoundException {

		ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
		proxyFactoryBean
				.setProxyInterfaces(new Class[] { MessageProcessor.class });
		// proxyFactoryBean.setTarget(null);
		proxyFactoryBean
				.setInterceptorNames(new String[] { "clientJmsRequestResponseInterceptor" });
		// In a java config class, the application context is not automatically
		// injected on a
		// BeanFactoryAware bean
		proxyFactoryBean.setBeanFactory(applicationContext);

		return (MessageProcessor) proxyFactoryBean.getObject();
	}

}
