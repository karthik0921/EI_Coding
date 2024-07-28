import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface Observer {
    void update(String message);
}

class Subject {
    private List<Observer> observers = new ArrayList<>();
    private String message;

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void setMessage(String message) {
        this.message = message;
        notifyAllObservers();
    }

    private void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
    public void receiveMessages() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a message (or type 'exit' to quit): ");
        String input = scanner.nextLine();

        if ("exit".equalsIgnoreCase(input)) {
            System.out.println("Exiting...");
        } else {
            setMessage(input);
            receiveMessages();  
        }
    }
}

class ConcreteObserver1 implements Observer {
    private String name;

    public ConcreteObserver1(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " received message: " + message);
    }
}

class ConcreteObserver2 implements Observer {
    private String name;

    public ConcreteObserver2(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " received message: " + message);
    }
}

public class BehaviouralObserverPattern {
    public static void main(String[] args) {
        Subject subject = new Subject();

        Observer observer1 = new ConcreteObserver1("Observer 1");
        Observer observer2 = new ConcreteObserver2("Observer 2");

        subject.attach(observer1);
        subject.attach(observer2);

        subject.receiveMessages();  
    }
}
