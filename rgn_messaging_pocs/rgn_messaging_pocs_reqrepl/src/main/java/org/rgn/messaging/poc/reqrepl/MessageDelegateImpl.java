package org.rgn.messaging.poc.reqrepl;

import org.springframework.stereotype.Component;

@Component("messageDelegate")
public class MessageDelegateImpl implements MessageDelegate {

	public ResponseVO processa(RequestVO requestVO) {
		System.out.println("ResponseVO processa(RequestVO requestVO)");
		return new ResponseVO(requestVO.toString());
	}

	public ResponseVO processa(RequestVO2 requestVO2) {
		System.out.println("ResponseVO processa(RequestVO2 requestVO2)");
		return new ResponseVO(requestVO2.toString());
	}

}
