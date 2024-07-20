package pl.veranet.exception;

public class ErrorEntity {
    private String message;

    public ErrorEntity() {
    }

    public ErrorEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
