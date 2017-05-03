public class Main {

    public static void main(String[] args) {

//        Gets the singleton instance to use || equivalent to --> SQLServer sqlserver = new SQLServer()
        SQLServer sqlServer = SQLServer.getInstance();

//        Connect to Casino Database. REQUIRED FIRST to perform any other function
        sqlServer.connectToDB();

    }
}
