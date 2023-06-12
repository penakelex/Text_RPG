package penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Using_Volume;
import static penakelex.textRPG.homeland.Main.Constants.Using_Weight;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicItem;
import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationItem;
import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillsItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.CharacteristicsViewModel.CharacteristicsViewModel;
import penakelex.textRPG.homeland.ViewModels.InventoryViewModel.InventoryViewModel;
import penakelex.textRPG.homeland.ViewModels.OtherInformationViewModel.OtherInformationViewModel;
import penakelex.textRPG.homeland.ViewModels.SkillsViewModel.SkillsViewModel;

public class InventoryTableHelper {
    private final InventoryViewModel inventoryViewModel;
    private CharacteristicsViewModel characteristicsViewModel = null;
    private SkillsViewModel skillsViewModel = null;
    private OtherInformationViewModel otherInformationViewModel = null;

    public InventoryTableHelper(InventoryViewModel inventoryViewModel) {
        this.inventoryViewModel = inventoryViewModel;

    }

    public InventoryTableHelper(InventoryViewModel inventoryViewModel, CharacteristicsViewModel characteristicsViewModel, SkillsViewModel skillsViewModel) {
        this.inventoryViewModel = inventoryViewModel;
        this.characteristicsViewModel = characteristicsViewModel;
        this.skillsViewModel = skillsViewModel;
    }

    public InventoryTableHelper(InventoryViewModel inventoryViewModel, CharacteristicsViewModel characteristicsViewModel, SkillsViewModel skillsViewModel, OtherInformationViewModel otherInformationViewModel) {
        this.inventoryViewModel = inventoryViewModel;
        this.characteristicsViewModel = characteristicsViewModel;
        this.skillsViewModel = skillsViewModel;
        this.otherInformationViewModel = otherInformationViewModel;
    }

    public void trading(InventoryItem inventoryItem, short owner) {
        CharacteristicItem attractiveness = characteristicsViewModel.getCharacteristic((byte) 7);
        SkillsItem trading = skillsViewModel.getSkill((byte) 5);
        short basePrice = getBaseItemPrice(inventoryItem.getId());
        if (owner == 1) {
            inventoryViewModel.changeOwner(owner, (float) (basePrice - basePrice * (attractiveness.getValue() * 0.1 + trading.getValue() * 0.005)), (short) inventoryItem.getPrimaryID());
        } else {
            inventoryViewModel.changeOwner(owner, (float) (basePrice + basePrice * (attractiveness.getValue() * 0.1 + trading.getValue() * 0.005)), (short) inventoryItem.getPrimaryID());
        }
    }

    public boolean throwAwayItem(InventoryItem item, SharedPreferences sharedPreferences, Context context) {
        if (item != null && !isItemForQuest(
                //item.getId()
        )) {
            inventoryViewModel.throwAwayItem(item);
            float[] values = getItemWeightAndVolume(item.getId(), context);
            sharedPreferences.edit().putFloat(Using_Volume, sharedPreferences.getFloat(Using_Volume, 0) - values[1]).apply();
            sharedPreferences.edit().putFloat(Using_Weight, sharedPreferences.getFloat(Using_Weight, 0) - values[0]).apply();
            return true;
        } else return false;
    }

    public void insertNewItemToPlayersInventory(short ID, Context context, View view) {
        String[] itemInformation = getAllInventoryItemInformation(context, ID);
        float weight = Float.parseFloat(itemInformation[2]), volume = Float.parseFloat(itemInformation[3]);
        SharedPreferences sharedPreferences = context.getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        List<OtherInformationItem> otherInformation = otherInformationViewModel.getAllOtherInformation();
        if (isHavingFreeSpace(otherInformation, sharedPreferences, weight, volume)) {
            SkillsItem trading = skillsViewModel.getSkill((byte) 5);
            sharedPreferences.edit().putFloat(Using_Weight, weight + sharedPreferences.getFloat(Using_Volume, 0)).putFloat(Using_Volume, volume + sharedPreferences.getFloat(Using_Volume, 0)).apply();
            CharacteristicItem attractiveness = characteristicsViewModel.getCharacteristic((byte) 7);
            short basePrice = getBaseItemPrice(ID);
            inventoryViewModel.add(new InventoryItem(ID, (short) 1, (float) (basePrice + basePrice * (attractiveness.getValue() * 0.1 + trading.getValue() * 0.005))));
        } else
            Snackbar.make(view, context.getResources().getString(R.string.too_many_items), Snackbar.LENGTH_SHORT).setTextColor(context.getResources().getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
    }

    public void insertNewItemToSomeoneInventory(short ID, short owner) {
        CharacteristicItem attractiveness = characteristicsViewModel.getCharacteristic((byte) 7);
        SkillsItem trading = skillsViewModel.getSkill((byte) 5);
        short basePrice = getBaseItemPrice(ID);
        inventoryViewModel.add(new InventoryItem(ID, owner, (float) (basePrice - basePrice * (attractiveness.getValue() * 0.1 + trading.getValue() * 0.005))));
    }

    private boolean isHavingFreeSpace(List<OtherInformationItem> otherInformationItems, SharedPreferences sharedPreferences, float weight, float volume) {
        return sharedPreferences.getFloat(Using_Weight, 0) + weight <= otherInformationItems.get(2).getValue() ||
                sharedPreferences.getFloat(Using_Volume, 0) + volume <= otherInformationItems.get(2).getValue();
    }

    public boolean isItemForQuest(
            //        short ID
    ) {
        return false;
    }

    private short getBaseItemPrice(short ID) {
        return switch ((int) ID) {
            case 1 -> 7;
            default -> 0;
        };
    }

    public String[] getAllInventoryItemInformation(Context context, short ID) {
        String[] itemInformation = new String[5];
        switch ((int) ID) {
            case 1 -> {
                itemInformation[0] = context.getResources().getString(R.string.name_chocolate_bar); //name
                itemInformation[1] = context.getResources().getString(R.string.type_food); //type
                itemInformation[2] = String.valueOf(0.1); //weight
                itemInformation[3] = String.valueOf(75); //volume
                itemInformation[4] = context.getResources().getString(R.string.description_chocolate_bar); //description
            }
            case 2 -> {
                itemInformation[0] = context.getResources().getString(R.string.name_water); //name
                itemInformation[1] = context.getResources().getString(R.string.type_food); //type
                itemInformation[2] = String.valueOf(0.1); //weight
                itemInformation[3] = String.valueOf(75); //volume
                itemInformation[4] = context.getResources().getString(R.string.description_water); //description
            }
        }
        return itemInformation;
    }

    private float[] getItemWeightAndVolume(short ID, Context context) {
        String[] itemInformation = getAllInventoryItemInformation(context, ID);
        return new float[]{Float.parseFloat(itemInformation[2]), Float.parseFloat(itemInformation[3])};
    }

    public String[] getInventoryItemShortInformation(Context context, short ID) {
        String[] itemInformation = getAllInventoryItemInformation(context, ID);
        return new String[]{itemInformation[0], itemInformation[1]};
    }
}
