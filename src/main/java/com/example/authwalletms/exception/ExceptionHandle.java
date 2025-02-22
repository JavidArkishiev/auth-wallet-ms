package com.example.authwalletms.exception;

import com.example.authwalletms.dto.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseResponse<String> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return BaseResponse.failure(errorMessage);
    }


    @ExceptionHandler(ResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse<String> handleDataNotFoundException(ResourceFoundException e) {
        return BaseResponse.failure(e.getMessage());
    }

    @ExceptionHandler(ResourceExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public BaseResponse<String> handleDataExistException(ResourceExistException e) {
        return BaseResponse.failure(e.getMessage());

    }

//    @ExceptionHandler(FeignException.NotFound.class)
//    public ResponseEntity<?> handleFeignNotFoundException(FeignException.NotFound ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.contentUTF8());
//    }

//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public BaseResponse<String> handleAccessDeniedException(AccessDeniedException e) {
//        return BaseResponse.failure(e.getMessage());
//    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public BaseResponse<String> handleException(Exception e) {
//        return BaseResponse.failure("Server error : " + e.getMessage());
//    }

}
