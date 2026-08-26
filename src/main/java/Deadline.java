/**
 * Represents a task that must be completed by a specified date and time.
 */
public class Deadline extends Task {
    private final String datetime;
    public Deadline(String description, String datetime) {
        super(description);
        this.datetime = datetime;
    }

    /**
     * Returns the deadline text used when saving this task.
     *
     * @return the deadline text
     */
    public String getDatetime() {
        return datetime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + this.datetime + ")";
    }
}
