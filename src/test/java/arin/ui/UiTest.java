package arin.ui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import arin.task.Todo;

/**
 * Regression checks for UI messages used by Arin.
 */
public class UiTest {
    /**
     * Verifies that the UI reads commands and labels list entries correctly.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Ui ui = new Ui(new Scanner("todo read book\n"), new PrintStream(buffer));

        if (!"todo read book".equals(ui.readCommand())) {
            throw new AssertionError("Ui did not read the entered command.");
        }

        ui.showTaskList(List.of(new Todo("read book")));
        String expected = "________________________________" + System.lineSeparator()
                + "Here are the tasks in your list:" + System.lineSeparator()
                + "1. [T][ ] read book" + System.lineSeparator()
                + "________________________________" + System.lineSeparator();
        if (!expected.equals(buffer.toString())) {
            throw new AssertionError("Unexpected list output: " + buffer);
        }

        System.out.println("UiTest passed");
    }
}
