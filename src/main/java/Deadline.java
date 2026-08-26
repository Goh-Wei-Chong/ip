/**
 * Represents a task that must be completed by a specified date and time.
 */
public class Deadline extends Task {
    private final String datetime;
    public Deadline(String description, String datetime) {
        super(description);
        this.datetime = datetime;
    }

    @Override
    public String storageToString() {
        return "D" + super.storageToString() + " | " + this.datetime;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + this.datetime + ")";
    }
}
