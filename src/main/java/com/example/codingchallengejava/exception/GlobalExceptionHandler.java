package com.example.codingchallengejava.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(400, e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInput(HttpMessageNotReadableException e) {
        String message = "Ungültige Eingabe. Bitte die Anfrage prüfen.";

        if(e.getMessage() != null && e.getMessage().contains("VehicleTypeFactor")){
            message = "Ungültiger Fahrzeugtyp. Erlaubt: PKW, MOTORRAD, LKW, ELEKTROAUTO";
        }

        if(e.getMessage() != null && e.getMessage().contains("out of range of int")){
            message = "Ungültige Kilometerleistung. Bitte eine gültige Zahl eingeben.";
        }

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(400, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception e) {
        return ResponseEntity
                .internalServerError()
                .body(new ErrorResponse(500, "Ein interner Fehler ist aufgetreten"));
    }
}
