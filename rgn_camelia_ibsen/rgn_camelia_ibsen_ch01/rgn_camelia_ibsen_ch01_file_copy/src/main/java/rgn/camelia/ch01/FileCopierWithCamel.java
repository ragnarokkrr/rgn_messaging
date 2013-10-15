package rgn.camelia.ch01;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class FileCopierWithCamel {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();

		context.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("file:data/inbox?noop=true")
					.to("file:data/outbox");
			}

		});

		context.start();
		Thread.sleep(10000);
		context.stop();
	}

}
