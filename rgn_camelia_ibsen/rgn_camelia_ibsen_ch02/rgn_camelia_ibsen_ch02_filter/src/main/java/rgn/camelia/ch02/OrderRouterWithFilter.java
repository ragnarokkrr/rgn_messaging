package rgn.camelia.ch02;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class OrderRouterWithFilter {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory factory = 
			new ActiveMQConnectionFactory("vm:/localhost");
		
		context.addComponent("jms"
			, JmsComponent.jmsComponentAutoAcknowledge(factory));
		
		context.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() {
				from("file:src/data?noop=true").to("jms:incomingOrders");
				
				from("jms:incomingOrders")
				.process(new Processor(){
					public void process(Exchange exchange) throws Exception {
						System.out.println("From incoming: "
								+ exchange.getIn().getHeader("CamelFileName"));
					}
				})
				.choice()
					.when(header("CamelFileName").endsWith(".xml"))
						.process(new Processor(){
							public void process(Exchange exchange)
									throws Exception {
								System.out.println("Received XML order: "
										+ exchange.getIn().getHeader("CamelFileName"));
							}
							
						})
						.to("jms:xmlOrders")
					.when(header("CamelFileName").regex("^.*(csv|csl)$"))
						.to("jms:csvOrders")
					.otherwise()
						.to("jms:badOrders");
				
				from("jms:xmlOrders").filter(xpath("/order[not(@test)]"))
				.process(new Processor(){

					public void process(Exchange exchange) throws Exception {
						System.out.println("Received (filtered) XML order: "
								+ exchange.getIn().getHeader("CamelFileName"));
					}});
			}
		});

		context.start();
		Thread.sleep(10000);
		context.stop();
	}

}
