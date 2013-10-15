package rgn.camelia.ch02;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class FtpToJMSExample {

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
				.to("jms:incomingOrders");
			}
		});
		
		context.start();
		Thread.sleep(10000);
		context.stop();

	}
}
