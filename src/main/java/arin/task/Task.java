package arin.task;

/**
 * Represents a task tracked by Arin.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates an incomplete task with the specified description.
     *
     * @param description text describing the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the symbol used to display this task's completion state.
     *
     * @return {@code X} when complete, or a space when incomplete
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /** Marks this task as complete. */
    public void markTask() {
        this.isDone = true;
    }

    /** Marks this task as incomplete. */
    public void unmarkTask() {
        this.isDone = false;
    }

    /**
     * Converts this task into its common storage-file representation.
     *
     * @return storage text containing the completion state and description
     */
    public String storageToString() {
        return " | " + this.getStatusIcon() + " | " + this.description;
    }

    /**
     * Returns the task text shown to the user.
     *
     * @return formatted task description and completion state
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
