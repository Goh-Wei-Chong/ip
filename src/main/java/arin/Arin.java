package arin;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import arin.command.Command;
import arin.command.ExitCommand;
import arin.exception.ArinException;
import arin.storage.Storage;
import arin.task.Deadline;
import arin.task.Event;
import arin.task.Task;
import arin.task.Todo;
import arin.ui.Ui;

/**
 * A chatbot that echoes commands until the user ends the conversation.
 */
public class Arin {
    /**
     * Starts Arin and processes commands from standard input.
     *
     * @param args Command-line arguments, which are not used.
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();
        List<Task> tasks = new ArrayList<>();
        Storage storage = new Storage();

        boolean isExit = false;
        while (!isExit) {
            String command = ui.readCommand();

            try {
                if (command.equals("bye")) {
                    Command exitCommand = new ExitCommand();
                    exitCommand.execute(tasks, ui, storage);
                    isExit = exitCommand.isExit();
                } else if (command.equals("list")) {
                    ui.showTaskList(tasks);
                } else if (command.equals("mark") || command.startsWith("mark ")) {
                    int taskNumber = getTaskNumber(command, "mark", tasks.size());
                    tasks.get(taskNumber).markTask();
                    storage.saveTasks(tasks);
                    ui.showMarkedTask(tasks.get(taskNumber));
                } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                    int taskNumber = getTaskNumber(command, "unmark", tasks.size());
                    tasks.get(taskNumber).unmarkTask();
                    storage.saveTasks(tasks);
                    ui.showUnmarkedTask(tasks.get(taskNumber));
                } else if (command.equals("delete") || command.startsWith("delete ")) {
                    int taskNumber = getTaskNumber(command, "delete", tasks.size());
                    Task removedTask = tasks.remove(taskNumber);
                    storage.saveTasks(tasks);
                    ui.showDeletedTask(removedTask, tasks.size());
                } else if (command.equals("find") || command.startsWith("find ")) {
                    String keyword = command.substring("find".length()).trim();
                    if (keyword.isEmpty()) {
                        throw new ArinException("Use: find <keyword>.");
                    }
                    ui.showMatchingTasks(findTasks(tasks, keyword));
                } else {
                    Task task = createTask(command);
                    tasks.add(task);
                    storage.saveTasks(tasks);
                    ui.showAddedTask(task, tasks.size());
                }
            } catch (ArinException | IOException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Creates a task from a valid task-creation command.
     *
     * @param command Command entered by the user.
     * @return Task represented by the command.
     * @throws ArinException If the command is unknown or missing required details.
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

        throw new ArinException("I don't recognise that command. Try todo, deadline, event, list, mark, unmark, "
                + "delete, find, or bye.");
    }

    /**
     * Returns tasks whose descriptions contain the keyword, ignoring letter case.
     *
     * @param tasks Tasks to search.
     * @param keyword Keyword to find in task descriptions.
     * @return Tasks whose descriptions contain the keyword.
     */
    static List<Task> findTasks(List<Task> tasks, String keyword) {
        List<Task> matchingTasks = new ArrayList<>();
        String lowercaseKeyword = keyword.toLowerCase(Locale.ROOT);
        for (Task task : tasks) {
            if (task.getDescription().toLowerCase(Locale.ROOT).contains(lowercaseKeyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }

    /**
     * Validates and converts the task number in a command.
     *
     * @param command Command entered by the user.
     * @param commandName Command's name.
     * @param numberOfTasks Number of tasks currently stored.
     * @return Zero-based index of the requested task.
     * @throws ArinException If the task number is missing, invalid, or out of range.
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
