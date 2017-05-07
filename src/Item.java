import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * Name: Alex Oladele
 * Date: 5/6/17
 * Assignment: CSE385_Project
 */
public class Item {
    private int itemID;
    private String itemName;
    private float itemPrice;
    private boolean isDeleted;


    public void addItem(@NotNull float price, @NotNull String itemName) {}
    public void updateItem(@NotNull int itemID, @Nullable Float newPrice, @Nullable String itemName) {
        if (itemName.isEmpty() || itemName == null) {
//            Get Current name of item, and assign it to that
        }
        if (newPrice == null) {
//            Get Current price of item, and assign it to that

        }
    }
    public void deleteItem(@NotNull int itemID) {}




}
