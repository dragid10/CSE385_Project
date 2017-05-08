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
public class SlotGame extends EmptyString {
    static private SlotGame slotGame = new SlotGame();
    private String sqlQuery;
    private CallableStatement callableStatement;
    private Statement statement;

    private SlotGame() {
    }

    public void addSlotGame(@NotNull int slotID, @NotNull int gameID) {
        sqlQuery = "{call spAddUpdateDeleteSlotGame (?, ?, ?, ?)}";
        try {
//            SQL-ify the call to the DB
            callableStatement = SQLServer.conn.prepareCall(sqlQuery);

//            Add the parameters to the sp and Execute
            callableStatement.setInt(1, 0);
            callableStatement.setInt(2, gameID);
            callableStatement.setInt(3, slotID);
            callableStatement.setBoolean(4, false);
            callableStatement.execute();
            System.out.println("SlotGame Successfully added!");

        } catch (SQLException e) {
            System.out.println("There was a problem adding the SlotGame to the database");
            e.printStackTrace();
        }
    }

    public void updateSlotGame(@NotNull int slotGameID, @NotNull int slotID, @NotNull int gameID) {
        boolean isDeleted = false;

//        Actual SQL query to SlotGame
        sqlQuery = String.format("SELECT * FROM tblSlotGame WHERE SlotGameID = %d", slotGameID);
        try {
//            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

//            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                //        Retrieve the current data from the Table to maybe reassign
                if (slotID <= 0) {
//            Get Current first name of SlotGame, and assign it to variable
                    slotID = rs.getInt("SlotID");
                }
                if (gameID <= 0) {
//            Get Current first name of SlotGame, and assign it to variable
                    gameID = rs.getInt("GameID");
                }
                isDeleted = rs.getBoolean("Deleted");

            }

//            Checks if the SlotGame is already soft-deleted
            if (!isDeleted) {
                //            Add the parameters to the sp and Execute
                sqlQuery = "{call spAddUpdateDeleteSlotGame (?, ?, ?, ?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);
                callableStatement.setInt("SlotGameID", slotGameID);
                callableStatement.setInt("GameID", gameID);
                callableStatement.setInt("SlotID", slotID);
                callableStatement.setBoolean("Deleted", false);
                callableStatement.execute();
                System.out.println("SlotGame Updated Successfully!");
            } else {
                System.out.println("The SlotGameID provided does not exist\nDatabase Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("SlotGame not updated");
            e.printStackTrace();
        }
    }

    public void deleteSlotGame(@NotNull int slotGameID) {
        int gameID = 0, slotID = 0;
        boolean isDeleted = false;

        //        Actual SQL query to SlotGame
        sqlQuery = String.format("SELECT * FROM tblSlotGame WHERE SlotGameID = %d", slotGameID);
        try {
//            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

//            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                //        Retrieve the current data from the Table to maybe reassign
                if (slotID <= 0) {
//            Get Current first name of SlotGame, and assign it to variable
                    slotID = rs.getInt("SlotID");
                }
                if (gameID <= 0) {
//            Get Current first name of SlotGame, and assign it to variable
                    gameID = rs.getInt("GameID");
                }
                isDeleted = rs.getBoolean("Deleted");

            }


//            Checks if the SlotGame is already soft-deleted
            if (!isDeleted) {
                //            Add the parameters to the sp and Execute
                sqlQuery = "{call spAddUpdateDeleteSlotGame (?, ?, ?, ?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);
                callableStatement.setInt("SlotGameID", slotGameID);
                callableStatement.setInt("GameID", gameID);
                callableStatement.setInt("SlotID", slotID);
                callableStatement.setBoolean("Deleted", true);
                callableStatement.execute();
                System.out.println("SlotGame Deleted Successfully!");
            } else {
                System.out.println("The SlotGameID provided does not exist - Database Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("SlotGame could not be Deleted");
            e.printStackTrace();
        }
    }


    public static SlotGame getInstance() {
        return slotGame;
    }
}
