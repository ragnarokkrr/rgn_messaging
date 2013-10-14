package org.rgn.jms.richards.ch04.p2p;

public class QLenderRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		QLender lender = new QLender("QueueCF", "LoanRequestQ");
		lender.perform();
	}

}
