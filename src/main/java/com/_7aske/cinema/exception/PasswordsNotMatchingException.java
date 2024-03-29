package com._7aske.cinema.exception;

import com._7aske.grain.web.controller.annotation.ResponseStatus;
import com._7aske.grain.web.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PasswordsNotMatchingException extends RuntimeException {
    public PasswordsNotMatchingException(String message) {
        super(message);
    }
}
