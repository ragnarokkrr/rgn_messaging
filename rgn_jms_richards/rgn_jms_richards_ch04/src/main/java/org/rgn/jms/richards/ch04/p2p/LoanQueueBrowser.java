package org.rgn.jms.richards.ch04.p2p;

import java.util.Enumeration;

import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class LoanQueueBrowser {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// Connect to the provider
		Context ctx = new InitialContext();

		QueueConnectionFactory qFactory = (QueueConnectionFactory)
				ctx.lookup("QueueCF");
		
		QueueConnection qConnection = qFactory.createQueueConnection();
		
		QueueSession qSession = qConnection.createQueueSession(false
				, Session.AUTO_ACKNOWLEDGE);

		String[] qs = {"LoanRequestQ", "LoanResponseQ"};
		Queue mQueue = (Queue) ctx.lookup(qs[1]); 
		
		
		QueueBrowser queueBrowser = qSession.createBrowser(mQueue);
		Enumeration<?> e = queueBrowser.getEnumeration();
		
		while(e.hasMoreElements()){
			TextMessage msg = (TextMessage) e.nextElement();
			System.out.println("Browsing: " + msg.getText());
		}
		System.out.println("ok");
		
		queueBrowser.close();
		qConnection.close();
		System.exit(0);
		
	}

}
