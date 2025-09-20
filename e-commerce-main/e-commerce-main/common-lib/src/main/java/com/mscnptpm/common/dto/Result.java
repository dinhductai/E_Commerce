package com.mscnptpm.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class Result<T> {
    private final boolean success;
    private final T data;
    private final String errorCode;
    private final String message;

    private Result(boolean success, T data, String errorCode, String message) {
        this.success = success; this.data = data; this.errorCode = errorCode; this.message = message;
    }
    public static <T> Result<T> ok(T data) { return new Result<>(true, data, null, null); }
    public static <T> Result<T> ok() { return new Result<>(true, null, null, null); }
    public static <T> Result<T> fail(String errorCode, String message) { return new Result<>(false, null, errorCode, message); }

    public boolean isSuccess() { return success; }
    public T getData() { return data; }
    public String getErrorCode() { return errorCode; }
    public String getMessage() { return message; }
}