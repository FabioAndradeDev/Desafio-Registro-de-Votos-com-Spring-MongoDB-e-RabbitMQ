
package com.bbb_consumidor.app.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CpfJaExistenteException extends RuntimeException {
    public CpfJaExistenteException(String message) {
        super(message);
    }

    public CpfJaExistenteException(String message, Throwable cause) {
        super(message, cause);
    }
}