package co.com.pragma.model.franchise.exceptions;

public enum ExceptionsEnum {
    ALREADY_EXIST_FRANCHISE(409,"Already exist franchise name")
    ;

    private final int httpStatus;
    private final String message;

    ExceptionsEnum(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
