package rgn.camelia.ch02;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class OrderRouterOtherwise {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		CamelContext camelContext = new DefaultCamelContext();
		ConnectionFactory connectionFactory = 
				new ActiveMQConnectionFactory("vm:/localhost");
		camelContext.addComponent("jms"
				, JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		camelContext.addRoutes(new RouteBuilder(){
			@Override
			public void configure() {
				from("file:src/data?noop=true").to("jms:incomingOrders");
				
				from("jms:incomingOrders")
				.choice()
					.when(header("CamelFileName").endsWith(".xml"))
						.to("jms:xmlOrders")
					.when(header("CamelFileName").regex("^.*(csv|csl)$"))
						.to("jms:csvOrders")
					.otherwise()
						.to("jms:badOrders");
				
				from("jms:xmlOrders").process(new Processor(){
					public void process(Exchange exchange) throws Exception {
						System.out.println("Received XML order: "
								+ exchange.getIn().getHeader("CamelFileName"));
					}});

				from("jms:csvOrders").process(new Processor(){
					public void process(Exchange exchange) throws Exception {
						System.out.println("Received CSV order: "
								+ exchange.getIn().getHeader("CamelFileName"));
					}});
				
				from("jms:badOrders").process(new Processor(){
					public void process(Exchange exchange) throws Exception {
						System.out.println("Received bad order: "
								+ exchange.getIn().getHeader("CamelFileName"));
					}});

			}});
		
		camelContext.start();
		Thread.sleep(10000);
		camelContext.stop();
	}

}
