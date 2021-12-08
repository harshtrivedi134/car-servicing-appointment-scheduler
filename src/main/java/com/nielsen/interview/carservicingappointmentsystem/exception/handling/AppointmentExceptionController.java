package com.nielsen.interview.carservicingappointmentsystem.exception.handling;

import com.nielsen.interview.carservicingappointmentsystem.exception.AppointmentBookingException;
import com.nielsen.interview.carservicingappointmentsystem.exception.CustomerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class AppointmentExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {CustomerNotFoundException.class})
    protected ResponseEntity<Object> handleCustomeraNotFoundException(CustomerNotFoundException ce) {
        log.error("Customer does not exist",ce);
        return new ResponseEntity<>(ce.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AppointmentBookingException.class})
    protected ResponseEntity<Object> handleAppointmentBookingException(AppointmentBookingException ae) {
        log.error("Slots are unavailable for requested appointment",ae);
        return new ResponseEntity<>(ae.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
