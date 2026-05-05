/**
 * Represents the Super admin user in the spike aware system.
 * Extends admin with elevated privileges including resource
 * approval, admin account management, and cvs exports.
 * <p>
 * Note: Deviated from UML - Admin is the parent class, as super admin
 * extends admin functionality rather than reversed
 *
 * @author SID 2503921
 */

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
public class SuperAdmin extends Admin {

    public void superAdminMenu(Scanner scanner, DatabaseManager db, ArrayList<Analytics> viewLog) {
        System.out.println("Super admin login successful");
        scanner.nextLine();
        Main.pressEnter(scanner);

        while (true) {
            Main.clearScreen();
            Main.titleCard();
            System.out.println(YELLOW + "=======================" + RESET);
            System.out.println(CYAN +   " Super admin dashboard " + RESET);
            System.out.println(YELLOW + "=======================" + RESET);
            System.out.println(YELLOW + "1. Add Resources" + RESET);
            System.out.println(YELLOW + "2. Delete Resources" + RESET);
            System.out.println(YELLOW + "3. Update Resources" + RESET);
            System.out.println(YELLOW + "4. View Analytics" + RESET);
            System.out.println(YELLOW + "5. View all resources" + RESET);
            System.out.println(YELLOW + "6. Manage admin accounts" + RESET);
            System.out.println(YELLOW + "7. Approve resource" + RESET);
            System.out.println(YELLOW + "8. Export to csv (Backup Data)"+RESET);
            System.out.println(YELLOW + "0. Logout" + RESET);


            int superAdminchoice = -1;
            try {
                superAdminchoice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(RED + "Invalid input - try again!" + RESET);
                scanner.nextLine();
                continue;// error handling for invalid input on super admin menu
            }

            switch (superAdminchoice) {
                case 1 -> {
                    System.out.print(CYAN+"Enter title: "+RESET);
                    String title = scanner.nextLine().trim();
                    System.out.print(CYAN+"enter url: "+RESET);
                    String Url = scanner.nextLine().trim();


                    System.out.println(CYAN+"1. Awareness Resources"+RESET);
                    System.out.println(CYAN+"2. Research Resources"+RESET);
                    int typeChoice = scanner.nextInt();
                    scanner.nextLine();
                            String category = (typeChoice == 2) ? "Research" : "Awareness";

                    System.out.println(CYAN+ "Enter content ( PASTE TEXT! ): "+RESET);
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
                    try {
                        Main.clearScreen();
                        System.out.println(YELLOW + "1. Delete approved resource" + RESET);
                        System.out.println(YELLOW + "2. Delete pending resource" + RESET);
                        System.out.print("Enter choice:");
                        int deleteChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (deleteChoice == 1) {
                            db.getAllapprovedResources("Awareness");
                            db.getAllapprovedResources("Research");
                        } else {
                            db.getPendingResources();
                        }

                        System.out.print("Enter resource ID to delete (or 0 to cancel): ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        if (id == 0) {
                            System.out.println("Cancelled!");
                            Main.pressEnter(scanner);
                            break;
                        }

                        System.out.println("Are you sure? (Y/N): ");
                        String confirm = scanner.nextLine().trim();
                        if (confirm.equalsIgnoreCase("Y")) {
                            db.deleteResources(id);
                            Main.pressEnter(scanner);
                        } else {
                            System.out.println("No resources have been deleted");
                            Main.pressEnter(scanner);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println(RED + "Invalid input - try again!" + RESET);
                    }

                }
                case 3 -> {
                    db.getAllResources();
                    System.out.print("Enter resource title to update: ");
                    String oldTitle = scanner.nextLine().trim();

                    if (oldTitle.isEmpty()) {
                        System.out.println(RED + BOLD + "Title cannot be empty!" + RESET);
                        Main.pressEnter(scanner);
                        break;
                    }

                    boolean exists = db.searchResources(oldTitle);
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

                case 4 -> {
                    if(viewLog.isEmpty()) {
                        System.out.println(RED + BOLD + "No views recorded yet!" + RESET);
                    }else{
                        for(Analytics a : viewLog){
                            System.out.println("Title: "+ a.getResourceType()+ " views: "+ a.getViewCount());
                        }
                    }
                    Main.pressEnter(scanner);
                }
                case 5 -> {
                    db.getAllResources();
                    Main.pressEnter(scanner);
                }
                case 6 -> {
                    int manageAdminMenu = -1;
                    do {
                        Main.clearScreen();
                        Main.titleCard();
                        System.out.println(CYAN+"====="+PURPLE+" Manage Admins "+CYAN+"=====");
                        System.out.println(YELLOW+"1.Create admin accounts");
                        System.out.println(YELLOW+"2.Remove admin accounts");
                        System.out.println(YELLOW+"3.List admins");
                        System.out.println(RED+BOLD+"0.Exit"+RESET);

                        System.out.print(YELLOW+"Enter choice: "+RESET);

                        try {
                            manageAdminMenu = scanner.nextInt();
                            scanner.nextLine();

                            switch (manageAdminMenu) {
                                case 1 -> {
                                    System.out.println("Enter new username");
                                    String user = scanner.nextLine().trim();
                                    System.out.println("Enter new password");
                                    String newPassword = scanner.nextLine().trim();
                                    db.addAdmin(user, newPassword);
                                    Main.pressEnter(scanner);
                                }
                                case 2 -> {
                                    System.out.print("Enter username to remove: ");
                                    String deleteAdminAccount = scanner.nextLine().trim();

                                    System.out.println(RED+BOLD+"Are you sure you want to delete" + deleteAdminAccount
                                    + "? (Y/N): "+RESET);
                                    String confirm = scanner.nextLine().trim();

                                    if (confirm.equalsIgnoreCase("Y")) {
                                        db.removeAdmin(deleteAdminAccount);
                                    }else{
                                        System.out.println("Deletion cancelled");
                                    }
                                    Main.pressEnter(scanner);
                                }
                                case 3 -> {
                                    System.out.println("Existing admin accounts");
                                    db.listAdmins();
                                    Main.pressEnter(scanner);
                                }
                                case 0 -> System.out.println(RED + BOLD + "exit" + RESET);
                                default -> System.out.println(RED + BOLD + "Invalid option!" + RESET);
                            }
                        } catch (InputMismatchException e) {
                            System.out.println(RED + BOLD + "Invalid input! - enter a number from (0-3)" + RESET);
                            scanner.nextLine();
                        }
                    }while (manageAdminMenu != 0);
                }
                case 7 -> {
                    try {
                        db.getAllResources();
                        System.out.println("Enter ID to approve resource");
                        int id = scanner.nextInt();
                        db.approveResources(id);

                    } catch (InputMismatchException e) {
                        System.out.println(RED + "Invalid input - try again!" + RESET);
                        scanner.nextLine();
                    }
                    Main.pressEnter(scanner);
                }
                case 8 -> {
                    db.exportToCVS();
                    db.exportAnalyticsToCVS(viewLog);
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
}

