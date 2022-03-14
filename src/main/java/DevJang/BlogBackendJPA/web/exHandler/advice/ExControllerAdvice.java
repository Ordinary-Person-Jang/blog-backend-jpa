package DevJang.BlogBackendJPA.web.exHandler.advice;

import DevJang.BlogBackendJPA.web.exHandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResult IllegalStateExHandler(IllegalStateException e) {
        log.error("[exceptionHandler]", e);
        return new ErrorResult("reduplication", e.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult IllegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler]", e);
        return new ErrorResult("bad", e.getMessage());
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResult BadCredentialsHandler(BadCredentialsException e) {
        log.error("[BadCredentialsException]", e);
        return new ErrorResult("badCredentials", e.getMessage());
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {

        log.error("[exceptionHandler ex", e);
        return new ErrorResult("EX", e.getMessage());
    }
}
