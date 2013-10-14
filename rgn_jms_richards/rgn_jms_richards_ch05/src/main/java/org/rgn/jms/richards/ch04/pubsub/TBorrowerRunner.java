package org.rgn.jms.richards.ch04.pubsub;

public class TBorrowerRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TBorrower borrower = new TBorrower("TopicCF", "RateTopic", "0.1");
		
		borrower.performRateEval();
	}

}
