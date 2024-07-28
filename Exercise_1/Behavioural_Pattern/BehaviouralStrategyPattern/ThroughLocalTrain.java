
class ThroughLocalTrain implements NavigationStrategy {
    public void navigate(String startPoint, String endPoint) {
        System.out.println("Navigating from " + startPoint + " to " + endPoint + " by driving.");
    }
}