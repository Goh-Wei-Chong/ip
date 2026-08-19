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
        while (true) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                break;
            }

            System.out.println("________________________________");
            System.out.println("    " + command);
            System.out.println("________________________________");
        }

        System.out.println("________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("________________________________");
    }
}
