package org.rgn.messaging.poc.reqrepl.service;

import java.io.Serializable;

public class RequestVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7092325311683508162L;
	private String value;

	public RequestVO(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "RequestVO [value=" + value + "]";
	}

}
