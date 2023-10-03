package election.global.exception;

public class voteIsCloseException extends globalException {


    public voteIsCloseException() {
        super("The voting phase is now closed.");
    }

    @Override
    public String getErrorTitle() {
        return "The voting phase is now closed.";
    }
}