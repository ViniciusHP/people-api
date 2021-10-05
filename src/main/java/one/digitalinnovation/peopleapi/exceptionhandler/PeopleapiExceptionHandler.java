package one.digitalinnovation.peopleapi.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PeopleapiExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @ExceptionHandler({DataIntegrityViolationException.class})
    private ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
        String message = messageSource.getMessage("resource.operation-not-allowed", null, LocaleContextHolder.getLocale());
        return handleExceptionInternal(ex, new Error(message), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Data
    @AllArgsConstructor
    public static class Error {
        private String message;
    }
}
