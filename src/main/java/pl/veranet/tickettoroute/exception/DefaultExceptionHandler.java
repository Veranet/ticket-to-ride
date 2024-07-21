package pl.veranet.tickettoroute.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorEntity onIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        return new ErrorEntity(illegalArgumentException.getMessage());
    }
}
