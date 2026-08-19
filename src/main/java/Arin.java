import java.util.Scanner;

/**
 * A chatbot that echoes commands until the user ends the conversation.
 */
public class Arin {
    /**
     * Starts Arin and processes commands from standard input.
     *
     * @param args command-line arguments, which are not used
     */
    public static void main(String[] args) {
        String banner = "________________________________\n" +
                "    _         _       \n" +
                "   / \\   _ __(_)_ __  \n" +
                "  / _ \\ | '__| | '_ \\ \n" +
                " / ___ \\| |  | | | | |\n" +
                "/_/   \\_\\_|  |_|_| |_|\n" +
                "Good afternoon.\n"
                + "My name is Arin.\n"
                + "And I am a Wilderness Explorer in Tribe 54, Sweat Lodge 12.\n"
                + "Are you in need of any assistance today, sir?\n" +
                "________________________________";

        System.out.println(banner);

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int numberOfTasks = 0;
        String description, datetime, start, end;

        while (numberOfTasks < 100) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                break;
            } else if (command.equals("list")) {
                System.out.println("________________________________");
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < numberOfTasks; i++) {
                    System.out.println((i + 1) + ". " + tasks[i].toString());
                }
                System.out.println("________________________________");
            } else if (command.startsWith("mark")) {
                String part = command.substring(5);
                int n = Integer.parseInt(part) - 1;
                tasks[n].markTask();
                System.out.println("________________________________");
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(tasks[n].toString());
                System.out.println("________________________________");
            } else if (command.startsWith("unmark")) {
                String part = command.substring(7);
                int n = Integer.parseInt(part) - 1;
                tasks[n].unmarkTask();
                System.out.println("________________________________");
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(tasks[n].toString());
                System.out.println("________________________________");
            }

            else {
                if (command.startsWith("todo")) {
                    description = command.substring(5);
                    tasks[numberOfTasks] = new Todo(description);
                }
                if (command.startsWith("deadline")) {
                    description = command.substring(9, command.indexOf("/"));
                    datetime = command.substring(command.indexOf("/")+4);
                    tasks[numberOfTasks] = new Deadline(description, datetime);
                }
                if (command.startsWith("event")) {
                    int first = command.indexOf("/");
                    int second = command.indexOf("/", first+1);
                    description = command.substring(6, first);
                    start = command.substring(first+6, second-1);
                    end = command.substring(second+4);
                    tasks[numberOfTasks] = new Event(description, start, end);
                }
                System.out.println("________________________________");
                System.out.println("Got it. I've added this task:\n" + tasks[numberOfTasks].toString());
                numberOfTasks++;
                System.out.println("Now you have " + numberOfTasks + " tasks in the list.");
                System.out.println("________________________________");
            }
        }

        System.out.println("________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("________________________________");
    }
}
