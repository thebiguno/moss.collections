/*
 * Created on Aug 3, 2007 by wyatt
 */
package org.homeunix.thecave.moss.collections.exception;

public class UnmodifiableObjectException extends RuntimeException {
	public static final long serialVersionUID = 0;
	
	public UnmodifiableObjectException() {
		super();
	}
	
	public UnmodifiableObjectException(String message){
		super(message);
	}
	
	public UnmodifiableObjectException(String message, Throwable cause){
		super(message, cause);
	}
	
	public UnmodifiableObjectException(Throwable cause){
		super(cause);
	}
}
