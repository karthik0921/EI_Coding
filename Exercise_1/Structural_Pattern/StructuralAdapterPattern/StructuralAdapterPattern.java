
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
