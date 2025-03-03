package co.com.pragma.model.franchise.models;

public class DeleteResponse {

    private String message;

    public DeleteResponse(String message) {
        this.message = message;
    }

    public DeleteResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
