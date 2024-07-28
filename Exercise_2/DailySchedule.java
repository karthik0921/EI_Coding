import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DailySchedule {
    private static ScheduleManager manager = ScheduleManager.getInstance();
    private static TaskFactory factory = new TaskFactory();
    private static final Logger logger = Logger.getLogger(DailySchedule.class.getName());

    //Main function
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            System.out.println("Add Task(\"Task name\", \"HH:MM\", \"HH:MM\", \"Priority\") or Remove Task(\"Task name\") or View Tasks or View Tasks Priority(\"Priority\") or Exit ");
            System.out.println("Enter command:");
            input = reader.readLine();
            processCommand(input, reader);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred", e);
        }
    }

    //Function executes till the user gives "Exit"
    private static void processCommand(String input, BufferedReader reader) throws Exception {
        if (input.equalsIgnoreCase("Exit")) {
            logger.info("\nExiting application.");
            return;
        }

        if (input.startsWith("Add Task")) {
            handleAddTask(input, reader);
        } else if (input.startsWith("Remove Task")) {
            handleRemoveTask(input);
        } else if (input.equals("View Tasks")) {
            handleViewTasks();
        } else if (input.startsWith("View Tasks Priority")) {
            handleViewTasksByPriority(input);
        } else {
            System.out.println("Invalid command. Please try again.\n");
            logger.warning("Invalid command: " + input);
        }

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Add Task(\"Task name\", \"HH:MM\", \"HH:MM\", \"Priority\") or Remove Task(\"Task name\") or View Tasks or View Tasks Priority(\"Priority\") or Exit ");
        System.out.println("Enter command:");
        input = reader.readLine();
        processCommand(input, reader);
    }

    //Task will be added here 
    private static void handleAddTask(String input, BufferedReader reader) throws Exception {
        Pattern pattern = Pattern.compile("Add Task\\(\"(.*?)\", \"(.*?)\", \"(.*?)\", \"(.*?)\"\\)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String description = matcher.group(1);
            String startTimeStr = matcher.group(2);
            String endTimeStr = matcher.group(3);
            String priority = matcher.group(4);

            //Task description validation
            if (!isValidDescription(description)) {
                System.out.println("Error: Invalid task description. Please enter a valid name.\n");
                logger.warning("Invalid task description: " + description);
                return;
            }

            //Start time and end time validation
            if (!isValidTimeFormat(startTimeStr) || !isValidTimeFormat(endTimeStr)) {
                System.out.println("Error: Invalid time format. Please enter time in HH:MM format.\n");
                logger.warning("Invalid time format: " + startTimeStr + " - " + endTimeStr);
                return;
            }

            //checks start time and end time are correct
            try {
                LocalTime startTime = LocalTime.parse(startTimeStr);
                LocalTime endTime = LocalTime.parse(endTimeStr);

                if (startTime.isAfter(endTime)) {
                    System.out.println("Error: Start time must be before end time.\n");
                    logger.warning("Start time is after end time: " + startTimeStr + " - " + endTimeStr);
                    return;
                }

                Task newTask = factory.createTask(description, startTimeStr, endTimeStr, priority);
                String result = manager.addTask(newTask);
                //Checks the conflicts with the start and end time
                if (result.startsWith("Conflict")) {
                    System.out.println(result);
                    System.out.println("Do you want to replace the existing tasks with the new task? (yes/no):");
                    String confirmation = reader.readLine();
                    if (confirmation.equalsIgnoreCase("yes")) {
                        List<Task> conflictingTasks = findConflictingTasks(newTask);
                        if (conflictingTasks != null) {
                            manager.replaceTasks(newTask, conflictingTasks);
                            System.out.println("New task added and existing conflicting tasks removed.\n");
                            logger.info("New task added and existing conflicting tasks removed: " + newTask);
                        } else {
                            System.out.println("Error: Could not find the conflicting tasks.\n");
                            logger.warning("Could not find conflicting tasks for: " + newTask);
                        }
                    } else{
                        System.out.println("New task was not added.");

                    }
                }else if(result.equals("Error: Task conflicts with existing task(s) of equal or higher priority.")){
                    System.out.println(result + "\n");
                    logger.info(" No Task added: " + newTask);
                } else {
                    System.out.println(result + "\n");
                    logger.info("Task added: " + newTask);
                }
            } catch (DateTimeException e) {
                System.out.println("Error: Invalid time format.\n");
                logger.log(Level.SEVERE, "DateTimeException occurred", e);
            }
        } else {
            System.out.println("Error: Invalid command format.\n");
            logger.warning("Invalid command format for Add Task: " + input);
        }
    }

    //Remove the task
    private static void handleRemoveTask(String input) {
        Pattern pattern = Pattern.compile("Remove Task\\(\"(.*?)\"\\)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String description = matcher.group(1);
            String result = manager.removeTask(description);
            System.out.println(result + "\n");
            if (result.startsWith("Error")) {
                logger.warning(result);
            } else {
                logger.info("Task removed: " + description);
            }
        } else {
            System.out.println("Error: Invalid command format.\n");
            logger.warning("Invalid command format for Remove Task: " + input);
        }
    }

    //View Tasks
    private static void handleViewTasks() {
        String tasks = manager.viewTasks();
        System.out.println(tasks + "\n");
        if (tasks.contains("No tasks")) {
            logger.info("No tasks scheduled for the day.");
        } else {
            logger.info("Tasks listed.");
        }
    }

    //view tasks using priority level
    private static void handleViewTasksByPriority(String input) {
        Pattern pattern = Pattern.compile("View Tasks Priority\\(\"(.*?)\"\\)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String priority = matcher.group(1);

            List<Task> tasks = manager.getTasksByPriority(priority);
            if (tasks.isEmpty()) {
                System.out.println("No tasks found with priority " + priority + "\n");
                logger.info("No tasks found with priority: " + priority);
            } else {
                tasks.forEach(task -> System.out.println(task.toString()));
                logger.info("\nTasks listed by priority: " + priority);
            }
        } else {
            System.out.println("Error: Invalid command format.\n");
            logger.warning("Invalid command format for View Tasks Priority: " + input);
        }
    }

    //checks task description in right format
    private static boolean isValidDescription(String description) {
        return description != null && description.matches("[a-zA-Z0-9 ]+");
    }

    //checks start and end time in right format    
    private static boolean isValidTimeFormat(String timeStr) {
        Pattern pattern = Pattern.compile("^([01]\\d|2[0-3]):([0-5]\\d)$");
        Matcher matcher = pattern.matcher(timeStr);
        return matcher.matches();
    }

    //finding the conflict occurs for the given time interval
    private static List<Task> findConflictingTasks(Task newTask) {
        return manager.getTasks().stream()
                .filter(newTask::overlapsWith)
                .collect(Collectors.toList());
    }
}
