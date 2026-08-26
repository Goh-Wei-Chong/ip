import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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
                new Deadline("return book", "June 6th"),
                new Event("project meeting", "Aug 6th 2pm", "Aug 6th 4pm")
        ));

        List<String> expected = List.of(
                "T | 1 | read book",
                "D | 0 | return book | June 6th",
                "E | 0 | project meeting | Aug 6th 2pm | Aug 6th 4pm"
        );
        List<String> actual = Files.readAllLines(testFile);

        if (!expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }

        Files.delete(testFile);
        Files.delete(testDirectory);
        System.out.println("StorageTest passed");
    }
}
