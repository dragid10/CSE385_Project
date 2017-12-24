import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.sql.*;

/**
 * Name: Alex Oladele
 * Date: 5/6/17
 * Assignment: CSE385_Project
 */
public class Player extends EmptyString {
    private static Player player = new Player();
    private String sqlQuery;
    private CallableStatement callableStatement;
    private Statement statement;

    private Player() {
    }

    public void addPlayer(@NotNull String firstName, @NotNull String lastName, @NotNull String emailAddress, @NotNull String password, @Nullable String rewardsCardLevel) {
        sqlQuery = "{call spAddUpdatePlayer (?, ?, ?, ?, ?, ?)}";
        try {
//            SQL-ify the call to the DB
            callableStatement = SQLServer.conn.prepareCall(sqlQuery);

//            Add the parameters to the sp and Execute
            callableStatement.setInt(1, 0);
            callableStatement.setString(2, firstName);
            callableStatement.setString(3, lastName);
            callableStatement.setString(4, emailAddress);
            callableStatement.setString(5, password);
            callableStatement.setString(6, rewardsCardLevel);
            callableStatement.execute();
            System.out.println("Player Successfully added!");

        } catch (SQLException e) {
            System.out.println("There was a problem adding the Player to the database");
            e.printStackTrace();
        }
    }

    public void updatePlayer(@NotNull int playerID, @Nullable String firstName, @Nullable String lastName, @Nullable String emailAddress, @Nullable String password, @Nullable String rewardsCardLevel) {
        boolean isDeleted = false;

        //        Actual SQL query to Player
        sqlQuery = String.format("SELECT * FROM tblPlayers WHERE PlayerID = %d", playerID);

        try {
//            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

            //            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                if (isEmpty(firstName)) {
//            Get Current first name of Player, and assign it to variable
                    firstName = rs.getString("FirstName");

                }
                if (isEmpty(lastName)) {
//            Get Current last name of Player, and assign it to variable
                    lastName = rs.getString("LastName");
                }

                if (isEmpty(emailAddress)) {
//            Get Current email of Player, and assign it to variable
                    emailAddress = rs.getString("Email");
                }

                if (isEmpty(password)) {
//            Get Current password of Player, and assign it to variable
                    password = rs.getString("Password");
                }

                if (isEmpty(rewardsCardLevel)) {
//            Get Current rewards card level of Player, and assign it to variable
                    rewardsCardLevel = rs.getString("Rewards_Card");
                }
                isDeleted = rs.getBoolean("Deleted");
            }


//            Checks if the Player is already soft-deleted
            if (!isDeleted) {
                //            Add the parameters to the sp and Execute
                sqlQuery = "{call spAddUpdatePlayer (?, ?, ?, ?, ?, ?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);
                callableStatement.setInt("PlayerID", playerID);
                callableStatement.setString("FirstName", firstName);
                callableStatement.setString("LastName", lastName);
                callableStatement.setString("Email", emailAddress);
                callableStatement.setString("Password", password);
                if (isEmpty(rewardsCardLevel)) {
                    callableStatement.setNull("Rewards_Card", Types.VARCHAR);
                } else {
                    callableStatement.setString("Rewards_card", rewardsCardLevel);
                }
                callableStatement.execute();
                System.out.println("Player Updated Successfully!");
            } else {
                System.out.println("The PlayerID provided does not exist - Database Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("Player not updated");
            e.printStackTrace();
        }
    }

    public void deletePlayer(@NotNull int playerID) {
        boolean isDeleted = false;
        try {
            //        Actual SQL query to Player
            sqlQuery = String.format("SELECT * FROM tblPlayers WHERE PlayerID = %d", playerID);

//            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

            //            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                isDeleted = rs.getBoolean("Deleted");
            }

            if (!isDeleted) {
                //            SQL-ify the call to the DB
                sqlQuery = "{call spDeletePlayer (?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);

//            Add the parameters to the sp and Execute
                callableStatement.setInt("PlayerID", playerID);
                callableStatement.execute();
                System.out.println("Player Successfully Deleted!");
            } else {
                System.out.println("The PlayerID provided does not exist - Database Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("There was a problem Deleting the Player from the database");
            e.printStackTrace();
        }
    }

    public float getSpending(@NotNull int playerID) {
        float queryResult = 0;
        boolean isDeleted = false;

        try {
            ResultSet rs;
            statement = SQLServer.conn.createStatement();

//            Executes the getCustomerSpending SP
            sqlQuery = String.format("SELECT * FROM tblPlayers WHERE PlayerID = %d", playerID);
            rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                isDeleted = rs.getBoolean("Deleted");
            }

//            If player is soft deleted, return -1
            if (isDeleted) {
                System.out.println("The PlayerID provided does not exist - No Data Fetched");
                return -1;
            } else {
                sqlQuery = String.format("{call spGetCustomerSpendingByID (%d)}", playerID);
                rs = statement.executeQuery(sqlQuery);
                while (rs.next()) {
                    queryResult = rs.getFloat("TotalSpent");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queryResult;
    }

    public static Player getInstance() {
        return player;
    }
}
