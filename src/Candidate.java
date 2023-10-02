public class Candidate implements java.io.Serializable {

    private String name;
    private String Surname;
    private String pitch;

    public Candidate(String name, String Surname, String pitch) {
        this.name = name;
        this.Surname = Surname;
        this.pitch = pitch;
    }

    public String toString() {
        return "Candidate{" + "name=" + name + ", Surname=" + Surname + ", pitch=" + pitch + '}';
    }

}
