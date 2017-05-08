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
public class Game extends EmptyString {
    private int gameID;
    private float bet, win, profit;
    private String gamePlayed;
    static private Game game = new Game();
    private String sqlQuery;
    private CallableStatement callableStatement;
    private Statement statement;

    private Game() {
    }

    public void addGame(@NotNull float bet, @NotNull float win, @NotNull String date) {
        sqlQuery = "{call spAddUpdateGame (?, ?, ?, ?, ?)}";
        try {
            callableStatement = SQLServer.conn.prepareCall(sqlQuery);

            //            Add the parameters to the sp and Execute
            callableStatement.setInt(1, 0);
            callableStatement.setFloat(2, bet);
            callableStatement.setFloat(3, win);
            callableStatement.setString(4, date);
            callableStatement.setBoolean(5, false);
            callableStatement.execute();
            System.out.println("Game Successfully added!");
        } catch (Exception e) {
            System.out.println("There was a problem adding the Game to the database");
            e.printStackTrace();
        }
    }

    public void updateGame(@NotNull int gameID, @Nullable float bet, @Nullable float win, @Nullable String date) {
        boolean isDeleted = false;

        //        Actual SQL query to Game
        sqlQuery = String.format("SELECT * FROM tblGames WHERE gameID = %d", gameID);
        try {
            //            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

            //            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);


            while (rs.next()) {
                if (isEmpty(date)) {
//            Get Current name of Game, and assign it to that
                    date = rs.getString("GamePlayed");
                }
                isDeleted = rs.getBoolean("Deleted");
            }

//        Call SP
            if (!isDeleted) {
                sqlQuery = "{call spAddUpdateGame (?, ?, ?, ?, ?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);

                callableStatement.setInt("GameID", gameID);
                callableStatement.setFloat("Bet", bet);
                callableStatement.setFloat("Win", win);
                callableStatement.setString("GamePlayed", date);
                callableStatement.setBoolean("Deleted", false);
                callableStatement.execute();
                System.out.println("Game Updated Successfully!");
            } else {
                System.out.println("The GameID provided does not exist - Database Was not altered");
            }
        } catch (Exception e) {
            System.out.println("Game not updated");
            e.printStackTrace();
        }
    }

    public void deleteGame(@NotNull int gameID) {
        boolean isDeleted = false;

        try {
            //        Actual SQL query to Game
            sqlQuery = String.format("SELECT * FROM tblGames WHERE GameID = %d", gameID);

//            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

            //            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                isDeleted = rs.getBoolean("Deleted");
            }

            if (!isDeleted) {
                //            SQL-ify the call to the DB
                sqlQuery = "{call spDeleteGame (?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);

//            Add the parameters to the sp and Execute
                callableStatement.setInt("GameID", gameID);
                callableStatement.execute();
                System.out.println("Game Successfully Deleted!");
            } else {
                System.out.println("The GameID provided does not exist - Database Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("There was a problem Deleting the Game from the database");
            e.printStackTrace();
        }
    }

    public int getWinRatio() {
        int winRatio = 0;
        try {
            statement = SQLServer.conn.createStatement();

            sqlQuery = "{call spGetCustomerWinRatio}";
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                winRatio = rs.getInt("WinLossRatio");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return winRatio;
    }

    public static Game getInstance() {
        return game;
    }
}
