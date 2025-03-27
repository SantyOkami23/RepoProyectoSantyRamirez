package co.pruebatecnica.exception;

public class InvalidTaskStateException extends Exception {
    public InvalidTaskStateException(String message) {
        super(message);
    }
}