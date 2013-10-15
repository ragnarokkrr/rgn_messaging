package rgn.camelia.ch02;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class FtpToJMSWithProcessorExample {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		
		ConnectionFactory connectionFactory = 
				new ActiveMQConnectionFactory("vm:/localhost");
		context.addComponent("jms"
			, JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		
		context.addRoutes(new RouteBuilder(){
			public void configure(){
				from("ftp://127.0.0.1/orders?username=johnny&password=quest")
				.process(new Processor(){
					public void process(Exchange exchange) throws Exception {
						System.out.println("\nWe just downloaded: "
							+ exchange.getIn().getHeader("CamelFileName"));
						System.out.println("Body:");
						System.out.println(exchange.getIn().getBody());
						System.out.println("\n==================\n");
					}
				})
				.to("jms:incomingOrders");
			}
		});
		
		context.start();
		Thread.sleep(60000);
		context.stop();

	}
}
