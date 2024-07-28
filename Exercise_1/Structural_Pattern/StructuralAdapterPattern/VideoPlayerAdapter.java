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