package org.rgn.messaging.poc.reqreplcontr.service;

import java.io.Serializable;

public class RequestVO2 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7092325311683508162L;
	private String value;

	public RequestVO2(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "RequestVO2 [value=" + value + "]";
	}

}
