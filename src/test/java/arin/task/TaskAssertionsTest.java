package arin.task;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies internal task-data assumptions with Java assertions enabled.
 */
class TaskAssertionsTest {
    @Test
    void constructor_nullDescription_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Todo(null));
    }

    @Test
    void constructor_blankDescription_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Todo(" \t "));
    }

    @Test
    void constructor_nullDeadlineDate_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Deadline("return book", null));
    }

    @Test
    void constructor_invalidEventStart_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Event("meeting", null, "4pm"));
        assertThrows(AssertionError.class, () -> new Event("meeting", " ", "4pm"));
    }

    @Test
    void constructor_invalidEventEnd_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new Event("meeting", "2pm", null));
        assertThrows(AssertionError.class, () -> new Event("meeting", "2pm", " "));
    }
}
