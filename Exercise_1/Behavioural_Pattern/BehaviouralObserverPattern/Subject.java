import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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