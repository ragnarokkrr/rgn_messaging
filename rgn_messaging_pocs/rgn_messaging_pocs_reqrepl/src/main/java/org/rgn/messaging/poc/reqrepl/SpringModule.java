package org.rgn.messaging.poc.reqrepl;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jndi.JndiTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
public class SpringModule {

	@Bean
	public JndiTemplate jndiTemplate() {

		Properties jndiProps = defaultJndiProps();

		JndiTemplate jndiTemplate = new JndiTemplate();

		jndiTemplate.setEnvironment(jndiProps);

		return jndiTemplate;
	}

	@Bean
	public Properties defaultJndiProps() {
		Properties jndiProps = new Properties();

		jndiProps.setProperty("java.naming.factory.initial",
				"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		jndiProps.setProperty("java.naming.provider.url",
				"tcp://localhost:61616");
		jndiProps.setProperty("java.naming.security.principal", "system");
		jndiProps.setProperty("java.naming.security.credentials", "manager");
		jndiProps.setProperty("connectionFactoryNames", "QueueCF");
		return jndiProps;
	}

	@Bean
	public JndiObjectFactoryBean jndiQueueConnectionFactory() {
		JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();

		jndiObjectFactoryBean.setJndiTemplate(jndiTemplate());
		jndiObjectFactoryBean.setJndiName("QueueCF");
		return jndiObjectFactoryBean;
	}

	@Bean
	public CachingConnectionFactory cachingConnectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
		cachingConnectionFactory.setTargetConnectionFactory(jndiQueueConnectionFactory().getObject());
		
		return cachingConnectionFactory;

	}

}
