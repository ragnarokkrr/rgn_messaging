package org.rgn.messaging.poc.reqrepl.service;

public interface MessageProcessor {

	ResponseVO processa(RequestVO requestVO);

	ResponseVO processa(RequestVO2 requestVO2);

	//ResponseVO processa();

}
