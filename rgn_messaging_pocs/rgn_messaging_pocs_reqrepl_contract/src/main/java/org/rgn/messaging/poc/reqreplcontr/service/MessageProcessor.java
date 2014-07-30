package org.rgn.messaging.poc.reqreplcontr.service;

import org.rgn.messaging.poc.reqreplcontr.annotations.MessagingContract;
import org.rgn.messaging.poc.reqreplcontr.annotations.MessagingDestination;

@MessagingContract(queue="filaoRU")
public interface MessageProcessor {

	@MessagingDestination(queue = "processa01Queue", setMessageIdAsCorrelationId = true, temporaryResponse = true)
	ResponseVO processa01(RequestVO requestVO);

	@MessagingDestination(queue = "processa02Queue")
	ResponseVO processa(RequestVO2 requestVO2);

	int soma(int lho, int rho);
}
