package co.com.pragma.model.franchise.exceptions;

public enum ExceptionsEnum {
    ALREADY_EXIST_FRANCHISE(409,"Already exist franchise name"),
    FRANCHISE_NOT_FOUND(409,"Franchise not found"),
    ALREADY_EXIST_BRANCH(409, "Already exist branch name"),
    BRANCH_NOT_FOUND(409, "Branch not found" ),
    ALREADY_EXIST_PRODUCT(409, "Already exist product name"),

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
