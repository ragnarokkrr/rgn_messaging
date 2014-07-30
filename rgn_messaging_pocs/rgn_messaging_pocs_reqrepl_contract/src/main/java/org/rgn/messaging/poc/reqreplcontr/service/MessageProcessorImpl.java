package org.rgn.messaging.poc.reqreplcontr.service;

import org.springframework.stereotype.Component;

@Component("messageProcessor")
public class MessageProcessorImpl implements MessageProcessor {

	public ResponseVO processa01(RequestVO requestVO) {
		System.out.println("ResponseVO processa(RequestVO requestVO)");
		return new ResponseVO(requestVO.toString());
	}

	public ResponseVO processa(RequestVO2 requestVO2) {
		System.out.println("ResponseVO processa(RequestVO2 requestVO2)");
		return new ResponseVO(requestVO2.toString());
	}

	public int soma(int lho, int rho) {
		System.out.println(" int soma(int lho, int rho)");

		return lho + rho;
	}

}
