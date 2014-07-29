package org.rgn.messaging.poc.reqrepl.service;

import org.springframework.stereotype.Component;

@Component("messageProcessor")
public class MessageProcessorImpl implements MessageProcessor {

	public ResponseVO processa(RequestVO requestVO) {
		System.out.println("ResponseVO processa(RequestVO requestVO)");
		return new ResponseVO(requestVO.toString());
	}

	public ResponseVO processa(RequestVO2 requestVO2) {
		System.out.println("ResponseVO processa(RequestVO2 requestVO2)");
		return new ResponseVO(requestVO2.toString());
	}

	//ignore it... needs working
	public ResponseVO processa() {
		System.out.println("ResponseVO processa()");
		return new ResponseVO("NO PAYLOAD!!!!!");
	}

}
