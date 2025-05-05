package com.chat.chatapp.Exception;


public enum ErrorCode {
    UNICATEGORIZED_EXCEPTION(9999, "Unicategorozed exception!"),
    USER_NOT_EXISTED(1001,"User not existed!"),
    USERNAME_INVALID(1002,"Username must be between 3 and 12 characters"),
    PASSWORD_INVALID(1003,"Password must be least at 8 characters"),
    USER_EXISTED(1004, "User existed!"),
    UNAUTHENTICATED(1005,"Unauthenticated"),
    INVALID_KEY(1006, "Invalid key!"),
    CONVERSATION_NOT_EXISTED(1007,"Conversation_not_existed!"),
    THIS_FIELD_CAN_NOT_EMPTY(1008,"This field can not empty"),
    USER_NOT_EXISTED_IN_GROUP(1009,"User not existed in group!"),
    FRIEND_REQUEST_DOES_NOT_EXISTED(1010,"Friend request does not exist yet!"),
    FRIEND_REQ_404(1011,"You have no friend requests!"),
    USER_HAS_NO_FRIENDS(1012,"User has no friends"),
    FRIENDSHIP_NOT_EXISTED(1013,"Friendship not existed"),
    FRIENDSHIP_EXISTED(1014,"Friendship existes"),
    ERROR_LOAD_DATA(1100,"Error when load data")
;
    private int code;
    private String messageError;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessageError() {
        return messageError;
    }
    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }
    private ErrorCode(int code, String messageError) {
        this.code = code;
        this.messageError = messageError;
    }
}
