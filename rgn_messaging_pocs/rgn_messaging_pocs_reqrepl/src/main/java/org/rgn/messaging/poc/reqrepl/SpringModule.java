package org.rgn.messaging.poc.reqrepl;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiTemplate;

import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
public class SpringModule {

	@Bean
	public JndiTemplate jndiTemplate() {

		Properties jndiProps = new Properties();

		jndiProps.setProperty("java.naming.factory.initial",
				"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		jndiProps.setProperty("java.naming.provider.url",
				"tcp://localhost:61616");
		jndiProps.setProperty("java.naming.security.principal", "system");
		jndiProps.setProperty("java.naming.security.credentials", "manager");
		jndiProps.setProperty("connectionFactoryNames", "QueueCF");

		JndiTemplate jndiTemplate = new JndiTemplate();

		jndiTemplate.setEnvironment(jndiProps);

		return jndiTemplate;
	}

	@Bean
	public JndiObjectFactoryBean jndiObjectFactoryBean() {
		JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
		
		jndiObjectFactoryBean.setJndiTemplate(jndiTemplate());

		return jndiObjectFactoryBean;
	}

}
