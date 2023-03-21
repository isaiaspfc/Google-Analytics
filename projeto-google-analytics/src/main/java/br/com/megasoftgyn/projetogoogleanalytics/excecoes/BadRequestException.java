package br.com.megasoftgyn.projetogoogleanalytics.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    
    private static final long serialVersionUID = 111789456100841112L;
    
    public BadRequestException(final String message) {
        super(message);
    }
}
