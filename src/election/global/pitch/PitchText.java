package election.global.pitch;


public class PitchText extends Pitch {
    private String text;

    public PitchText(String text) {
        this.text = text;
    }

    @Override
    public String getPitch() {
        return text;
    }
}
