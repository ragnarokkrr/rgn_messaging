package org.rgn.messaging.poc.reqrepl;

import java.io.Serializable;

public class ResponseVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7092325311683508162L;
	private String value;

	public ResponseVO(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "ResponseVO [value=" + value + "]";
	}

}
