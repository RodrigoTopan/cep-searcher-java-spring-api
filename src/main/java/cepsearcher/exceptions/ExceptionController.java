package cepsearcher.exceptions;

import cepsearcher.exceptions.BadRequestException;
import cepsearcher.exceptions.InternalServerException;
import cepsearcher.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
public class ExceptionController {
    @Getter
    @Setter
    @AllArgsConstructor
    private class JsonResponse {
        String message;
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<JsonResponse> handleBadRequestException(BadRequestException e) {
        return new ResponseEntity<JsonResponse>(new JsonResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<JsonResponse> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<JsonResponse>(new JsonResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InternalServerException.class)
    public ResponseEntity<JsonResponse> handleInternalServerException(InternalServerException e) {
        return new ResponseEntity<JsonResponse>(new JsonResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<JsonResponse> handleUnexpectedException(Exception e) {
        return new ResponseEntity<JsonResponse>(new JsonResponse("Ocorreu um erro inesperado no servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
