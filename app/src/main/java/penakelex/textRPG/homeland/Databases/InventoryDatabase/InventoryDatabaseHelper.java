package penakelex.textRPG.homeland.Databases.InventoryDatabase;

import android.content.Context;

import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabase;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabaseHelper;
import penakelex.textRPG.homeland.R;

public class InventoryDatabaseHelper {

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

    public static void insertNewItemToPlayersInventory(int ID, Context context) {
        InventoryDatabase.getDatabase(context).inventoryDao().insertItem(new InventoryItem(ID, 1, getItemPriceForPlayerSelling(context, ID)));
    }

    public static void trading(int primaryID, int newOwnerID, Context context) {
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
            case 2:
                return 0;
        }
        return 0;
    }

    public static boolean isItemForQuest(long ID, Context context) {
        InventoryItem item = InventoryDatabase.getDatabase(context).inventoryDao().getItem(ID);
        int id = item.getId();
        return id != 2;
    }
}
