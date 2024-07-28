class ModernVideoPlayer implements VideoPlayer {
    @Override
    public void play(String videoType, String fileName) {
        System.out.println("Playing " + videoType.toUpperCase() + " file: " + fileName + " using Modern Video Player.");
    }
}
