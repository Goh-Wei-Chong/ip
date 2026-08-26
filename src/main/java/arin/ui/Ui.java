package arin.ui;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import arin.task.Task;

/**
 * Handles all console input and output for Arin.
 */
public class Ui {
    private static final String DIVIDER = "________________________________";

    private final Scanner scanner;
    private final PrintStream output;

    /**
     * Creates a UI that reads from standard input and writes to standard output.
     */
    public Ui() {
        this(new Scanner(System.in), System.out);
    }

    /**
     * Creates a UI with specified input and output streams.
     *
     * @param scanner source of user commands
     * @param output destination for messages
     */
    public Ui(Scanner scanner, PrintStream output) {
        this.scanner = scanner;
        this.output = output;
    }

    /**
     * Displays Arin's welcome message.
     */
    public void showWelcome() {
        output.println(DIVIDER);
        output.println("    _         _       ");
        output.println("   / \\   _ __(_)_ __  ");
        output.println("  / _ \\ | '__| | '_ \\ ");
        output.println(" / ___ \\| |  | | | | |");
        output.println("/_/   \\_\\_|  |_|_| |_|");
        output.println("Good afternoon.");
        output.println("My name is Arin.");
        output.println("And I am a Wilderness Explorer in Tribe 54, Sweat Lodge 12.");
        output.println("Are you in need of any assistance today, sir?");
        output.println(DIVIDER);
    }

    /**
     * Reads one complete command entered by the user.
     *
     * @return the entered command
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays all tasks with their one-based positions.
     *
     * @param tasks tasks to display
     */
    public void showTaskList(List<Task> tasks) {
        output.println(DIVIDER);
        output.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            output.println((i + 1) + ". " + tasks.get(i));
        }
        output.println(DIVIDER);
    }

    /** Displays confirmation that a task was marked complete. */
    public void showMarkedTask(Task task) {
        showTaskMessage("Nice! I've marked this task as done:", task);
    }

    /** Displays confirmation that a task was marked incomplete. */
    public void showUnmarkedTask(Task task) {
        showTaskMessage("OK, I've marked this task as not done yet:", task);
    }

    /** Displays confirmation that a task was added. */
    public void showAddedTask(Task task, int taskCount) {
        output.println(DIVIDER);
        output.println("Got it. I've added this task:");
        output.println(task);
        output.println("Now you have " + taskCount + " tasks in the list.");
        output.println(DIVIDER);
    }

    /** Displays confirmation that a task was deleted. */
    public void showDeletedTask(Task task, int taskCount) {
        output.println(DIVIDER);
        output.println("Noted. I've removed this task:");
        output.println("  " + task);
        output.println("Now you have " + taskCount + " tasks in the list.");
        output.println(DIVIDER);
    }

    /** Displays an error message to the user. */
    public void showError(String message) {
        output.println(DIVIDER);
        output.println("Oops! " + message);
        output.println(DIVIDER);
    }

    /** Displays Arin's goodbye message. */
    public void showGoodbye() {
        output.println(DIVIDER);
        output.println("Bye. Hope to see you again soon!");
        output.println(DIVIDER);
    }

    /** Displays a task message surrounded by the standard divider. */
    private void showTaskMessage(String message, Task task) {
        output.println(DIVIDER);
        output.println(message);
        output.println(task);
        output.println(DIVIDER);
    }
}
