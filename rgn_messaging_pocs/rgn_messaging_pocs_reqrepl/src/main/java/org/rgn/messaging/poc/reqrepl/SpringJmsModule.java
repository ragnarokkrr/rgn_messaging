package org.rgn.messaging.poc.reqrepl;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.ProxyHelper;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.osgi.CamelContextFactoryBean;
import org.apache.camel.processor.aggregate.AbstractListAggregationStrategy;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.remoting.CamelProxyFactoryBean;
import org.logicblaze.lingo.jms.JmsProxyFactoryBean;
import org.rgn.messaging.poc.reqrepl.infra.jms.ClientJmsRequestResponseInterceptor;
import org.rgn.messaging.poc.reqrepl.service.MessageProcessor;
import org.rgn.messaging.poc.springremoting.infra.ServerRoutes;
import org.rgn.messaging.poc.springremoting.service.HelloService;
import org.rgn.messaging.poc.springremoting.service.HelloServiceImpl;
import org.rgn.messaging.poc.springremoting.service.Person;
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
	private ApplicationContext applicationContext;

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
	public MessageProcessor messageClientDelegate()
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

	// configuracao camel
	@Bean
	public JmsComponent jms() throws JMSException {
		JmsComponent jmsComponent = new JmsComponent();
		jmsComponent.setConnectionFactory(jndiQueueConnectionFactory);
		return jmsComponent;
	}

	@Bean
	public RouteBuilder serverRoutes() {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				errorHandler(deadLetterChannel("jms:queue:dead").log(
						"Received ${body}"));

				from("jms:queue3").wireTap("jms:queue3_log").to(
						ExchangePattern.InOut, "helloService");

				from("jms:queue3_log").process(new Processor() {

					public void process(Exchange exchange) throws Exception {
						System.out.println("\t\t" + exchange.getIn().getBody());
					}
				}).to("jms:queue3_agregates");

				from("jms:queue3_agregates").aggregate(constant(true))
						.completionSize(2).groupExchanges()
						.process(new Processor() {

							public void process(Exchange exchange)
									throws Exception {
								@SuppressWarnings("unchecked")
								List<Exchange> grouped = exchange.getIn()
										.getBody(List.class);
								System.out.println("\n\t List Grouped: "
										+ Arrays.toString(grouped.toArray()));
							}

						});
			}
		};
	}

	@Bean
	public CamelContext camelContext(RouteBuilder serverRoutes)
			throws Exception {
		CamelContext ctx = new SpringCamelContext(applicationContext);
		ctx.addRoutes(serverRoutes);
		return ctx;

	}

	@Bean(name = "helloService")
	public HelloService helloService() {
		return new HelloServiceImpl();
	}

	@Bean(name = "helloServiceProxy")
	public HelloService helloServiceProxy(CamelContext context)
			throws Exception {

		CamelProxyFactoryBean factory = new CamelProxyFactoryBean();

		factory.setServiceInterface(HelloService.class);
		factory.setServiceUrl("jms:queue3");
		factory.setCamelContext(context);
		factory.afterPropertiesSet();
		return (HelloService) factory.getObject();

	}

}
