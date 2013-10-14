package org.rgn.jms.richards.ch04.pubsub;

public class TLenderRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TLender lender = new TLender("TopicCF", "RateTopic");
		lender.perform();
		
	}

}
