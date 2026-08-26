/**
 * Represents a task tracked by Arin.
 */
public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the description used when saving this task.
     *
     * @return this task's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether this task has been completed.
     *
     * @return true if this task is complete
     */
    public boolean isDone() {
        return isDone;
    }

    public void markTask() {
        this.isDone = true;
    }
    public void unmarkTask() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + description;
    }
}
