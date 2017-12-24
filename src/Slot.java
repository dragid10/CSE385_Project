import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Name: Alex Oladele
 * Date: 5/6/17
 * Assignment: CSE385_Project
 */
public class Slot extends EmptyString {
    static private Slot slot = new Slot();
    private String sqlQuery;
    private CallableStatement callableStatement;
    private Statement statement;

    private Slot() {
    }

    public void addSlot(@NotNull String slotName, @NotNull String location) {
        sqlQuery = "{call spAddSlot (?, ?, ?, ?, ?)}";
        try {
            callableStatement = SQLServer.conn.prepareCall(sqlQuery);

            //            Add the parameters to the sp and Execute
            callableStatement.setInt(1, 0);
            callableStatement.setString(2, slotName);
            callableStatement.setString(3, location);
            callableStatement.setBoolean(4, true);
            callableStatement.setBoolean(5, false);
            callableStatement.execute();
            System.out.println("Slot Successfully added!");
        } catch (Exception e) {
            System.out.println("There was a problem adding the Slot to the database");
            e.printStackTrace();
        }
    }

    public void updateSlot(@NotNull int slotID, @Nullable String slotName, @Nullable String location, @NotNull boolean isAvailable) {
        boolean isDeleted = false;

        //        Actual SQL query to Slot
        sqlQuery = String.format("SELECT * FROM tblSlots WHERE SlotID = %d", slotID);
        try {
            //            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

            //            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);


            while (rs.next()) {
                if (isEmpty(slotName)) {
//            Get Current name of slot, and assign it to that
                    slotName = rs.getString("SlotName");
                }
                if (isEmpty(location)) {
//            Get Current price of location, and assign it to that
                    location = rs.getString("Location");
                }
                isDeleted = rs.getBoolean("Deleted");
            }

//        Call SP
            if (!isDeleted) {
                sqlQuery = "{call spUpdateSlot (?, ?, ?, ?, ?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);

                callableStatement.setInt("SlotID", slotID);
                callableStatement.setString("SlotName", slotName);
                callableStatement.setString("Location", location);
                callableStatement.setBoolean("Available", isAvailable);
                callableStatement.setBoolean("Deleted", false);
                callableStatement.execute();
                System.out.println("Slot Updated Successfully!");
            } else {
                System.out.println("The SlotID provided does not exist - Database Was not altered");
            }
        } catch (Exception e) {
            System.out.println("Slot not updated");
            e.printStackTrace();
        }
    }

    public void deleteSlot(@NotNull int slotID) {
        boolean isDeleted = false;

        try {
            //        Actual SQL query to Slot
            sqlQuery = String.format("SELECT * FROM tblSlots WHERE SlotID = %d", slotID);

//            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

            //            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                isDeleted = rs.getBoolean("Deleted");
            }

            if (!isDeleted) {
                //            SQL-ify the call to the DB
                sqlQuery = "{call spDeleteSlot (?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);

//            Add the parameters to the sp and Execute
                callableStatement.setInt("SlotID", slotID);
                callableStatement.execute();
                System.out.println("Slot Successfully Deleted!");
            } else {
                System.out.println("The SlotID provided does not exist - Database Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("There was a problem Deleting the Slot from the database");
            e.printStackTrace();
        }
    }

    public ArrayList<String> getHighestWin(int slotID) {
        ArrayList<String> returnResult = new ArrayList<>();
        boolean isDeleted = false;

        try {
            ResultSet rs;
            statement = SQLServer.conn.createStatement();

            //            Executes the getHighestWinBySlot SP
            sqlQuery = String.format("SELECT * FROM tblSlots WHERE SlotID = %d", slotID);
            rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                isDeleted = rs.getBoolean("Deleted");
            }

            if (isDeleted) {
                System.out.println("The SlotID provided does not exist - No Data Fetched");
                return new ArrayList<>();
            } else {
                sqlQuery = String.format("{call spGetHighestWinBySlot (%d)}", slotID);
                rs = statement.executeQuery(sqlQuery);
                while (rs.next()) {
                    returnResult.add(rs.getString("Player"));
                    returnResult.add(rs.getString("SlotName"));
                    returnResult.add(rs.getString("Location"));
                    returnResult.add(rs.getFloat("Win") + "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnResult;
    }

    public static Slot getInstance() {
        return slot;
    }
}
