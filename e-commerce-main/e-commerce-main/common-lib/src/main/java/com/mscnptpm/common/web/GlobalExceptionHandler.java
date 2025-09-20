package com.mscnptpm.common.web;

import com.mscnptpm.common.dto.Result;
import com.mscnptpm.common.error.AppException;
import com.mscnptpm.common.error.ErrorCode;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ProblemDetail handleAppException(AppException ex) {
        var status = ex.getCode().status();
        var pd = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        pd.setTitle(ex.getCode().name());
        pd.setProperty("code", ex.getCode().name());
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation error");
        pd.setTitle(ErrorCode.VALIDATION_ERROR.name());
        pd.setProperty("code", ErrorCode.VALIDATION_ERROR.name());
        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a,b) -> a));
        pd.setProperty("errors", fieldErrors);
        return pd;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraint(ConstraintViolationException ex) {
        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Constraint violation");
        pd.setTitle(ErrorCode.VALIDATION_ERROR.name());
        pd.setProperty("code", ErrorCode.VALIDATION_ERROR.name());
        pd.setProperty("errors", ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(v -> v.getPropertyPath().toString(), v -> v.getMessage(), (a,b)->a)));
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleOther(Exception ex) {
        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error");
        pd.setTitle(ErrorCode.INTERNAL_ERROR.name());
        pd.setProperty("code", ErrorCode.INTERNAL_ERROR.name());
        return pd;
    }
}
