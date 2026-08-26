package arin.task;

/**
 * Represents a task without a date or time.
 */
public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String storageToString() {
        return "T" + super.storageToString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
