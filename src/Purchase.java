import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Name: Alex Oladele
 * Date: 5/6/17
 * Assignment: CSE385_Project
 */
public class Purchase {
    private static Purchase purchase = new Purchase();
    private String sqlQuery;
    private CallableStatement callableStatement;
    private Statement statement;


    private Purchase() {
    }

    public ArrayList<String> getLastPurchaseFromStore() {
        ArrayList<String> returnResult = new ArrayList<>();
        try {
            ResultSet rs;
            statement = SQLServer.conn.createStatement();
                sqlQuery = "{call spGetLastPurchaseFromStore}";
                rs = statement.executeQuery(sqlQuery);
                while (rs.next()) {
                    returnResult.add(rs.getString("Customer"));
                    returnResult.add(rs.getString("ItemName"));
                    returnResult.add(rs.getString("ItemPrice"));
                    returnResult.add(rs.getString("PurchaseDate"));
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnResult;
    }

    public ArrayList<String> getLastPurchaseFromStore(int playerID) {
        ArrayList<String> returnResult = new ArrayList<>();
        boolean isDeleted = false;

        try {
            ResultSet rs;
            statement = SQLServer.conn.createStatement();

            //            Executes the getHighestWinBySlot SP
            sqlQuery = String.format("SELECT * FROM tblPurchases WHERE PurchaseID = %d", playerID);
            rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                isDeleted = rs.getBoolean("Deleted");
            }

            if (isDeleted) {
                System.out.println("The PurchaseID provided does not exist - No Data Fetched");
                return new ArrayList<>();
            } else {
                sqlQuery = String.format("{call spGetLastPurchaseFromStoreByID (%d)}", playerID);
                rs = statement.executeQuery(sqlQuery);
                while (rs.next()) {
                    returnResult.add(rs.getString("Customer"));
                    returnResult.add(rs.getString("ItemName"));
                    returnResult.add(rs.getString("ItemPrice"));
                    returnResult.add(rs.getString("PurchaseDate"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnResult;
    }

    public static Purchase getInstance() {
        return purchase;
    }
}
