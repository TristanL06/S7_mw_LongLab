package election.global.exception;

public class badOTPException extends globalException {

    public badOTPException() {
        super("The OTP is wrong");
    }

    @Override
    public String getErrorTitle() {
        return "The OTP is wrong";
    }
}