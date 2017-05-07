import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Name: Alex Oladele
 * Date: 5/3/17
 * Assignment: CSE385_Project
 * Notes: This class is a Singleton, which means there's only allowed to be one instance of it, which is exactly what we want.
 */
public class SQLServer {

    // ========================================= Member Variables

    private static SQLServer sqlServer = new SQLServer();
    private String loginUsername, loginPassword;
    private HashMap<String, String> userList;
    public static Connection conn = null;



    // ========================================= Constructors
    private SQLServer() {
//        Instantiates Hashmap of Users, and fills it with test users
        userList = new HashMap<>();
        userList.put("admin", "dora");
        userList.put("user", "boots");
//        connectToDB();
    }

    private SQLServer(String loginUsername, String loginPassword) {
        super();
        this.loginUsername = loginUsername;
        this.loginPassword = loginPassword;
    }

    // ========================================= Getters / Setters

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;

    }

    // ========================================= Member Methods

    protected void connectToDB() {
//        Get file path for future use
        final String dbInfoFilePath, dbConnectionURL;
        String dbUsername = null, dbPassword = null;

        File dbInfo = new File("DB-info.txt");
        dbInfoFilePath = dbInfo.getAbsolutePath();
        try {
//            Creates bufferedReader in order to read in file
            BufferedReader bReader = new BufferedReader(new FileReader(dbInfoFilePath));

//            Reads in db username and password from text file and assigns to variables
            dbUsername = bReader.readLine();
            dbPassword = bReader.readLine();

//            Closes Buffered Reader  as its no longer needed past this point
            bReader.close();

//            Forms the Connection URL to the database
            dbConnectionURL = String.format("jdbc:sqlserver://mydatabase.ctidpczh1sap.us-east-2.rds.amazonaws.com:1433;" +
                    "databaseName=Casino;user=%s;password=%s;", dbUsername, dbPassword);

//            Makes the Actual Connection to the database
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(dbConnectionURL);
            if (conn != null) {
                System.out.println("Successfully Connected to Casino DB!");
            }

        } catch (FileNotFoundException e) {
            System.out.println("DB Login Info was not found\nTherefore Connection could not be established - Exiting");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.out.println("DB Info file was empty - Could not get Username and Password! - Exiting");
            e.printStackTrace();
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.out.println("Internal Error with using SQL Server- Exiting");
            e.printStackTrace();
            System.exit(0);
        } catch (SQLException e) {
            System.out.println("Error connecting to Casino Database! - Exiting");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /* Static 'instance' method */
    public static SQLServer getInstance() {
        return sqlServer;
    }
}
