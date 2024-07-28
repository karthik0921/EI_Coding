
public class BehaviouralStrategyPattern {
    public static void main(String[] args) {
        NavigationContext context = new NavigationContext();

        context.setStrategy(new ThroughLocalTrain());
        context.navigate("Home", "Office");

        context.setStrategy(new ThroughBus());
        context.navigate("office","park");

        context.setStrategy(new ThroughWalk());
        context.navigate("Park", "Gym");

        
    }
}
