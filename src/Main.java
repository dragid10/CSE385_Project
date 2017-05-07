import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

//        Gets the singleton instance to use || equivalent to --> SQLServer sqlserver = new SQLServer()
        SQLServer sqlServer = SQLServer.getInstance();

//        Connect to Casino Database. REQUIRED FIRST to perform any other function
        sqlServer.connectToDB();

//        SP Testing

//        ======= EMPLOYEE =======
        Employee employee = Employee.getInstance();
//        employee.addEmployee("Logan", "Johnson", "AAAYO@AYAAY.com", "password");
//        employee.updateEmployee(10, "TACO", "DOMO", "tyler@oddfuture.com", "oddfuture");
//        employee.deleteEmployee(11);

//        ======= PLAYER =======
        Player player = Player.getInstance();
//        player.addPlayer("Frank", "Ocean", "frankocean@oceans.com", "nostalgia","VIP");
//        player.updatePlayer(25,"Frank", "Ocean", "percyocean@greek.com", "riptide","VIP");
//        player.deletePlayer(27);
        ArrayList<String> result = player.getSpending(3);
        for (String s: result) {
            System.out.println(s);
        }


    }
}
