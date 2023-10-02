package exception;

public class globalException extends Exception {

    private final String errorTitle;

    public globalException(String message) {
        super(message);
        this.errorTitle = "An error has occurred";
    }

    public String getErrorTitle() {
        return this.errorTitle;
    }

}
