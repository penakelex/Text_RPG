package penakelex.textRPG.homeland.Databases.InventoryDatabase;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Using_Volume;
import static penakelex.textRPG.homeland.Main.Constants.Using_Weight;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import penakelex.textRPG.homeland.Adapters.TradingAdapter.TradingInformation;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabase;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationDatabase;
import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationDatabaseHelper;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabaseHelper;
import penakelex.textRPG.homeland.R;

public class InventoryDatabaseHelper {
    public static InventoryItem getInventoryItem(int primaryID, Context context) {
        return InventoryDatabase.getDatabase(context).inventoryDao().getItem(primaryID);
    }
    public static ArrayList<TradingInformation> getTradingInformation(Context context, int ownerID, boolean typeTrading) {
        ArrayList<TradingInformation> arrayList = new ArrayList<>();
        List<InventoryItem> list = InventoryDatabase.getDatabase(context).inventoryDao().getInventory(ownerID);
        for (InventoryItem item : list) arrayList.add(new TradingInformation(getAllInventoryItemInformation(context, item.getId())[0], (typeTrading ? getItemPriceForPlayerBuying(context, item.getId()) : getItemPriceForPlayerSelling(context, item.getId()))));
        return (ArrayList<TradingInformation>) arrayList.clone();
    }

    public static String[] getInventoryItemShortInformation(Context context, int ID) {
        String[] shortItemInformation = new String[2], itemInformation = getAllInventoryItemInformation(context, ID);
        shortItemInformation[0] = itemInformation[0];
        shortItemInformation[1] = itemInformation[1];
        return shortItemInformation;
    }

    public static String[] getAllInventoryItemInformation(Context context, long ID) {
        String[] itemInformation = new String[5];
        switch ((int) ID) {
            case 1: {
                itemInformation[0] = context.getResources().getString(R.string.name_chocolate_bar); //name
                itemInformation[1] = context.getResources().getString(R.string.type_food); //type
                itemInformation[2] = String.valueOf(0.1); //weight
                itemInformation[3] = String.valueOf(75); //volume
                itemInformation[4] = context.getResources().getString(R.string.description_chocolate_bar); //description
            }
            break;
        }
        return itemInformation;
    }

    public static void insertNewItemToPlayersInventory(int ID, Context context, View view) {
        String[] itemInformation = getAllInventoryItemInformation(context, ID);
        float weight = Float.parseFloat(itemInformation[2]), volume = Float.parseFloat(itemInformation[3]);
        SharedPreferences sharedPreferences = context.getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        SQLiteDatabase database = new OtherInformationDatabaseHelper(context).getReadableDatabase();
        if (sharedPreferences.getFloat(Using_Weight, 0) + weight <= OtherInformationDatabase.getValue(database, 3) ||
                sharedPreferences.getFloat(Using_Volume, 0) + volume <= OtherInformationDatabase.getValue(database, 4))
            InventoryDatabase.getDatabase(context).inventoryDao().insertItem(new InventoryItem(ID, 1, getItemPriceForPlayerSelling(context, ID)));
        else Snackbar.make(view, context.getResources().getString(R.string.too_many_items), Snackbar.LENGTH_SHORT).setTextColor(context.getResources().getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
    }

    public static void trading(long primaryID, int newOwnerID, Context context) {
        InventoryDatabase database = InventoryDatabase.getDatabase(context);
        if (newOwnerID == 1) {
            database.inventoryDao().changeOwner(newOwnerID, getItemPriceForPlayerSelling(context, database.inventoryDao().getItem(primaryID).getId()), primaryID);
        } else {
            database.inventoryDao().changeOwner(newOwnerID, getItemPriceForPlayerBuying(context, database.inventoryDao().getItem(primaryID).getId()), primaryID);
        }
    }

    public static float getItemPriceForPlayerBuying(Context context, int ID) {
        short attractiveness = (short) CharacteristicsDatabase.
                getValue(new CharacteristicsDatabaseHelper(context).getReadableDatabase(), 7),
                trading = (short) SkillsDatabase.
                        getValueSkill(new SkillsDatabaseHelper(context).getReadableDatabase(), 5),
                basePrice = getBaseItemPrice(ID);
        return (float) (basePrice - basePrice * (attractiveness * 0.1 + trading * 0.005));
    }

    public static float getItemPriceForPlayerSelling(Context context, int ID) {
        short attractiveness = (short) CharacteristicsDatabase.
                getValue(new CharacteristicsDatabaseHelper(context).getReadableDatabase(), 7),
                trading = (short) SkillsDatabase.
                        getValueSkill(new SkillsDatabaseHelper(context).getReadableDatabase(), 5),
                basePrice = getBaseItemPrice(ID);
        return (float) (basePrice + basePrice * (attractiveness * 0.1 + trading * 0.005));
    }

    public static short getBaseItemPrice(int ID) {
        switch (ID) {
            case 1:
                return 7;
        }
        return 0;
    }

    public static boolean isItemForQuest(long ID, Context context) {
        InventoryItem item = InventoryDatabase.getDatabase(context).inventoryDao().getItem(ID);
        int id = item.getId();
        return id != 2;
    }
}
