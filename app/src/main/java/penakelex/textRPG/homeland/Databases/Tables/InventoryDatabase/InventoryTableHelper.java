package penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Using_Volume;
import static penakelex.textRPG.homeland.Main.Constants.Using_Weight;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

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
    private final LifecycleOwner lifecycleOwner;
    private CharacteristicsViewModel characteristicsViewModel = null;
    private SkillsViewModel skillsViewModel = null;
    private OtherInformationViewModel otherInformationViewModel = null;

    public InventoryTableHelper(InventoryViewModel inventoryViewModel, LifecycleOwner lifecycleOwner) {
        this.inventoryViewModel = inventoryViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    public InventoryTableHelper(InventoryViewModel inventoryViewModel, CharacteristicsViewModel characteristicsViewModel, SkillsViewModel skillsViewModel, LifecycleOwner lifecycleOwner) {
        this.inventoryViewModel = inventoryViewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.characteristicsViewModel = characteristicsViewModel;
        this.skillsViewModel = skillsViewModel;
    }

    public InventoryTableHelper(InventoryViewModel inventoryViewModel, LifecycleOwner lifecycleOwner, CharacteristicsViewModel characteristicsViewModel, SkillsViewModel skillsViewModel, OtherInformationViewModel otherInformationViewModel) {
        this.inventoryViewModel = inventoryViewModel;
        this.lifecycleOwner = lifecycleOwner;
        this.characteristicsViewModel = characteristicsViewModel;
        this.skillsViewModel = skillsViewModel;
        this.otherInformationViewModel = otherInformationViewModel;
    }

    public void trading(InventoryItem inventoryItem, short owner) {
        LiveData<CharacteristicItem> attractiveness = characteristicsViewModel.getCharacteristic((byte) 7);
        attractiveness.observe(lifecycleOwner, characteristicItem -> {
            attractiveness.removeObservers(lifecycleOwner);
            LiveData<SkillsItem> trading = skillsViewModel.getSkill((byte) 5);
            trading.observe(lifecycleOwner, skillsItem -> {
                trading.removeObservers(lifecycleOwner);
                short basePrice = getBaseItemPrice(inventoryItem.getId());
                if (owner == 1) {
                    inventoryViewModel.changeOwner(owner, (float) (basePrice - basePrice * (characteristicItem.getValue() * 0.1 + skillsItem.getValue() * 0.005)), (short) inventoryItem.getId());
                } else {
                    inventoryViewModel.changeOwner(owner, (float) (basePrice + basePrice * (characteristicItem.getValue() * 0.1 + skillsItem.getValue() * 0.005)), (short) inventoryItem.getId());
                }
            });
        });
    }

    public boolean throwAwayItem(InventoryItem item, SharedPreferences sharedPreferences, Context context) {
        if (item != null && !isItemForQuest(item.getId())) {
            inventoryViewModel.throwAwayItem(item);
            float[] values = getItemWeightAndVolume(item.getId(), context);
            sharedPreferences.edit().putFloat(Using_Volume, sharedPreferences.getFloat(Using_Volume, 0) - values[1]).apply();
            sharedPreferences.edit().putFloat(Using_Weight, sharedPreferences.getFloat(Using_Weight, 0) - values[0]).apply();
            return true;
        } else return false;
    }

    /*public static InventoryItem getInventoryItem(long primaryID, Context context) {
        return InventoryDatabase.getDatabase(context).inventoryDao().getItem(primaryID);
    }
    public static ArrayList<TradingInformation> getTradingInformation(Context context, int ownerID, boolean typeTrading) {
        ArrayList<TradingInformation> arrayList = new ArrayList<>();
        List<InventoryItem> list = InventoryDatabase.getDatabase(context).inventoryDao().getInventory(ownerID);
        for (InventoryItem item : list) {
            arrayList.add(new TradingInformation(getAllInventoryItemInformation(context, item.getId())[0], (typeTrading ? getItemPriceForPlayerBuying(context, item.getId()) : getItemPriceForPlayerSelling(context, item.getId())), item.getPrimaryID()));
        }
        return arrayList;
    }

    public static String[] getInventoryItemShortInformation(Context context, int ID) {
        String[] shortItemInformation = new String[2], itemInformation = getAllInventoryItemInformation(context, ID);
        shortItemInformation[0] = itemInformation[0];
        shortItemInformation[1] = itemInformation[1];
        return shortItemInformation;
    }
*/
    public void insertNewItemToPlayersInventory(short ID, Context context, View view) {
        String[] itemInformation = getAllInventoryItemInformation(context, ID);
        float weight = Float.parseFloat(itemInformation[2]), volume = Float.parseFloat(itemInformation[3]);
        SharedPreferences sharedPreferences = context.getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        LiveData<List<OtherInformationItem>> otherInformation = otherInformationViewModel.getAllOtherInformation();
        otherInformation.observe(lifecycleOwner, new Observer<>() {
            @Override
            public void onChanged(List<OtherInformationItem> otherInformationItems) {
                otherInformation.removeObservers(lifecycleOwner);
                if (isHavingFreeSpace(otherInformationItems)) {
                    LiveData<CharacteristicItem> attractiveness = characteristicsViewModel.getCharacteristic((byte) 7);
                    attractiveness.observe(lifecycleOwner, characteristicItem -> {
                        attractiveness.removeObservers(lifecycleOwner);
                        LiveData<SkillsItem> trading = skillsViewModel.getSkill((byte) 5);
                        trading.observe(lifecycleOwner, skillsItem -> {
                            trading.removeObservers(lifecycleOwner);
                            short basePrice = getBaseItemPrice(ID);
                            inventoryViewModel.add(new InventoryItem(ID, (short) 1, (float) (basePrice + basePrice * (characteristicItem.getValue() * 0.1 + skillsItem.getValue() * 0.005))));
                        });
                    });
                } else
                    Snackbar.make(view, context.getResources().getString(R.string.too_many_items), Snackbar.LENGTH_SHORT).setTextColor(context.getResources().getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
            }

            private boolean isHavingFreeSpace(List<OtherInformationItem> otherInformationItems) {
                return sharedPreferences.getFloat(Using_Weight, 0) + weight <= otherInformationItems.get(2).getValue() ||
                        sharedPreferences.getFloat(Using_Volume, 0) + volume <= otherInformationItems.get(2).getValue();
            }
        });
    }

    public void insertNewItemToSomeoneInventory(short ID, short owner) {
        LiveData<List<OtherInformationItem>> otherInformation = otherInformationViewModel.getAllOtherInformation();
        otherInformation.observe(lifecycleOwner, otherInformationItems -> {
            otherInformation.removeObservers(lifecycleOwner);
                LiveData<CharacteristicItem> attractiveness = characteristicsViewModel.getCharacteristic((byte) 7);
                attractiveness.observe(lifecycleOwner, characteristicItem -> {
                    attractiveness.removeObservers(lifecycleOwner);
                    LiveData<SkillsItem> trading = skillsViewModel.getSkill((byte) 5);
                    trading.observe(lifecycleOwner, skillsItem -> {
                        trading.removeObservers(lifecycleOwner);
                        short basePrice = getBaseItemPrice(ID);
                        inventoryViewModel.add(new InventoryItem(ID, owner, (float) (basePrice - basePrice * (characteristicItem.getValue() * 0.1 + skillsItem.getValue() * 0.005))));
                    });
                });
        });
    }

    public boolean isItemForQuest(short ID) {
        return ID == 2;
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
