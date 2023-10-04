package election.global;


public class User implements java.io.Serializable {

    private int userNumber;
    private String name;
    private String password;

    public User(int userNumber, String password, String name) {
        this.userNumber = userNumber;
        this.password = password;
        this.name = name;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
