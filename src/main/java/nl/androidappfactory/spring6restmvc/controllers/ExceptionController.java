package nl.androidappfactory.spring6restmvc.controllers;

import lombok.extern.slf4j.Slf4j;
import nl.androidappfactory.spring6restmvc.model.CustomerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CustomerDTO> handleNotFoundException() {
        log.debug("in ExceptionController to handle NotFoundException.................. ");
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomerDTO> handleIllegalArgumentException() {
        log.debug("in ExceptionController to handle IllegalArgumentException.............. ");
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomerDTO> handleRuntimeException() {
        log.debug("in ExceptionController to handle RuntimeException.............. ");
        return ResponseEntity.internalServerError().build();
    }


}
