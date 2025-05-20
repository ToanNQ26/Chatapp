package com.chat.chatapp.Exception;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.chat.chatapp.dto.response.ApiResponse;



@ControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler(value = Exception.class)
    // ResponseEntity<ApiResponse<String>> handlingRuntimeException(RuntimeException exception){
    //     ApiResponse<String> apiResponse = new ApiResponse<>();
    //     apiResponse.setCode(ErrorCode.UNICATEGORIZED_EXCEPTION.getCode());
    //     apiResponse.setMessage(ErrorCode.UNICATEGORIZED_EXCEPTION.getMessageError());
    //     apiResponse.setResult("Unknow Error");
    //     return ResponseEntity.badRequest().body(apiResponse);
    // }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<String>> handlingAppException(AppException exception){
        ErrorCode erroCode = exception.getErrorCode();

        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(erroCode.getCode());
        apiResponse.setMessage(erroCode.getMessageError());
        apiResponse.setResult("");

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<String>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        // String enumkey = exception.getFieldError().getDefaultMessage();
        String enumkey = Optional.ofNullable(exception.getFieldError())
                            .map(FieldError::getDefaultMessage)
                            .orElse("Valid error!");
        ErrorCode erroCode = ErrorCode.INVALID_KEY;

        try {
        erroCode = ErrorCode.valueOf(enumkey);
        } catch (IllegalArgumentException e) {
            
        }
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(erroCode.getCode());
        apiResponse.setMessage(erroCode.getMessageError());

        return ResponseEntity.badRequest().body(apiResponse);

    }
}
