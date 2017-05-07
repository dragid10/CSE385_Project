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
public class Employee extends EmptyString {
    private String sqlQuery;
    private CallableStatement callableStatement;
    private Statement statement;
    private static Employee employee = new Employee();

    private Employee() {
    }

    public void addEmployee(@NotNull String employeeFirstName, @NotNull String employeeLastName, @NotNull String emailAddress, @NotNull String password) {
        sqlQuery = "{call spAddUpdateDeleteEmployee (?, ?, ?, ?, ?, ?)}";
        try {
//            SQL-ify the call to the DB
            callableStatement = SQLServer.conn.prepareCall(sqlQuery);

//            Add the parameters to the sp and Execute
            callableStatement.setInt(1, 0);
            callableStatement.setString(2, employeeFirstName);
            callableStatement.setString(3, employeeLastName);
            callableStatement.setString(4, emailAddress);
            callableStatement.setString(5, password);
            callableStatement.setBoolean(6, false);
            callableStatement.execute();
            System.out.println("Employee Successfully added!");

        } catch (SQLException e) {
            System.out.println("There was a problem adding the Employee to the database");
            e.printStackTrace();
        }
    }

    public void updateEmployee(@NotNull int employeeID, @Nullable String employeeFirstName, @Nullable String employeeLastName,
                               @Nullable String emailAddress, @Nullable String password) {
        boolean isDeleted = false;

//        Actual SQL query to Employee
        sqlQuery = String.format("SELECT * FROM tblEmployees WHERE EmployeeID = %d", employeeID);
        try {
//            SQL-ify's the string
            statement = SQLServer.conn.createStatement();

//            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                //        Retrieve the current data from the Table to maybe reassign
                if (isEmpty(employeeFirstName)) {
//            Get Current first name of employee, and assign it to variable
                    employeeFirstName = rs.getString("FirstName");
                }
                if (isEmpty(employeeLastName)) {
//            Get Current last name of employee, and assign it to variable
                    employeeLastName = rs.getString("LastName");
                }
                if (isEmpty(emailAddress)) {
//            Get Current email of employee, and assign it to variable
                    emailAddress = rs.getString("Email");
                }
                if (isEmpty(password)) {
//            Get Current password of item, and assign it to variable
                    password = rs.getString("Password");
                }

                isDeleted = rs.getBoolean("Deleted");

            }

//            Checks if the Employee is already soft-deleted
            if (!isDeleted) {
                //            Add the parameters to the sp and Execute
                sqlQuery = "{call spAddUpdateDeleteEmployee (?, ?, ?, ?, ?, ?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);
                callableStatement.setInt("EmployeeID", employeeID);
                callableStatement.setString("FirstName", employeeFirstName);
                callableStatement.setString("LastName", employeeLastName);
                callableStatement.setString("Email", emailAddress);
                callableStatement.setString("Password", password);
                callableStatement.setBoolean("Delete", false);
                callableStatement.execute();
                System.out.println("Employee Updated Successfully!");
            } else {
                System.out.println("The EmployeeID provided does not exist\nDatabase Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("Employee not updated");
            e.printStackTrace();
        }
    }

    public void deleteEmployee(@NotNull int employeeID) {
        String employeeFirstName = "", employeeLastName = "", emailAddress = "", password = "";
        boolean isDeleted = false;

        try {
//            Get info from the data already in the table
            sqlQuery = String.format("SELECT * FROM tblEmployees WHERE EmployeeID = %d", employeeID);
            //            SQL-ify's the string
            statement = SQLServer.conn.createStatement();
            //            Executes the query
            ResultSet rs = statement.executeQuery(sqlQuery);

            while (rs.next()) {
                //        Retrieve the current data from the Table to maybe reassign
                if (isEmpty(employeeFirstName)) {
//            Get Current first name of employee, and assign it to variable
                    employeeFirstName = rs.getString("FirstName");
                }
                if (isEmpty(employeeLastName)) {
//            Get Current last name of employee, and assign it to variable
                    employeeLastName = rs.getString("LastName");
                }
                if (isEmpty(emailAddress)) {
//            Get Current email of employee, and assign it to variable
                    emailAddress = rs.getString("Email");
                }
                if (isEmpty(password)) {
//            Get Current password of item, and assign it to variable
                    password = rs.getString("Password");
                }

                isDeleted = rs.getBoolean("Deleted");
            }

//            Checks if the Employee is already soft-deleted
            if (!isDeleted) {
                //            Add the parameters to the sp and Execute
                sqlQuery = "{call spAddUpdateDeleteEmployee (?, ?, ?, ?, ?, ?)}";
                callableStatement = SQLServer.conn.prepareCall(sqlQuery);
                callableStatement.setInt("EmployeeID", employeeID);
                callableStatement.setString("FirstName", employeeFirstName);
                callableStatement.setString("LastName", employeeLastName);
                callableStatement.setString("Email", emailAddress);
                callableStatement.setString("Password", password);
                callableStatement.setBoolean("Delete", true);
                callableStatement.execute();
                System.out.println("Employee Deleted Successfully!");
            } else {
                System.out.println("The EmployeeID provided does not exist - Database Was not altered");
            }
        } catch (SQLException e) {
            System.out.println("Employee could not be Deleted");
            e.printStackTrace();
        }

    }

    public static Employee getInstance() {
        return employee;
    }
}
