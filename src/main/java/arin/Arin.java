package arin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import arin.command.Command;
import arin.command.ExitCommand;
import arin.exception.ArinException;
import arin.parser.TaskParser;
import arin.storage.Storage;
import arin.task.Task;
import arin.ui.Ui;

/**
 * Coordinates task commands, console feedback, and persistent storage.
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
                    Task task = TaskParser.parse(command);
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
     * Returns tasks whose descriptions contain the keyword, ignoring letter case.
     *
     * @param tasks Tasks to search.
     * @param keyword Keyword to find in task descriptions.
     * @return Tasks whose descriptions contain the keyword.
     */
    static List<Task> findTasks(List<Task> tasks, String keyword) {
        String lowercaseKeyword = keyword.toLowerCase(Locale.ROOT);
        return tasks.stream()
                .filter(task -> task.getDescription().toLowerCase(Locale.ROOT).contains(lowercaseKeyword))
                .collect(Collectors.toCollection(ArrayList::new));
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
