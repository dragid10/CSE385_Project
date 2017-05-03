import java.util.HashMap;

/**
 * Name: Alex Oladele
 * Date: 5/3/17
 * Assignment: CSE385_Project
 */
public class SQLServer {

    // ========================================= Member Variables

    private String loginUsername, loginPassword;
    private HashMap<String, String> userList;

    // ========================================= Constructors
    public SQLServer() {
        userList = new HashMap<>();
        userList.put("admin", "dora");
        userList.put("user", "boots");
    }

    public SQLServer(String loginUsername, String loginPassword) {
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

    public static void connectToDB() {
//        Use text file to connect to DB
        String dbUsername = null, dbPassword = null;

    }
}
