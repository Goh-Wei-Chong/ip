package arin.task;

/**
 * Represents a task that occurs between a start and end time.
 */
public class Event extends Task {
    private final String start;
    private final String end;

    /**
     * Creates an event task with a start and end time description.
     *
     * @param description text describing the event
     * @param start event start time
     * @param end event end time
     */
    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns this event in the storage-file format.
     *
     * @return storage text prefixed with the event task type and times
     */
    @Override
    public String storageToString() {
        return "E" + super.storageToString() + " | " + this.start + " " + this.end;
    }

    /**
     * Returns this event in the display format.
     *
     * @return display text prefixed with the event task type and times
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.start + " to: " + this.end + ")";
    }
}
