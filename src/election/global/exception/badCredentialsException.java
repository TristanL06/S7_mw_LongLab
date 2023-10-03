package election.global.exception;

public class badCredentialsException extends globalException {

    private final String errorTitle;

    public badCredentialsException(String wrongCredentials) {
        super("The credentials you have entered : " + wrongCredentials + " are wrong. Please try again.");
        this.errorTitle = wrongCredentials;
    }

    @Override
    public String getErrorTitle() {
        return "The credentials you have entered : " + this.errorTitle + " are wrong. Please try again.";
    }
}
