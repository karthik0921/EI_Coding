
public class NavigationContext {
    private NavigationStrategy strategy;

    public void setStrategy(NavigationStrategy strategy) {
        this.strategy = strategy;
    }

    public void navigate(String startPoint, String endPoint) {
        strategy.navigate(startPoint, endPoint);
    }
}
