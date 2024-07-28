
interface VideoPlayer {
    void play(String videoType, String fileName);
}


class OldVideoPlayer {
    public void playMP4(String fileName) {
        System.out.println("Playing MP4 file: " + fileName);
    }

    public void playAVI(String fileName) {
        System.out.println("Playing AVI file: " + fileName);
    }
}


class VideoPlayerAdapter implements VideoPlayer {
    private OldVideoPlayer oldVideoPlayer;

    public VideoPlayerAdapter(OldVideoPlayer oldVideoPlayer) {
        this.oldVideoPlayer = oldVideoPlayer;
    }

    @Override
    public void play(String videoType, String fileName) {
        if (videoType.equalsIgnoreCase("mp4")) {
            oldVideoPlayer.playMP4(fileName);
        } else if (videoType.equalsIgnoreCase("avi")) {
            oldVideoPlayer.playAVI(fileName);
        } else {
            System.out.println("Invalid video type: " + videoType + ". Supported types are MP4 and AVI.");
        }
    }
}

class ModernVideoPlayer implements VideoPlayer {
    @Override
    public void play(String videoType, String fileName) {
        System.out.println("Playing " + videoType.toUpperCase() + " file: " + fileName + " using Modern Video Player.");
    }
}


public class StructuralAdapterPattern {
    public static void main(String[] args) {
       
        OldVideoPlayer oldVideoPlayer = new OldVideoPlayer();
        VideoPlayer adapter = new VideoPlayerAdapter(oldVideoPlayer);
        adapter.play("mp4", "movie1.mp4");
        adapter.play("avi", "movie2.avi");
        adapter.play("mkv", "movie3.mkv");
        
        
        VideoPlayer modernVideoPlayer = new ModernVideoPlayer();
        modernVideoPlayer.play("mp4", "modern_movie1.mp4");
        modernVideoPlayer.play("avi", "modern_movie2.avi");
    }
}
