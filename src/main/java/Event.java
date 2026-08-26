/**
 * Represents a task that occurs between a start and end time.
 */
public class Event extends Task {
    private final String start;
    private final String end;
    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the event start text used when saving this task.
     *
     * @return the event start text
     */
    public String getStart() {
        return start;
    }

    /**
     * Returns the event end text used when saving this task.
     *
     * @return the event end text
     */
    public String getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.start + " to: " + this.end + ")";
    }
}
