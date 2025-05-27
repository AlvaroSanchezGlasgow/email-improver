package com.module.email_improver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllExceptions(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        String errorMessage;

        if (ex instanceof HttpClientErrorException) {
            errorMessage = "Error al comunicarse con el servicio de OpenAI. Por favor, verifica tu clave API.";
        } else {
            errorMessage = "Ha ocurrido un error inesperado. Por favor, inténtalo de nuevo.";
        }

        modelAndView.addObject("error", errorMessage);
        modelAndView.addObject("details", ex.getMessage());
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return modelAndView;
    }
}