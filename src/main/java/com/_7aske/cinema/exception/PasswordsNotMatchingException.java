package com._7aske.cinema.exception;

public class PasswordsNotMatchingException extends RuntimeException {
	public PasswordsNotMatchingException(String path) {
		super(path);
	}
}
