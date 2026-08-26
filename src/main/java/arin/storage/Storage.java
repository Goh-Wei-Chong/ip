package arin.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import arin.task.Task;

/**
 * Saves Arin's task list to a text file.
 */
public class Storage {
    private static final Path DEFAULT_FILE_PATH = Path.of("data", "arin.txt");

    private final Path filePath;

    /**
     * Creates storage that saves tasks in the project's data directory.
     */
    public Storage() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Creates storage that saves tasks at a specified path.
     * This constructor makes the writing behaviour straightforward to test.
     *
     * @param filePath path of the task file
     */
    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves all tasks, replacing the previously saved list.
     *
     * @param tasks tasks to save
     * @throws IOException if the task file cannot be written
     */
    public void saveTasks(List<Task> tasks) throws IOException {
        Path parentDirectory = filePath.getParent();
        if (parentDirectory != null) {
            Files.createDirectories(parentDirectory);
        }

        List<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(formatTask(task));
        }
        Files.write(filePath, lines);
    }

    /**
     * Converts a task to one line of the Level 7 storage format.
     *
     * @param task task to format
     * @return formatted task line
     */
    private String formatTask(Task task) {
        return task.storageToString();
    }
}
