/**
 * Spike Aware UK - Resource management system
 * SID: 2503921
 * TEAM - Safe Sip
 */
import java.util.Scanner;

public class Main {
    /**
     * Source: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
     * ANSI escape codes for terminal colours
     */
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    public static void pressEnter(Scanner scanner) {
        System.out.print(CYAN+"Press enter to continue...");
        scanner.nextLine();
    }

    public static void titleCard (){
    System.out.println(PURPLE + "\n" +
            "░██████╗██████╗░██╗██╗░░██╗███████╗\n" +
            "██╔════╝██╔══██╗██║██║░██╔╝██╔════╝\n" +
            "╚█████╗░██████╔╝██║█████═╝░█████╗░░\n" +
            "░╚═══██╗██╔═══╝░██║██╔═██╗░██╔══╝░░\n" +
            "██████╔╝██║░░░░░██║██║░╚██╗███████╗\n" +
            "╚═════╝░╚═╝░░░░░╚═╝╚═╝░░╚═╝╚══════╝\n" +
            "\n" +
            "░█████╗░░██╗░░░░░░░██╗░█████╗░██████╗░███████╗      ██╗░░░██╗██╗░░██╗\n" +
            "██╔══██╗░██║░░██╗░░██║██╔══██╗██╔══██╗██╔════╝      ██║░░░██║██║░██╔╝\n" +
            "███████║░╚██╗████╗██╔╝███████║██████╔╝█████╗░░      ██║░░░██║█████═╝░\n" +
            "██╔══██║░░████╔═████║░██╔══██║██╔══██╗██╔══╝░░      ██║░░░██║██╔═██╗░\n" +
            "██║░░██║░░╚██╔╝░╚██╔╝░██║░░██║██║░░██║███████╗      ╚██████╔╝██║░╚██╗\n" +
            "╚═╝░░╚═╝░░░╚═╝░░░╚═╝░░╚═╝░░╚═╝╚═╝░░╚═╝╚══════╝       ╚═════╝░╚═╝░░╚═╝");
       System.out.println("\n");}

    public static void main(String[] args) {
        /**
         * https://stackoverflow.com/questions/8363493/hiding-system-out-print-calls-of-a-class
         * stops SQLite JDBC warning output
         */
        System.setErr(new java.io.PrintStream(java.io.OutputStream.nullOutputStream()));
        System.setProperty("file.encoding", "UTF-8");////makes ANSI COLOURS WORK ON WINDOWS
        System.setProperty("stdout.encoding", "UTF-8");
        Scanner scanner = new Scanner(System.in);
        DatabaseManager db = new DatabaseManager();
        db.connect();
        db.createTable();

            // Demonstrating AwarenessResources class and inherited Resource methods
            AwarenessResources awareness = new AwarenessResources();
            awareness.setTitle("NHS Spiking Guide");
            awareness.setUrl("www.nhs.uk/spiking");
            awareness.setCategory("Awareness");
            awareness.setDateAdded("2024-01-01");
            System.out.println("Resource title: " + awareness.getTitle());
            System.out.println("Filter by date match: " + awareness.filterByDate("2024"));

            // Demonstrating ResearchResource class
            ResearchResource research = new ResearchResource();
            research.setTitle("Drug Facilitated Assault Study");
            research.setPublicationYear(2023);
            research.setAuthors("Smith, J");
            research.setKeyWords("spiking assault");
            System.out.println("Keyword match: " + research.getByKeywords("spiking"));
            System.out.println("Year match: " + research.filterByYear(2023));

            // Demonstrating Analytics class
            Analytics analytics = new Analytics(1, 1, "NHS Spiking Guide");
            analytics.incrementView();
            analytics.getStatistics();

        new PublicUser().publicMenu(scanner, db);
    }
}
