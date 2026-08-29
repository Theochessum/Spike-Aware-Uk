/**
 * Represents the admin user within the spike aware system.
 * Provides admin menu, logins and resource management.
 * Hardcoded credentials stored in HashMaps, dynamic account stored via SQLite.
 *
 * @author SID 2503921
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Admin {
    /**
     * Source: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
     * ANSI escape codes for terminal colours
     */
    public static final String GREEN = "\u001B[32m";
    public static final String CYAN = "\u001B[36m";
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001b[1m";
    public static final String YELLOW = "\u001B[93m";
    public static final String PURPLE = "\u001B[35m";
    /**
     * Displays admin menu and handles user input
     *
     * @param scanner - for user input
     * @param db - handles database operation like stored admin accounts
     */
    //Admin menu
    public void adminMenu(Scanner scanner, DatabaseManager db, ArrayList<Analytics> viewLog) {
        while (true) {
            Main.clearScreen();
            Main.titleCard();
            System.out.println(YELLOW + "========================" + RESET);
            System.out.println(CYAN   + "     Admin dashboard    " + RESET);
            System.out.println(YELLOW + "========================" + RESET);
            System.out.println(YELLOW + "1. Add Resources" + RESET);
            System.out.println(YELLOW + "2. Update Resources" + RESET);
            System.out.println(YELLOW + "3. View Analytics" + RESET);
            System.out.println(YELLOW + "4. View all resources" + RESET);
            System.out.println(YELLOW + "0. Logout" + RESET);

            int adminChoice = -1;
            try {
                adminChoice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(RED + BOLD + "Invalid input - try again!" + RESET);
                scanner.nextLine();
                continue;// error handling for invalid input on super admin menu
            }

                //switch cases for admin
                switch (adminChoice) {
                    case 1 -> {
                        System.out.print("Enter title: ");
                        String title = scanner.nextLine().trim();
                        System.out.print("enter url: ");
                        String Url = scanner.nextLine().trim();
                        System.out.print("Enter category (Awareness/Research): ");
                        String category = scanner.nextLine().trim();
                        System.out.println("Enter content (PASTE TEXT!): ");
                        String content = scanner.nextLine().trim();

                        AwarenessResources resource = new AwarenessResources();
                        resource.setTitle(title);
                        resource.setUrl(Url);
                        resource.setCategory(category);
                        resource.setContent(content);
                        db.addResources(resource);
                        Main.pressEnter(scanner);
                    }

                    case 2 -> {
                        db.getAllResources();
                        System.out.print("Enter resource title to update (Must be exact!): ");
                        String oldTitle = scanner.nextLine().trim();

                        if (oldTitle.isEmpty()) {
                            System.out.println(RED + BOLD + "Title cannot be empty!" + RESET);
                            Main.pressEnter(scanner);
                            break;
                        }

                        boolean exists = db.resourceExists(oldTitle);
                        if (!exists) {
                            System.out.println(RED + BOLD + "Resource not found!" + RESET);
                            Main.pressEnter(scanner);
                            break;
                        }

                        System.out.println("Do you want to update selected resource? (Y/N)");
                        String confirm = scanner.nextLine().trim();

                        if (confirm.equalsIgnoreCase("Y")) {
                            System.out.print("Enter new title: ");
                            String newTitle = scanner.nextLine().trim();
                            System.out.print("Enter new url: ");
                            String newUrl = scanner.nextLine().trim();

                            AwarenessResources resource = new AwarenessResources();
                            resource.setTitle(newTitle);
                            resource.setUrl(newUrl);
                            resource.setStatus("pending");
                            db.updateResource(resource, oldTitle);
                        } else {
                            System.out.println(RED + BOLD + "Resource not updated!" + RESET);
                        }
                        Main.pressEnter(scanner);
                    }

                    case 3 -> {
                        if(viewLog.isEmpty()) {
                            System.out.println(RED + BOLD + "No views recorded yet!" + RESET);
                        }else{
                            for(Analytics a : viewLog){
                                System.out.println("Title: "+ a.getResourceType()+ " views: "+ a.getViewCount());
                            }
                        }
                        Main.pressEnter(scanner);
                    }
                    case 4 -> {
                        boolean resourceExists = db.getAllResources();
                        if (!resourceExists) {
                            System.out.println(RED + BOLD + "No resources found!");
                        }
                        Main.pressEnter(scanner);
                    }
                    case 0 -> {
                        logout();
                        Main.pressEnter(scanner);
                        return;
                    }
            }
        }
    }

    //Admin login

    /**
     * Displays promt for admin login and checks username and password
     * against hashmap and SQLite Database.
     * Redirect to Superadmin account if Superadmin credentials are entered
     *
     * @param scanner - scans object for user input
     * @param db -  handles database operation like stored admin accounts
     */
    public void showLogin(Scanner scanner, DatabaseManager db, ArrayList<Analytics> viewLog){

        System.out.print("Username: ");
        String username = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        boolean hashMapLogin = login.containsKey(username) &&
                               login.get(username).equals(password);

        boolean dbLogin = db.loginAdmin(username, password);

        if (hashMapLogin || dbLogin) {
            if (username.equals("Superadmin1")) {
                new SuperAdmin().superAdminMenu(scanner, db, viewLog);
            } else {
                adminMenu(scanner, db, viewLog);
            }
        } else {
            System.out.println(RED+ BOLD+ "Invalid username or password!");
            scanner.nextLine();
            Main.pressEnter(scanner);
        }
    }

    private HashMap<String, String> login = new HashMap<>();

    public Admin() {
        login.put("Admin1", "Password123");
        login.put("Superadmin1", "Super123");
    }

    /**
     * Logs admin out and returns to main menu
     */
    final void logout() {
        System.out.println(GREEN + BOLD +"Admin log out successful!"+RESET);
    }
}

