package org.rgn.messaging.poc.reqreplcontr;

import java.util.Properties;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsModule {
	@Inject
	@Qualifier("jndiQueueConnectionFactory")
	private ConnectionFactory jndiQueueConnectionFactory;

	@Inject
	@Qualifier("jndiEnvironment")
	private Properties jndiEnvironment;
}
