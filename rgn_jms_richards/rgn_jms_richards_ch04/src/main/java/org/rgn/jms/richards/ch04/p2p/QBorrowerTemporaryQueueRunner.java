package org.rgn.jms.richards.ch04.p2p;

public class QBorrowerTemporaryQueueRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		QBorrowerTemporaryQueue borrower = 
			new QBorrowerTemporaryQueue("QueueCF", "LoanRequestQ", "LoanResponseQ");
		borrower.requestLoan();
	}

}
