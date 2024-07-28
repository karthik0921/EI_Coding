
public class CreationalSingletonPattern {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        Logger logger3 = Logger.getInstance();

        logger1.log("This is the first log message.");
        logger2.log("This is the second log message.");
        logger3.log("This is the third log message.");
        logger1.log("This is the fourth log message.");

        System.out.println(logger1 == logger2);
        System.out.println(logger1 == logger3);  
    }
}
