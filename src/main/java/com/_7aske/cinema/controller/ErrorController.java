package com._7aske.cinema.controller;

import com._7aske.grain.web.controller.annotation.ExceptionController;
import com._7aske.grain.web.controller.exceptionhandler.ExceptionHandler;
import com._7aske.grain.web.exception.HttpException;
import com._7aske.grain.web.http.HttpResponse;

@ExceptionController
public class ErrorController {

    @ExceptionHandler(HttpException.Forbidden.class)
    public String handleForbidden(HttpException.Forbidden ex, HttpResponse response) {
        return "redirect:/login";
    }
}
