import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A chatbot that echoes commands until the user ends the conversation.
 */
public class Arin {
    /**
     * Starts Arin and processes commands from standard input.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        String banner = "________________________________\n" +
                "    _         _       \n" +
                "   / \\   _ __(_)_ __  \n" +
                "  / _ \\ | '__| | '_ \\ \n" +
                " / ___ \\| |  | | | | |\n" +
                "/_/   \\_\\_|  |_|_| |_|\n" +
                "Good afternoon.\n"
                + "My name is Arin.\n"
                + "And I am a Wilderness Explorer in Tribe 54, Sweat Lodge 12.\n"
                + "Are you in need of any assistance today, sir?\n" +
                "________________________________";

        System.out.println(banner);

        Scanner scanner = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();
        Storage storage = new Storage();

        while (true) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                break;
            }

            try {
                if (command.equals("list")) {
                    System.out.println("________________________________");
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    System.out.println("________________________________");
                } else if (command.equals("mark") || command.startsWith("mark ")) {
                    int taskNumber = getTaskNumber(command, "mark", tasks.size());
                    tasks.get(taskNumber).markTask();
                    storage.saveTasks(tasks);
                    System.out.println("________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(tasks.get(taskNumber));
                    System.out.println("________________________________");
                } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                    int taskNumber = getTaskNumber(command, "unmark", tasks.size());
                    tasks.get(taskNumber).unmarkTask();
                    storage.saveTasks(tasks);
                    System.out.println("________________________________");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(tasks.get(taskNumber));
                    System.out.println("________________________________");
                } else if (command.equals("delete") || command.startsWith("delete ")) {
                    int taskNumber = getTaskNumber(command, "delete", tasks.size());
                    Task removedTask = tasks.remove(taskNumber);
                    storage.saveTasks(tasks);
                    System.out.println("________________________________");
                    System.out.println("Noted. I've removed this task:");
                    System.out.println("  " + removedTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("________________________________");
                } else {
                    Task task = createTask(command);
                    tasks.add(task);
                    storage.saveTasks(tasks);
                    System.out.println("________________________________");
                    System.out.println("Got it. I've added this task:\n" + task);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    System.out.println("________________________________");
                }
            } catch (ArinException | IOException e) {
                System.out.println("________________________________");
                System.out.println("Oops! " + e.getMessage());
                System.out.println("________________________________");
            }
        }

        System.out.println("________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("________________________________");
    }

    /**
     * Creates a task from a valid task-creation command.
     *
     * @param command the command entered by the user
     * @return the task represented by the command
     * @throws ArinException if the command is unknown or missing required details
     */
    private static Task createTask(String command) throws ArinException {
        if (command.equals("todo") || command.startsWith("todo ")) {
            String description = command.substring(4).trim();
            if (description.isEmpty()) {
                throw new ArinException("Please include a description after 'todo'.");
            }
            return new Todo(description);
        }

        if (command.equals("deadline") || command.startsWith("deadline ")) {
            String details = command.substring(8).trim();
            int byIndex = details.indexOf(" /by ");
            if (byIndex == -1) {
                throw new ArinException("Use: deadline <description> /by <date and time>.");
            }

            String description = details.substring(0, byIndex).trim();
            String dateText = details.substring(byIndex + 5).trim();
            if (description.isEmpty() || dateText.isEmpty()) {
                throw new ArinException("A deadline needs both a description and a due date.");
            }

            try {
                LocalDate date = LocalDate.parse(dateText);
                return new Deadline(description, date);
            } catch (DateTimeParseException e) {
                throw new ArinException("Please use the date format yyyy-MM-dd, for example 2019-10-15.");
            }
        }

        if (command.equals("event") || command.startsWith("event ")) {
            String details = command.substring(5).trim();
            int fromIndex = details.indexOf(" /from ");
            int toIndex = details.indexOf(" /to ");
            if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                throw new ArinException("Use: event <description> /from <start> /to <end>.");
            }

            String description = details.substring(0, fromIndex).trim();
            String start = details.substring(fromIndex + 7, toIndex).trim();
            String end = details.substring(toIndex + 5).trim();
            if (description.isEmpty() || start.isEmpty() || end.isEmpty()) {
                throw new ArinException("An event needs a description, start time, and end time.");
            }
            return new Event(description, start, end);
        }

        throw new ArinException("I don't recognise that command. Try todo, deadline, event, list, mark, unmark, delete, or bye.");
    }

    /**
     * Validates and converts the task number in a command.
     *
     * @param command the command entered by the user
     * @param commandName the command's name
     * @param numberOfTasks number of tasks currently stored
     * @return the zero-based index of the requested task
     * @throws ArinException if the task number is missing, invalid, or out of range
     */
    private static int getTaskNumber(String command, String commandName, int numberOfTasks)
            throws ArinException {
        String numberText = command.substring(commandName.length()).trim();
        try {
            int taskNumber = Integer.parseInt(numberText);
            if (taskNumber < 1 || taskNumber > numberOfTasks) {
                throw new ArinException("There is no task numbered " + numberText + ".");
            }
            return taskNumber - 1;
        } catch (NumberFormatException e) {
            throw new ArinException("Use: " + commandName + " <task number>.");
        }
    }
}
