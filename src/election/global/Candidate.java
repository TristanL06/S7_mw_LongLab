package election.global;

import election.global.pitch.Pitch;
import election.global.pitch.PitchText;
import election.global.pitch.PitchVideo;

public class Candidate implements java.io.Serializable {

    private Integer rank;
    private String name;
    private String Surname;
    private Pitch pitch;

    public Candidate(String rank, String name, String pitch , boolean isAVideoPitch) {
        this.rank = new Integer(rank).intValue();
        this.name = name;
        if (isAVideoPitch) {
            this.pitch = new PitchVideo(pitch);
        } else {
            this.pitch = new PitchText(pitch);
        }
    }

    public String toString() {
        return "Rank :" + rank + "\nName : " + name + "\nPitch : " + pitch.getPitch();
    }

    public String getRank() {
        return rank.toString();
    }

}
