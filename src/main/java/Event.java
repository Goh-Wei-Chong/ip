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

    @Override
    public String storageToString() {
        return "E" + super.storageToString() + " | " + this.start + " " + this.end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.start + " to: " + this.end + ")";
    }
}
