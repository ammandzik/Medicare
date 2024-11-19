package pl.infoshare.clinicweb.advice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
class ExceptionHandlerApp {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleException(MethodArgumentNotValidException exception) {
        Map<String, String> errorsMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach((error) -> {

            errorsMap.put(error.getField(), error.getDefaultMessage());

        });
        return errorsMap;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String, String> handleException(EntityNotFoundException exception) {

        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("error", "Nie znaleziono podanego obiektu w bazie.");

        return errorsMap;
    }

    @ExceptionHandler(PeselFormatException.class)
    public Map<String, String> handleException(PeselFormatException exception) {

        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("error", "Niepoprawny format numeru pesel.");

        return errorsMap;
    }

    @ExceptionHandler(UserEmailExistsException.class)
    public Map<String, String> handleException(UserEmailExistsException exception) {

        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("error", "Użytkownik o podanym adresie email już istnieje.");
        return errorsMap;
    }


}
