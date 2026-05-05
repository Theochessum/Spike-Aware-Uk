/**
 * Represents the public user of spike aware system.
 * provides main public menu for browsing and
 * searching resources
 *
 * @author SID 2503921
 */

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class PublicUser {
    /**
     * Source: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
     * ANSI escape codes for terminal colours
     */
    public static final String CYAN = "\u001B[36m";
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001b[1m";
    public static final String YELLOW = "\u001B[93m";

   public void publicMenu(Scanner scanner, DatabaseManager db) {

       ArrayList<Analytics> viewLog = new ArrayList<>();

       while (true) {
           //main menu -public user
           Main.clearScreen();
           Main.titleCard();
           System.out.println(YELLOW + "╔══════════════════════════╗");
           System.out.println(YELLOW + "║" + CYAN + " 1. Browse resources" + YELLOW + "      ║" + RESET);
           System.out.println(YELLOW + "║" + CYAN + " 2. Search resources" + YELLOW + "      ║" + RESET);
           System.out.println(YELLOW + "║" + CYAN + " 3. Emergency help" + YELLOW + "        ║" + RESET);
           System.out.println(YELLOW + "║" + CYAN + " 4. Admin login" + YELLOW + "           ║" + RESET);
           System.out.println(YELLOW + "║                          ║");
           System.out.println(YELLOW + "║" + RED + " 0. Exit" + YELLOW + "                  ║" + RESET);
           System.out.println(YELLOW + "╚══════════════════════════╝" + RESET);
           System.out.print(BOLD + CYAN + "Enter choice: " + RESET);

           int choice = -1;
           try {
               choice = scanner.nextInt();
               scanner.nextLine();
           } catch (InputMismatchException e) {
               System.out.println(RED + "Invalid input - try again!" + RESET);
               scanner.nextLine();
               continue;
           }

           //switch cases public user menu
           switch (choice) {
               case 1 -> {
                   System.out.println(CYAN + "1. Awareness Resources");
                   System.out.println("2. Research Resources" + RESET);

                   int choice2 = -1;
                   try {
                       choice2 = scanner.nextInt();
                       scanner.nextLine();
                   } catch (InputMismatchException e) {
                       System.out.println(RED + "Invalid input - try again!" + RESET);
                       scanner.nextLine();
                       Main.pressEnter(scanner);
                       break;
                   }

                   if (choice2 == 1) {
                       db.getAllapprovedResources("Awareness");
                   } else {
                       db.getAllapprovedResources("Research");
                   }


                   System.out.print("Enter resource title to view (or press enter to go back): ");
                   String viewTitle = scanner.nextLine().trim();

                   if (!viewTitle.isEmpty()) {
                       db.getResourceByTitle(viewTitle);

                       Analytics viewAnalytics = null;
                       for (Analytics a : viewLog) {
                           if (a.getResourceType().equals(viewTitle)) {
                               viewAnalytics = a;
                               break;
                           }
                       }
                       if (viewAnalytics == null) {
                           viewAnalytics = new Analytics(viewLog.size() + 1, 0, viewTitle);
                           viewLog.add(viewAnalytics);
                       }
                       viewAnalytics.incrementView();
                   }
                   Main.pressEnter(scanner);
               }
// key word search removed from public menu - poorly suited for terminal application
// searchResources still used in admin menus
               case 2 -> {
                   db.getAllapprovedResources("Awareness");
                   db.getAllapprovedResources("Research");
                   System.out.print("Search: ");
                   String keyword = scanner.nextLine();
                   db.getResourceByTitle(keyword);

                   Analytics viewAnalytics = null;
                   for (Analytics a : viewLog) {
                       if (a.getResourceType().equals(keyword)) {
                           viewAnalytics = a;
                           break;
                       }
                   }
                   if (viewAnalytics == null) {
                       viewAnalytics = new Analytics(viewLog.size() + 1, 0, keyword);
                       viewLog.add(viewAnalytics);
                   }
                   viewAnalytics.incrementView();
                   Main.pressEnter(scanner);
               }

               case 3 ->{
                       System.out.println("please call 999 for emergency help");
                   Main.pressEnter(scanner);
               }

               case 4 -> new Admin().showLogin(scanner, db, viewLog);


               case 0 -> {
                   System.out.println("Goodbye!!!");
                   Main.pressEnter(scanner);
                   return;
               }
           }
       }
   }
}


