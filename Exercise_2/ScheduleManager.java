import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScheduleManager {
    private static ScheduleManager instance;
    private List<Task> tasks;

    private ScheduleManager() {
        tasks = new ArrayList<>();
    }

    public static synchronized ScheduleManager getInstance() {
        if (instance == null) {
            instance = new ScheduleManager();
        }
        return instance;
    }

    //add the task
    public String addTask(Task newTask) {
        List<Task> conflictingTasks = findConflictingTasks(newTask);

        if (!conflictingTasks.isEmpty()) {
            if (newTask.getPriority().equalsIgnoreCase("High")) {
                boolean canReplace = conflictingTasks.stream()
                        .allMatch(t -> !t.getPriority().equalsIgnoreCase("High"));
                if (canReplace) {
                    return handleTaskConflict(newTask, conflictingTasks);
                } else {
                    return "Error: Task conflicts with existing high-priority task(s).";
                }
            } else if (newTask.getPriority().equalsIgnoreCase("Medium")) {
                boolean canReplace = conflictingTasks.stream()
                        .allMatch(t -> t.getPriority().equalsIgnoreCase("Low"));
                if (canReplace) {
                    return handleTaskConflict(newTask, conflictingTasks);
                } else {
                    return "Error: Task conflicts with existing task(s) of equal or higher priority.";
                }
            } else {
                return "Error: Task conflicts with existing task(s).";
            }
        } else {
            tasks.add(newTask);
            return "Task added successfully. No conflicts.";
        }
    }

    //checks for higher priority if the conflicts occurs
    private boolean isHigherPriority(Task newTask, Task existingTask) {
        return getPriorityValue(newTask) > getPriorityValue(existingTask);
    }

    //returns the priority level value
    private int getPriorityValue(Task task) {
        switch (task.getPriority()) {
            case "High": return 3;
            case "Medium": return 2;
            case "Low": return 1;
            default: return 0;
        }
    }

    //if the newly given task creats time conflict then ask the user to update the task to new time slot
    private String handleTaskConflict(Task newTask, List<Task> conflictingTasks) {
        return String.format(
            "Conflict with existing task(s) \"%s\". New task priority is higher. Do you want to replace the existing tasks with the new priority? (yes/no):",
            conflictingTasks.stream().map(Task::getDescription).collect(Collectors.joining(", "))
        );
    }

    //update the task
    public String updateTask(Task existingTask, Task newTask) {
        for (Task task : tasks) {
            if (task.getStartTime().equals(existingTask.getStartTime()) && task.getEndTime().equals(existingTask.getEndTime())) {
                task.setDescription(newTask.getDescription());
                task.setPriority(newTask.getPriority());
                task.setEndTime(newTask.getEndTime());
                task.setStartTime(newTask.getStartTime());
                return "Task updated successfully.";
            }
        }
        return "Error: Task not found.";
    }

    //remove the task
    public String removeTask(String description) {
        Task toRemove = null;
        for (Task task : tasks) {
            if (task.getDescription().equals(description)) {
                toRemove = task;
                break;
            }
        }
        if (toRemove != null) {
            tasks.remove(toRemove);
            return "Task removed successfully.";
        }
        return "Error: Task not found.";
    }

    public List<Task> getTasks() {
        return tasks;
    }

    //View task
    public String viewTasks() {
        if (tasks.isEmpty()) {
            return "No tasks scheduled for the day.";
        }
        Collections.sort(tasks, Comparator.comparing(Task::getStartTime));
        StringBuilder sb = new StringBuilder();
        for (Task task : tasks) {
            sb.append(task.toString()).append("\n");
        }
        return sb.toString();
    }

    //view task using priority level
    public List<Task> getTasksByPriority(String priority) {
        return tasks.stream()
                .filter(task -> task.getPriority().equalsIgnoreCase(priority))
                .sorted(Comparator.comparing(Task::getStartTime))
                .collect(Collectors.toList());
    }

    //if the user needs to replace the old task with new task
    public void replaceTasks(Task newTask, List<Task> conflictingTasks) {
        tasks.removeAll(conflictingTasks);
        tasks.add(newTask);
    }

    //checks the time overlaps
    private List<Task> findConflictingTasks(Task newTask) {
        return tasks.stream()
                .filter(newTask::overlapsWith)
                .collect(Collectors.toList());
    }
}
