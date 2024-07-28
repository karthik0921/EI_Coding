import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


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
