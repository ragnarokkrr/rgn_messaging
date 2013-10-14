package org.rgn.jms.richards.ch04.p2p;

public class QBorrowerRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		QBorrower borrower = 
			new QBorrower("QueueCF", "LoanRequestQ", "LoanResponseQ");
		borrower.requestLoan();
	}

}
