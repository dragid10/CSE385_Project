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
//        System.out.println(player.getSpending(3));
//        System.out.println(player.getSpending(26));

//        ======= SLOT =======
        Slot slot = Slot.getInstance();
//        slot.addSlot("Roy", "Blips and Chitz");
//        slot.updateSlot(28, "PleepBlopSkip", "Earth-2833");
//        slot.deleteSlot(28);
      /*  ArrayList<String> result = slot.getHighestWin(2);
        for (String s : result) {
            System.out.println(s);
        }*/

//        ======= PURCHASE =======
        Purchase purchase = Purchase.getInstance();
//      ArrayList<String> result1 = purchase.getLastPurchaseFromStore();
//      ArrayList<String> result2 = purchase.getLastPurchaseFromStore(5);

//        ======= ITEM =======
        Item item = Item.getInstance();
//        item.addItem("Purple-Flurp", 50);
//        item.updateItem(10, "Cocaina", -6);
//        item.deleteItem(11);

//        ======= GAME =======
        Game game = Game.getInstance();
//        game.addGame(5, 80, "10/08/2017");
//        game.updateGame(30, 8, 50, "10/09/2015");
//        game.deleteGame(31);

//       ======= PLAYERGAME =======
        PlayerGame playerGame = PlayerGame.getInstance();
//        playerGame.addPlayerGame(9, 31);
//        playerGame.updatePlayerGame(21, 11, 21);
//        playerGame.deletePlayerGame(23);

//     ======= PLAYERPURCHASE =======
        PlayerPurchase playerPurchase = PlayerPurchase.getInstance();
//        playerPurchase.addPlayerPurchase(9, 31);
//        playerPurchase.updatePlayerPurchase(21, 11, 21);
//        playerPurchase.deletePlayerPurchase(23);
    }

    //     ======= PURCHASEITEM =======
    PurchaseItem purchaseItem = PurchaseItem.getInstance();
//        purchaseItem.addPurchaseItem(9, 31);
//        purchaseItem.updatePurchaseItem(21, 11, 21);
//        purchaseItem.deletePurchaseItem(23);

    //     ======= SLOTGAME =======
    SlotGame slotGame = SlotGame.getInstance();
//        slotGame.addSlotGame(9, 31);
//        slotGame.updateSlotGame(21, 11, 21);
//        slotGame.deleteSlotGame(23);
}

