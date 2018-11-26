package in.ravikalla.security.exception;

import org.springframework.security.core.AuthenticationException;

public class UnknownUserException extends AuthenticationException {
	private static final long serialVersionUID = -4153279258648510298L;

	public UnknownUserException(String msg, Throwable t) {
		super(msg, t);
	}

	public UnknownUserException(String msg) {
		super(msg);
	}
}
