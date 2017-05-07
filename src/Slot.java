import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;

/**
 * Name: Alex Oladele
 * Date: 5/6/17
 * Assignment: CSE385_Project
 */
public class Slot extends EmptyString {
    private int slotID;
    private String slotName, location;
    private boolean isAvailable, isDeleted;

    public void addSlot(@NotNull String slotName, @NotNull String location) {
    }

    public void updateSlot(@NotNull int slotID, @Nullable String slotName, @Nullable String location) {
        if (isEmpty(slotName)) {
//            Get Current name of item, and assign it to that
        }
        if (isEmpty(location)) {
//            Get Current price of item, and assign it to that
        }

//        Call SP

    }

    public void deleteSlot(@NotNull int SlotID) {
    }

    public ArrayList<String> getHighestWin(){

        //TODO FIX THIS
        return new ArrayList<>();
    }
    public ArrayList<String> getHighestWin(int slotID){
        //TODO FIX THIS
        return new ArrayList<>();}
}
