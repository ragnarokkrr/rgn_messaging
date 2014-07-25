package org.rgn.messaging.poc.reqrepl;

public interface MessageDelegate {

	ResponseVO processa(RequestVO requestVO);

	ResponseVO processa(RequestVO2 requestVO2);

	//ResponseVO processa();

}
