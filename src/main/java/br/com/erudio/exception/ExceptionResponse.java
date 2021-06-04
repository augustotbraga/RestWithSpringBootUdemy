package br.com.erudio.exception;

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date timesTamp;
	private String message;
	private String datails;

	public ExceptionResponse(Date timesTamp, String message, String datails) {
		this.timesTamp = timesTamp;
		this.message = message;
		this.datails = datails;
	}

	public Date getTimesTamp() {
		return timesTamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDatails() {
		return datails;
	}

}
