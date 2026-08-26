package arin.task;

/**
 * Represents a task without a date or time.
 */
public class Todo extends Task {
    /**
     * Creates a to-do task with no associated date or time.
     *
     * @param description text describing the task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns this to-do task in the storage-file format.
     *
     * @return storage text prefixed with the to-do task type
     */
    @Override
    public String storageToString() {
        return "T" + super.storageToString();
    }

    /**
     * Returns this to-do task in the display format.
     *
     * @return display text prefixed with the to-do task type
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
