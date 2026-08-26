package arin.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a task that must be completed by a specified date and time.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter DISPLAY_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    private final LocalDate date;

    /**
     * Creates a deadline task with a calendar date.
     *
     * @param description description of the task
     * @param date deadline date
     */
    public Deadline(String description, LocalDate date) {
        super(description);
        this.date = date;
    }

    /**
     * Returns this deadline in the storage-file format, including its ISO date.
     *
     * @return storage text prefixed with the deadline task type and date
     */
    @Override
    public String storageToString() {
        return "D" + super.storageToString() + " | " + date;
    }

    /**
     * Returns this deadline in the display format, including its formatted date.
     *
     * @return display text prefixed with the deadline task type and date
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + date.format(DISPLAY_FORMAT) + ")";
    }
}
