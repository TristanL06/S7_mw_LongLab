package election.global.pitch;


public class PitchVideo extends Pitch {
    private String video;

    public PitchVideo(String video) {
        this.video = video;
    }

    @Override
    public String getPitch() {
        return "This candidate have a video as pitch.\nHere is the link to the video : " + video;
    }
}