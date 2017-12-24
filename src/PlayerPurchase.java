import com.sun.istack.internal.NotNull;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Name: Alex Oladele
 * Date: 5/8/17
 * Assignment: CSE385_Project
 */
public class PlayerPurchase {
    static private PlayerPurchase playerPurchase = new PlayerPurchase();
    private String sqlQuery;
    private CallableStatement callableStatement;
    private Statement statement;

    private PlayerPurchase() {
    }

    public void addPlayerPurchase(@NotNull int playerID, @NotNull int purchaseID) {
        sqlQuery = "{call spAddUpdateDeletePlayerPurchase (?, ?, ?, ?)}";
        try {
//            SQL-ify the call to the DB
            callableStatement = SQLServer.conn.prepareCall(sqlQuery);

//            Add the parameters to the sp and Execute
            callableStatement.setInt(1, 0);
            callableStatement.setInt(2, purchaseID);
            callableStatement.setInt(3, playerID);
            callableStatement.setBoolean(4, false);
            callableStatement.execute();
            System.out.println("PlayerPurchase Successfully added!");

        } catch (SQLException e) {
            System.out.println("There was a problem adding the PlayerPurchase to the database");
            e.printStackTrace();
        }
    }

    public void updatePlayerPurchase(@NotNull int playerPurchaseID, @NotNull int playerID, @NotNull int purchaseID) {
        boolean isDeleted = false;

//        Actual SQL query to PlayerPurchase
        sqlQuery = String.format("SELECT * FROM tblPlayerPurchase WHERE PlayerPurchaseID = %d", playerPurchaseID);
        try {
//            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

//            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                //        Retrieve the current data from the Table to maybe reassign
                if (playerID <= 0) {
//            Get Current first name of PlayerPurchase, and assign it to variable
                    playerID = rs.getInt("PlayerID");
                }
                if (purchaseID <= 0) {
//            Get Current first name of PlayerPurchase, and assign it to variable
                    purchaseID = rs.getInt("PurchaseID");
                }
                isDeleted = rs.getBoolean("Deleted");

            }

//            Checks if the PlayerPurchase is already soft-deleted
            if (!isDeleted) {
                //            Add the parameters to the sp and Execute
                sqlQuery = "{call spAddUpdateDeletePlayerPurchase (?, ?, ?, ?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);
                callableStatement.setInt("PlayerPurchaseID", playerPurchaseID);
                callableStatement.setInt("PurchaseID", purchaseID);
                callableStatement.setInt("PlayerID", playerID);
                callableStatement.setBoolean("Deleted", false);
                callableStatement.execute();
                System.out.println("PlayerPurchase Updated Successfully!");
            } else {
                System.out.println("The PlayerPurchaseID provided does not exist\nDatabase Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("PlayerPurchase not updated");
            e.printStackTrace();
        }
    }

    public void deletePlayerPurchase(@NotNull int playerPurchaseID) {
        int purchaseID = 0, playerID = 0;
        boolean isDeleted = false;

        //        Actual SQL query to PlayerPurchase
        sqlQuery = String.format("SELECT * FROM tblPlayerPurchase WHERE PlayerPurchaseID = %d", playerPurchaseID);
        try {
//            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

//            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                //        Retrieve the current data from the Table to maybe reassign
                if (playerID <= 0) {
//            Get Current first name of PlayerPurchase, and assign it to variable
                    playerID = rs.getInt("PlayerID");
                }
                if (purchaseID <= 0) {
//            Get Current first name of PlayerPurchase, and assign it to variable
                    purchaseID = rs.getInt("PurchaseID");
                }
                isDeleted = rs.getBoolean("Deleted");

            }


//            Checks if the PlayerPurchase is already soft-deleted
            if (!isDeleted) {
                //            Add the parameters to the sp and Execute
                sqlQuery = "{call spAddUpdateDeletePlayerPurchase (?, ?, ?, ?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);
                callableStatement.setInt("PlayerPurchaseID", playerPurchaseID);
                callableStatement.setInt("PurchaseID", purchaseID);
                callableStatement.setInt("PlayerID", playerID);
                callableStatement.setBoolean("Deleted", true);
                callableStatement.execute();
                System.out.println("PlayerPurchase Deleted Successfully!");
            } else {
                System.out.println("The PlayerPurchaseID provided does not exist - Database Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("PlayerPurchase could not be Deleted");
            e.printStackTrace();
        }
    }


    public static PlayerPurchase getInstance() {
        return playerPurchase;
    }
}
