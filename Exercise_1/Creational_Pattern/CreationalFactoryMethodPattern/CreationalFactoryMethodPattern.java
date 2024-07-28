import java.util.Scanner;

public class CreationalFactoryMethodPattern {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        promptUser(scanner);
    }

    private static void promptUser(Scanner scanner) {
        System.out.println("Enter the type of bus (private/government) or 'exit' to quit:");
        String busType = scanner.nextLine();

        if (busType.equalsIgnoreCase("exit")) {
            System.out.println("Exiting the program. Goodbye!");
            return; 
        }

        System.out.println("Enter the passenger name:");
        String passengerName = scanner.nextLine();

        BusFactory busFactory;

        if (busType.equalsIgnoreCase("private")) {
            busFactory = new PrivateBusFactory();
        } else if (busType.equalsIgnoreCase("government")) {
            busFactory = new GovernmentBusFactory();
        } else {
            System.out.println("Invalid bus type. Please enter 'private' or 'government'.");
            promptUser(scanner); 
            return;
        }

        busFactory.bookBusSeat(passengerName);
        promptUser(scanner); 
    }
}
