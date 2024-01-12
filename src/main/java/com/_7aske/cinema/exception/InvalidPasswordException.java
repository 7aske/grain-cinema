package com._7aske.cinema.exception;

import com._7aske.grain.web.controller.annotation.ResponseStatus;
import com._7aske.grain.web.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
