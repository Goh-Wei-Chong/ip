package arin.storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import arin.task.Deadline;
import arin.task.Event;
import arin.task.Todo;

/**
 * Regression checks for Level 7 task-file writing.
 */
public class StorageTest {
    /**
     * Writes each task type and verifies the required file format.
     *
     * @param args command-line arguments, which are not used
     * @throws Exception if the test setup or assertion fails
     */
    public static void main(String[] args) throws Exception {
        Path testDirectory = Files.createTempDirectory("arin-storage-test");
        Path testFile = testDirectory.resolve("arin.txt");

        Todo todo = new Todo("read book");
        todo.markTask();
        Storage storage = new Storage(testFile);
        storage.saveTasks(List.of(
                todo,
                new Deadline("return book", LocalDate.of(2019, 12, 2)),
                new Event("project meeting", "Aug 6th 2pm", "Aug 6th 4pm")
        ));

        List<String> expected = List.of(
                "T | X | read book",
                "D |   | return book | 2019-12-02",
                "E |   | project meeting | Aug 6th 2pm Aug 6th 4pm"
        );
        List<String> actual = Files.readAllLines(testFile);

        if (!expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }

        Deadline deadline = new Deadline("return book", LocalDate.of(2019, 12, 2));
        String expectedDisplay = "[D][ ] return book (by: Dec 02 2019)";
        if (!expectedDisplay.equals(deadline.toString())) {
            throw new AssertionError("Expected " + expectedDisplay + " but got " + deadline);
        }

        Files.delete(testFile);
        Files.delete(testDirectory);
        System.out.println("StorageTest passed");
    }
}
