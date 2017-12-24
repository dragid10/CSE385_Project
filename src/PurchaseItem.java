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
public class PurchaseItem extends EmptyString{
    private static PurchaseItem purchaseItem = new PurchaseItem();
    private String sqlQuery;
    private CallableStatement callableStatement;
    private Statement statement;

    private PurchaseItem() {
    }

    public void addPurchaseItem(@NotNull int itemID, @NotNull int purchaseID) {
        sqlQuery = "{call spAddUpdateDeletePurchaseItem (?, ?, ?, ?)}";
        try {
//            SQL-ify the call to the DB
            callableStatement = SQLServer.conn.prepareCall(sqlQuery);

//            Add the parameters to the sp and Execute
            callableStatement.setInt(1, 0);
            callableStatement.setInt(2, purchaseID);
            callableStatement.setInt(3, itemID);
            callableStatement.setBoolean(4, false);
            callableStatement.execute();
            System.out.println("PurchaseItem Successfully added!");

        } catch (SQLException e) {
            System.out.println("There was a problem adding the PurchaseItem to the database");
            e.printStackTrace();
        }
    }

    public void updatePurchaseItem(@NotNull int purchaseItemID, @NotNull int itemID, @NotNull int purchaseID) {
        boolean isDeleted = false;

//        Actual SQL query to PurchaseItem
        sqlQuery = String.format("SELECT * FROM tblPurchaseItem WHERE PurchaseItemID = %d", purchaseItemID);
        try {
//            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

//            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                //        Retrieve the current data from the Table to maybe reassign
                if (itemID <= 0) {
//            Get Current first name of PurchaseItem, and assign it to variable
                    itemID = rs.getInt("ItemID");
                }
                if (purchaseID <= 0) {
//            Get Current first name of PurchaseItem, and assign it to variable
                    purchaseID = rs.getInt("PurchaseID");
                }
                isDeleted = rs.getBoolean("Deleted");

            }

//            Checks if the PurchaseItem is already soft-deleted
            if (!isDeleted) {
                //            Add the parameters to the sp and Execute
                sqlQuery = "{call spAddUpdateDeletePurchaseItem (?, ?, ?, ?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);
                callableStatement.setInt("PurchaseItemID", purchaseItemID);
                callableStatement.setInt("PurchaseID", purchaseID);
                callableStatement.setInt("ItemID", itemID);
                callableStatement.setBoolean("Deleted", false);
                callableStatement.execute();
                System.out.println("PurchaseItem Updated Successfully!");
            } else {
                System.out.println("The PurchaseItemID provided does not exist\nDatabase Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("PurchaseItem not updated");
            e.printStackTrace();
        }
    }

    public void deletePurchaseItem(@NotNull int purchaseItemID) {
        int purchaseID = 0, itemID = 0;
        boolean isDeleted = false;

        //        Actual SQL query to PurchaseItem
        sqlQuery = String.format("SELECT * FROM tblPurchaseItem WHERE PurchaseItemID = %d", purchaseItemID);
        try {
//            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

//            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                //        Retrieve the current data from the Table to maybe reassign
                if (itemID <= 0) {
//            Get Current first name of PurchaseItem, and assign it to variable
                    itemID = rs.getInt("ItemID");
                }
                if (purchaseID <= 0) {
//            Get Current first name of PurchaseItem, and assign it to variable
                    purchaseID = rs.getInt("PurchaseID");
                }
                isDeleted = rs.getBoolean("Deleted");

            }


//            Checks if the PurchaseItem is already soft-deleted
            if (!isDeleted) {
                //            Add the parameters to the sp and Execute
                sqlQuery = "{call spAddUpdateDeletePurchaseItem (?, ?, ?, ?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);
                callableStatement.setInt("PurchaseItemID", purchaseItemID);
                callableStatement.setInt("PurchaseID", purchaseID);
                callableStatement.setInt("ItemID", itemID);
                callableStatement.setBoolean("Deleted", true);
                callableStatement.execute();
                System.out.println("PurchaseItem Deleted Successfully!");
            } else {
                System.out.println("The PurchaseItemID provided does not exist - Database Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("PurchaseItem could not be Deleted");
            e.printStackTrace();
        }
    }


    public static PurchaseItem getInstance() {
        return purchaseItem;
    }
}
