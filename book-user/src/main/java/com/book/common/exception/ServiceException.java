package com.book.common.exception;

public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8029566318822720766L;

	public ServiceException() {
		super();
	}
	public ServiceException(String message) {
		super(message);
	}
	public ServiceException(Throwable cause) {
		super(cause);
	}
}
