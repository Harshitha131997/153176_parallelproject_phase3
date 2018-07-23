package com.capgemini.core.pwa.exception;

public class InsufficientBalanceException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public InsufficientBalanceException(String msg) {
		super(msg);
	}
	public InsufficientBalanceException() {
		super();
	}
	public InsufficientBalanceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	public InsufficientBalanceException(String message, Throwable cause) {
		super(message, cause);
	}
	public InsufficientBalanceException(Throwable cause) {
		super(cause);
	}
}
