/**
 * Manages all database operation for Spike aware resource System.
 * manages (crud) create, read, update and delete operations
 * for resources and admin account using SQLite via
 * Java Database connectivity (JDBC).
 *
 * @author SID: 2503921
 */

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    /**
     * Source: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
     * ANSI escape codes for terminal colours
     */
    public static final String BOLD = "\u001b[1m";
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[93m";

    private Connection connection; // box called connection
    /**
     * Connects to SQLite database through jdbc
     * Source: https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
     * JDBC DriverManager.getConnection usage
     */
    public void connect() {

        try {    // this is to attempt the code do not use if else
            connection = DriverManager.getConnection("jdbc:sqlite:spikeaware.db");// error handling try and catch
            System.out.println("Connected!");

        } catch (SQLException error) { // if connection fails perform catch
            System.out.println(RED + BOLD + "Connection failed: " + error.getMessage());
        }
    }
    /**
     * Adds resource to SQLite database via admin menu's
     * with the status of "Pending".
     * resource must be approved within the Super admin menu
     * before being seen by public user
     * @param resource - object containing (Title, Url and Status).
     */
    public void addResources(Resource resource) {
        try {
            String sql = "insert into resources (Title, Url, Status, category, content) values(?,?, ?,?,?)";
            PreparedStatement Statement = connection.prepareStatement(sql);
            Statement.setString(1, resource.getTitle());
            Statement.setString(2, resource.getUrl());
            Statement.setString(3, "pending");
            Statement.setString(4, resource.getCategory());
            Statement.setString(5, resource.getContent());

            Statement.executeUpdate();
            System.out.println(GREEN+BOLD+"✓ Resource added!"+RESET);
        } catch (SQLException Error) {
            System.out.println(RED + BOLD + "failed to add resource!: " + Error.getMessage());
        }
    }

    /**
     * Deletes resource from Database
     *
     * @param id = resource to delete
     */

    public void deleteResources(int id) { // method to remove resources from
        try {
            String sql = "DELETE FROM Resources WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("Resource deleted");
            } else {
                System.out.println(RED + BOLD +"Invalid resource ID"+RESET);
            }
        } catch (SQLException error) {
            System.out.println(RED + BOLD + "Failed to delete resource: " + error.getMessage());
        }
    }
    /**
     * Creates tables for both resources and admin account with the SQLITE
     * Database. method is called during startup of program.
     * Resources table holds: id, title, URL and status.
     * Admin table holds: id, username and password
     */

    /**
     *  https://stackoverflow.com/questions/66579936/create-a-table-in-sqlite-with-the-following-fields
     *  - SQLite methods and tables from this source
     */
    public void createTable() { // if adding to table remember delete db database
        try {
            String sql = "create table if not exists resources (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT NOT NULL," +
                    "URL TEXT NOT NULL," +
                    "status TEXT NOT NULL,"+
                    "category TEXT NOT NULL," +
                    "content TEXT)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();

            //superAdmin Table//
            String adminSql = "CREATE TABLE IF NOT EXISTS admins (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT NOT NULL UNIQUE," +
                    "password TEXT NOT NULL)";
            PreparedStatement adminStatement = connection.prepareStatement(adminSql);
            adminStatement.executeUpdate();

            System.out.println(GREEN + BOLD +"table working"+RESET);
        } catch (SQLException Error) {
            System.out.println(RED + BOLD + "table failed"+RESET);
        }
    }

    /**
     * Allows admins to update pending or approved resources in Database
     * replaces the id, title, URL of the selected resources ID.
     *
     * @param resource - Object containing method for updating (id, title, URL) for given ID.
     */
    public void updateResource(Resource resource, String oldTitle) {
        try {
            String sql = "UPDATE resources SET title = ?, " +
                    "url = ?, status = ? WHERE title = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, resource.getTitle());
            statement.setString(2, resource.getUrl());
            statement.setString(3, resource.getStatus());
            statement.setString(4, oldTitle);
            statement.executeUpdate();

            System.out.println(BOLD+GREEN+"✓ Resource update"+RESET);
        } catch (SQLException Error) {
            System.out.println(RED + BOLD + "Failed to update resource!:" + Error.getMessage());
        }
    }
    /**
     * Searches the SQLite Database for resource containing
     * the users Entered word.
     * uses the SQL LIKE command to allow partial matches
     *
     * @param keyword - users entered word to show related resources
     */
    public boolean searchResources(String keyword) {
        try {
            String sql = "SELECT * FROM resources WHERE title LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + keyword + "%");

            ResultSet search = statement.executeQuery();
            boolean found = false;
            while (search.next()) {
                found = true;
                System.out.println(YELLOW+"Title: "+RESET + search.getString("title"));
                System.out.println(YELLOW+"URL: "+RESET + search.getString("url"));
                System.out.println(YELLOW+"Status: "+RESET + search.getString("status"));
            }
            return found;
        } catch (SQLException Error) {
            System.out.println(RED + BOLD + "search failed!: " + Error.getMessage());
        }return false;
    }
    /**
     * Gets resources from Data base table
     *
     * @return True if resource is found.
     * returns false if table is empty.
     */
    public boolean getAllResources() {
        try {
            String sql = "SELECT * FROM resources";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            boolean found = false; // error handling for no resources in table
            ArrayList<ArrayList<String>> allResources = new ArrayList<>();

            while (results.next()) {
                found = true;
                ArrayList<String> row = new ArrayList<>();
                row.add(String.valueOf(results.getInt("id")));
                row.add(results.getString("title"));
                row.add(results.getString("url"));
                row.add(results.getString("status"));
                allResources.add(row);
            }

            for (ArrayList<String> row : allResources) {
                System.out.println(YELLOW+"ID: " +GREEN+row.get(0)+RESET);
                System.out.println(YELLOW+"Title: " +GREEN+ row.get(1)+RESET);
                System.out.println(YELLOW+"URL: " +GREEN+row.get(2)+RESET);
                System.out.println(YELLOW+"Status: " +RESET+ GREEN+row.get(3)+RESET);
                System.out.println(YELLOW+"----------------"+RESET);
            }
            return found;
        } catch (SQLException Error) {
            System.out.println(RED + BOLD + "Failed to get resources!" + RESET);
            return false;
        }
    }

    /**
     * Displays a list of all approved resources from the SQL Database and for given catorgory
     * Displays Message "No resources found!" if no resources are in the Database.
     */
    public ArrayList<Resource> getAllapprovedResources(String category) {
        ArrayList<Resource> resources = new ArrayList<>();
        try {
            String sql = "SELECT * FROM resources WHERE status = 'approved' AND category = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, category);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
               Resource r;
               if(category.equals("Awareness")){
                   r = new AwarenessResources();
               }else{
                   r = new ResearchResource();
               }
               r.setTitle(results.getString("title"));
                r.setUrl(results.getString("url"));
                r.setCategory(results.getString("category"));
                resources.add(r);
               }
        } catch (SQLException error) {
            System.out.println(RED + BOLD + "Failed to display resources!: " + RESET + error.getMessage());
        }
        return resources;
    }
    /**
     * Displays a list of all pending resources from the SQL Database and for given catorgory
     * Displays Message "No resources found!" if no resources are in the Database.
     */
    public void getPendingResources() {
        try {
            String sql = "SELECT * FROM resources WHERE status = 'pending'";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            boolean found = false;
            while (results.next()) {
                found = true;
                System.out.println(YELLOW + "Title: " + RESET + results.getString("title"));
                System.out.println(YELLOW + "URL: " + RESET+ results.getString("url"));
                System.out.println(YELLOW + "ID: " + RESET+ results.getInt("id"));
                System.out.println(YELLOW + "Category: " + RESET+ results.getString("category"));
                System.out.println(YELLOW + "Status: " + RESET+ results.getString("status"));
                System.out.println("-------------------------------");
            }
            if (!found) {
                System.out.println(RED + BOLD + "No pending resources found!" + RESET);
            }
        } catch (SQLException error) {
            System.out.println(RED + BOLD + "Failed to display resources!: " + RESET + error.getMessage());
        }
    }

    /**
     * Allows Super admin to change the status of resources from pending to approved.
     * Resources become visable to the public user once approved
     * If ID does not match any resources within the database, an error message is displayed.
     *
     * @param id of resource
     */
    public void approveResources(int id) {
        try {
            String sql = "UPDATE resources SET status = 'approved' WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println("Resource approved");
            } else {
                System.out.println(RED + BOLD + "Incorrect ID!" + RESET);
            }
        } catch (SQLException Error) {
            System.out.println(RED + BOLD + "Failed to approve resource!: " + RESET + Error.getMessage());
        }
    }
    /**
     * retrieves and displays content linked to the title of the resource
     * only returns approved resources to the public user
     *
     * @param title - title of resource to view
     */
    public void getResourceByTitle (String title) {
        try {
            String sql = "SELECT * FROM resources WHERE title = ? AND status = 'approved'";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            ResultSet results = statement.executeQuery();

            if (results.next()) {
                System.out.println(YELLOW + "Title: " + RESET + results.getString("title"));
                System.out.println(YELLOW + "URL: " + RESET + results.getString("url"));
                System.out.println(YELLOW + "Category: " + RESET + results.getString("category"));
                System.out.println(YELLOW + "Content: " + RESET + results.getString("content"));
            } else {
                System.out.println(RED + BOLD + "Resource not found!" + RESET);
            }
        } catch (SQLException Error) {
            System.out.println(RED + BOLD + "Failed: " + RESET + Error.getMessage());
        }
    }
    /**
     * exports how many views each resource has to a cvs file
     *
     * @param viewLog
     */
    public void exportAnalyticsToCVS(ArrayList<Analytics> viewLog) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("analytics.csv"));
            writer.write("Title,Views");
            writer.newLine();

            for (Analytics a : viewLog) {
                writer.write(a.getResourceType() + "," + a.getViewCount());
                writer.newLine();
            }

            writer.close();
            System.out.println(GREEN + BOLD + "Analytics exported to analytics.csv!" + RESET);
        } catch (IOException Error) {
            System.out.println(RED + BOLD + "Export failed!" + RESET);
        }
    }

    //admin methods

    /**
     * Creates new admin account in the SQL database
     * Lets Super admins creat accounts without hardcoded password or usernames
     *
     * @param username - username for new admin account
     * @param password - password for new admin account
     */
    public void addAdmin(String username, String password) {
        try {
            String sql = "INSERT INTO admins (username, password) VALUES(?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            System.out.println(GREEN + BOLD +"Admin account created"+RESET);
        } catch (SQLException Error) {
            System.out.println(RED + BOLD + "Failed to create admin!: " + RESET + Error.getMessage());
        }
    }

    /**
     * Removes an admin account from the SQLITE Database
     * Displays error message if Admins username is not found
     *
     * @param username - username of admin account to delete
     */
    public void removeAdmin(String username) {
        try {
            String sql = "DELETE FROM admins WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                System.out.println(GREEN + BOLD +"Admin account deleted"+RESET);
            } else {
                System.out.println(RED + BOLD +"Admin not found!"+RESET);
            }
        } catch (SQLException Error) {
            System.out.println(RED + BOLD + "Failed to delete admin account!" + RESET + Error.getMessage());
        }
    }

    /**
     * verifies admin username and password in the SQLite database
     *
     * @param username - username for admin account
     * @param password - password linked to the entered admin account
     * @return username
     */
    public boolean loginAdmin(String username, String password) {
        try {
            String sql = "SELECT *FROM admins WHERE username = ? AND PASSWORD = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();
            return results.next();
        } catch (SQLException Error) {
            System.out.println(RED + BOLD + "Login failed!" + RESET + Error.getMessage());
            return false;
        }
    }

    /**
     * Displays a list of admins within the SQLite database
     */
    public void listAdmins() {
        try {
            String sql = "SELECT username FROM admins";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                System.out.println("- "+ results.getString("username"));
            }
        } catch (SQLException Error) {
            System.out.println(RED+BOLD+"Failed to list admins!" + Error.getMessage());
        }
    }

    //cvs files used as data back up

    /**
     * Exports all resources from SQLite database into a CVS file.
     * Used as a backup for Super admins to retrieve Data if lost.
     * File saved to the project root as resources.csv
     */
    public void exportToCVS() {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("resources.csv"));

            writer.write("ID,Title,URL,Status");
            writer.newLine();

            String sql = "SELECT * FROM resources";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                       writer.write(
                            results.getInt("id") + "," +
                                results.getString("title") + "," +
                                results.getString("url") + "," +
                                results.getString("status"));
                       writer.newLine();
            }
            writer.close();
            System.out.println(GREEN + BOLD +"Resources exported to resources.csv!"+RESET);
        }catch (SQLException | IOException Error){
            System.out.println(RED+BOLD+ "Export failed! "+RESET+Error.getMessage());
        }
    }
}




