package rgn.camelia.ch02;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class FtpToJMSRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
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

}
