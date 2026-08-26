package arin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import arin.task.Deadline;
import arin.task.Event;
import arin.task.Task;
import arin.task.Todo;

/**
 * Tests task searching in {@link Arin}.
 */
class ArinTest {
    @Test
    void findTasks_matchingKeyword_returnsAllMatchingTasksInOriginalOrder() {
        Task readBook = new Todo("read book");
        Task returnBook = new Deadline("return book", LocalDate.of(2026, 6, 6));
        Task projectMeeting = new Event("project meeting", "2pm", "4pm");

        List<Task> matchingTasks = Arin.findTasks(
                List.of(readBook, returnBook, projectMeeting), "book");

        assertEquals(List.of(readBook, returnBook), matchingTasks);
    }

    @Test
    void findTasks_keywordWithDifferentCase_returnsMatchingTasks() {
        Task readBook = new Todo("Read Book");

        List<Task> matchingTasks = Arin.findTasks(List.of(readBook), "bOoK");

        assertEquals(List.of(readBook), matchingTasks);
    }

    @Test
    void findTasks_noMatchingDescriptions_returnsEmptyList() {
        List<Task> matchingTasks = Arin.findTasks(List.of(new Todo("read book")), "meeting");

        assertEquals(List.of(), matchingTasks);
    }
}
