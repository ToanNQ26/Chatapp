package com.chat.chatapp.Exception;

public class AppException extends RuntimeException{
    private ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessageError());
        this.errorCode = errorCode;
     }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(ErrorCode errorCode) {
         this.errorCode = errorCode;
     }
}
