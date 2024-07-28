interface NavigationStrategy {
    void navigate(String startPoint, String endPoint);
}

class ThroughLocalTrain implements NavigationStrategy {
    public void navigate(String startPoint, String endPoint) {
        System.out.println("Navigating from " + startPoint + " to " + endPoint + " by driving.");
    }
}

class ThroughBus implements NavigationStrategy {
    public void navigate(String startPoint, String endPoint) {
        System.out.println("Navigating from " + startPoint + " to " + endPoint + " by travelling through bus.");
    }
}

class Walking implements NavigationStrategy {
    public void navigate(String startPoint, String endPoint) {
        System.out.println("Navigating from " + startPoint + " to " + endPoint + " by walking.");
    }
}


class NavigationContext {
    private NavigationStrategy strategy;

    public void setStrategy(NavigationStrategy strategy) {
        this.strategy = strategy;
    }

    public void navigate(String startPoint, String endPoint) {
        strategy.navigate(startPoint, endPoint);
    }
}

public class BehaviouralStrategyPattern {
    public static void main(String[] args) {
        NavigationContext context = new NavigationContext();

        context.setStrategy(new ThroughLocalTrain());
        context.navigate("Home", "Office");

        context.setStrategy(new ThroughBus());
        context.navigate("office","park");

        context.setStrategy(new Walking());
        context.navigate("Park", "Gym");

        
    }
}
