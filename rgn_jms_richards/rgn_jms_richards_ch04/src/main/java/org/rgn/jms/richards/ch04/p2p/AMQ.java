package org.rgn.jms.richards.ch04.p2p;

import java.net.URI;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;

public class AMQ {

	public static void main(String[] args) throws Exception {
		BrokerService broker = new BrokerService();

		TransportConnector connector = new TransportConnector();
		connector.setUri(new URI("tcp://localhost:61616"));
		broker.addConnector(connector);
		broker.start();
	}

}
