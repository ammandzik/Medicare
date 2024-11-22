package pl.infoshare.clinicweb.advice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.infoshare.clinicweb.exception.validation.TimeSlotUnavailableException;


import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerApp {


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
    @ExceptionHandler(TimeSlotUnavailableException.class)
    public  Map<String ,String> handleTimeSlotUnavailableException(TimeSlotUnavailableException exception) {
        Map<String, String> errorsMap = new HashMap<>();
        errorsMap.put("error","Wybrany termin jest juz zajety");
        return errorsMap;
    }

}
