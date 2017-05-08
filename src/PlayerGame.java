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
public class PlayerGame {
    private int PlayerGameID, PlayerID, GameID;
    static private PlayerGame playerGame = new PlayerGame();
    private String sqlQuery;
    private CallableStatement callableStatement;
    private Statement statement;

    private PlayerGame() {
    }

    public void addPlayerGame(@NotNull int playerID, @NotNull int gameID) {
        sqlQuery = "{call spAddUpdateDeletePlayerGame (?, ?, ?, ?)}";
        try {
//            SQL-ify the call to the DB
            callableStatement = SQLServer.conn.prepareCall(sqlQuery);

//            Add the parameters to the sp and Execute
            callableStatement.setInt(1, 0);
            callableStatement.setInt(2, gameID);
            callableStatement.setInt(3, playerID);
            callableStatement.setBoolean(4, false);
            callableStatement.execute();
            System.out.println("PlayerGame Successfully added!");

        } catch (SQLException e) {
            System.out.println("There was a problem adding the PlayerGame to the database");
            e.printStackTrace();
        }
    }

    public void updatePlayerGame(@NotNull int playerGameID, @NotNull int playerID, @NotNull int gameID) {
        boolean isDeleted = false;

//        Actual SQL query to PlayerGame
        sqlQuery = String.format("SELECT * FROM tblPlayerGame WHERE PlayerGameID = %d", playerGameID);
        try {
//            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

//            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                //        Retrieve the current data from the Table to maybe reassign
                if (playerID <= 0) {
//            Get Current first name of PlayerGame, and assign it to variable
                    playerID = rs.getInt("PlayerID");
                }
                if (gameID <= 0) {
//            Get Current first name of PlayerGame, and assign it to variable
                    gameID = rs.getInt("GameID");
                }
                isDeleted = rs.getBoolean("Deleted");

            }

//            Checks if the PlayerGame is already soft-deleted
            if (!isDeleted) {
                //            Add the parameters to the sp and Execute
                sqlQuery = "{call spAddUpdateDeletePlayerGame (?, ?, ?, ?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);
                callableStatement.setInt("PlayerGameID", playerGameID);
                callableStatement.setInt("GameID", gameID);
                callableStatement.setInt("PlayerID", playerID);
                callableStatement.setBoolean("Deleted", false);
                callableStatement.execute();
                System.out.println("PlayerGame Updated Successfully!");
            } else {
                System.out.println("The PlayerGameID provided does not exist\nDatabase Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("PlayerGame not updated");
            e.printStackTrace();
        }
    }

    public void deletePlayerGame(@NotNull int playerGameID) {
        int gameID = 0, playerID = 0;
        boolean isDeleted = false;

        //        Actual SQL query to PlayerGame
        sqlQuery = String.format("SELECT * FROM tblPlayerGame WHERE PlayerGameID = %d", playerGameID);
        try {
//            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

//            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                //        Retrieve the current data from the Table to maybe reassign
                if (playerID <= 0) {
//            Get Current first name of PlayerGame, and assign it to variable
                    playerID = rs.getInt("PlayerID");
                }
                if (gameID <= 0) {
//            Get Current first name of PlayerGame, and assign it to variable
                    gameID = rs.getInt("GameID");
                }
                isDeleted = rs.getBoolean("Deleted");

            }


//            Checks if the PlayerGame is already soft-deleted
            if (!isDeleted) {
                //            Add the parameters to the sp and Execute
                sqlQuery = "{call spAddUpdateDeletePlayerGame (?, ?, ?, ?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);
                callableStatement.setInt("PlayerGameID", playerGameID);
                callableStatement.setInt("GameID", gameID);
                callableStatement.setInt("PlayerID", playerID);
                callableStatement.setBoolean("Deleted", true);
                callableStatement.execute();
                System.out.println("PlayerGame Deleted Successfully!");
            } else {
                System.out.println("The PlayerGameID provided does not exist - Database Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("PlayerGame could not be Deleted");
            e.printStackTrace();
        }
    }


    public static PlayerGame getInstance() {
        return playerGame;
    }
}
