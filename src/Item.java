import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Name: Alex Oladele
 * Date: 5/6/17
 * Assignment: CSE385_Project
 */
public class Item extends EmptyString {
    static private Item item = new Item();
    private String sqlQuery;
    private CallableStatement callableStatement;
    private Statement statement;

    private Item() {
    }

    public void addItem(@NotNull String itemName, @NotNull float price) {
        sqlQuery = "{call spAddItem (?, ?, ?, ?)}";
        try {
            callableStatement = SQLServer.conn.prepareCall(sqlQuery);

            //            Add the parameters to the sp and Execute
            callableStatement.setInt(1, 0);
            callableStatement.setString(2, itemName);
            callableStatement.setFloat(3, price);
            callableStatement.setBoolean(4, false);
            callableStatement.execute();
            System.out.println("Item Successfully added!");
        } catch (Exception e) {
            System.out.println("There was a problem adding the Item to the database");
            e.printStackTrace();
        }
    }

    public void updateItem(@NotNull int itemID, @Nullable String itemName, @Nullable float newPrice) {
        boolean isDeleted = false;

        //        Actual SQL query to Item
        sqlQuery = String.format("SELECT * FROM tblItems WHERE itemID = %d", itemID);
        try {
            //            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

            //            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);


            while (rs.next()) {
                if (isEmpty(itemName)) {
//            Get Current name of Item, and assign it to that
                    itemName = rs.getString("ItemName");
                }
                if (newPrice < 0) {
//            Get Current price of location, and assign it to that
                    newPrice = rs.getFloat("ItemPrice");
                }
                isDeleted = rs.getBoolean("Deleted");
            }

//        Call SP
            if (!isDeleted) {
                sqlQuery = "{call spUpdateItem (?, ?, ?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);

                callableStatement.setInt("ItemID", itemID);
                callableStatement.setString("ItemName", itemName);
                callableStatement.setFloat("ItemPrice", newPrice);
                callableStatement.execute();
                System.out.println("Item Updated Successfully!");
            } else {
                System.out.println("The ItemID provided does not exist - Database Was not altered");
            }
        } catch (Exception e) {
            System.out.println("Item not updated");
            e.printStackTrace();
        }
    }

    public void deleteItem(@NotNull int itemID) {
        boolean isDeleted = false;

        try {
            //        Actual SQL query to Item
            sqlQuery = String.format("SELECT * FROM tblItems WHERE ItemID = %d", itemID);

//            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

            //            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                isDeleted = rs.getBoolean("Deleted");
            }

            if (!isDeleted) {
                //            SQL-ify the call to the DB
                sqlQuery = "{call spDeleteItem (?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);

//            Add the parameters to the sp and Execute
                callableStatement.setInt("ItemID", itemID);
                callableStatement.execute();
                System.out.println("Item Successfully Deleted!");
            } else {
                System.out.println("The ItemID provided does not exist - Database Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("There was a problem Deleting the Item from the database");
            e.printStackTrace();
        }
    }

    public static Item getInstance() {
        return item;
    }
}
