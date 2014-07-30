package org.rgn.messaging.poc.springremoting.infra;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;

public class ServerRoutes extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("helloService").to(ExchangePattern.InOut, "jms:queue3");
	}

}
