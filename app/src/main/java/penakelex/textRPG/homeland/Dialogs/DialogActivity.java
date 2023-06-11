package penakelex.textRPG.homeland.Dialogs;

import static penakelex.textRPG.homeland.Main.Constants.Current_Activity;
import static penakelex.textRPG.homeland.Main.Constants.Experience;
import static penakelex.textRPG.homeland.Main.Constants.Going_To_Starting_Information;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Main.Constants.Trader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;


import java.util.ArrayList;
import java.util.Arrays;

import penakelex.textRPG.homeland.CreatingCharacterForm.CreatingCharacter;
import penakelex.textRPG.homeland.Dialogs.DialogHelper.BooleanSP;
import penakelex.textRPG.homeland.Dialogs.DialogHelper.DialogActivityHelper;
import penakelex.textRPG.homeland.Dialogs.DialogHelper.IntSP;
import penakelex.textRPG.homeland.Dialogs.DialogHelper.ShortItemInformation;
import penakelex.textRPG.homeland.Main.MainActionParentActivity;
import penakelex.textRPG.homeland.Map.Map;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.Trading.TradingActivity;
import penakelex.textRPG.homeland.databinding.ActivityDialogBinding;
import penakelex.textRPG.homeland.databinding.ReplicaButtonBinding;

/**
 * DialogActivity
 * Активность с диалогами
 */

public class DialogActivity extends MainActionParentActivity {
    private final Dialogs dialogs = new Dialogs();
    private ActivityDialogBinding binding;
    private Animation animation;
    private Dialogs.Quote[] quotes;
    private DialogActivityHelper dialogActivityHelper;
    private final int[] array = {0, 0, 0}; //репутация, опыт, деньги
    private final ArrayList<ShortItemInformation> itemsToAdd = new ArrayList<>();
    private final short[] plusStatistics = new short[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        handlingToolBar(toolbar);
        //Анимация для текста
        animation = AnimationUtils.loadAnimation(this, R.anim.text_animation);
        //Помощник активности
        dialogActivityHelper = new DialogActivityHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        sharedPreferences.edit().putInt(Current_Activity, 3).apply();
        //Начало диалога по идентификатору
        initiateDialog(sharedPreferences.getInt(ID_Dialog, 0));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * initiateDialog - процедура
     * Инициализация диалога
     *
     * @param ID - идентификатор диалога
     */
    private void initiateDialog(int ID) {
        quotes = dialogs.getQuotes(ID);
        startQuote(0);
    }

    /**
     * startQuote - процедура
     * Выполнение некоторых действий, либо переход на следующий шаг
     *
     * @param step - следующий шаг (если отрицателен, то выполнение определённых действий,
     *             если положителен, то переход на следующий шаг)
     */
    private void startQuote(int step) {
        switch (step) {
            case -1 -> {
                dialogActivityHelper.putBooleansToSharedPreferences(new BooleanSP(Going_To_Starting_Information, false));
                dialogActivityHelper.putIntsToSharedPreferences(new IntSP(ID_Dialog, 1));
                saveChanges();
                binding = null;
                goingToCreatingCharacter();
            }
            case -2 -> {
                dialogActivityHelper.updateLocationForDialog(4, 2, 24, false);
                saveChanges();
                goingToMap();
                binding = null;
            }
            case -3 -> {
                dialogActivityHelper.putBooleansToSharedPreferences(new BooleanSP(Going_To_Starting_Information, true));
                saveChanges();
                binding = null;
                goingToCreatingCharacter();
            }
            case -4 -> {
                dialogActivityHelper.putBooleansToSharedPreferences(new BooleanSP(Going_To_Starting_Information, true));
                dialogActivityHelper.putIntsToSharedPreferences(new IntSP(Experience, 200));
                saveChanges();
                dialogActivityHelper.updateQuestStage((short) 2, (short) 1);
                binding = null;
                goingToCreatingCharacter();
            }
            case -5 -> {
                dialogActivityHelper.updateQuestStage((short) 2, (short) 1);
                initiateDialog(4);
            }
            case -6 -> {
                dialogActivityHelper.addQuest(R.string.excursion);
                dialogActivityHelper.addReputation(R.string.instructor_serdcev);
                dialogActivityHelper.updateLocationForDialog(5, 1, 20, true);
                saveChanges();
                binding = null;
                goingToMap();
            }
            case -7 -> {
                dialogActivityHelper.updateLocationForDialog(6, 2, 24, true);
                saveChanges();
                binding = null;
                goingToMap();
            }
            case -8 -> {
                dialogActivityHelper.updateLocationForDialog(7, 6, 43, true);
                saveChanges();
                binding = null;
                goingToMap();
            }
            case -9 -> {
                itemsToAdd.add(new ShortItemInformation((short) 1));
                fillReplicas(quotes[3]);
            }
            case -10 -> {
                dialogActivityHelper.updateLocationForDialog(8, 6, 44, true);
                saveChanges();
                binding = null;
                goingToMap();
            }
            case -11 -> {
                saveChanges();
                dialogActivityHelper.updateQuestStage((short) 2, (short) 2);
                binding = null;
                dialogActivityHelper.updateLocationForDialog(9, 6, 44, false);
                goingToMap();
            }
            case -12 -> {
                dialogActivityHelper.addReputation(R.string.andrey, R.string.alena);
                saveChanges();
                binding = null;
                dialogActivityHelper.updateLocationForDialog(10, 2, 24, false);
                goingToMap();
            }
            case -13 -> {
                dialogActivityHelper.updateLocationForDialog(12, 1, 20, false);
                saveChanges();
                initiateDialog(12);
            }
            case -14 -> {
                dialogActivityHelper.updateLocationForDialog(11, 2, 24, false);
                saveChanges();
                initiateDialog(11);
            }
            case -15 -> {
                dialogActivityHelper.putIntsToSharedPreferences(new IntSP(Trader, 2));
                for (int i = 0; i < 10; i++) {
                    itemsToAdd.add(new ShortItemInformation((short) 2, (short) 2));
                }
                dialogActivityHelper.updateLocationForDialog(12, 2, 24, false);
                saveChanges();
                binding = null;
                goingToTrading();
            }
            case -16 -> {
                dialogActivityHelper.updateLocationForDialog(12, 2, 24, false);
                saveChanges();
            }
            case -17 -> {
                dialogActivityHelper.updateLocationForDialog(13, 2, 24, false);
                saveChanges();
                initiateDialog(13);
            }
            case -18 -> {
                dialogActivityHelper.updateLocationForDialog(14, 2, 24, false);
                saveChanges();
                initiateDialog(14);
            }
            case -19 -> {
                dialogActivityHelper.updateLocationForDialog(100, 2, 24, false);
                saveChanges();
                goingToMap();
            }
            default -> fillReplicas(quotes[step]);
        }
    }

    /**
     * goingToTrading - процедура
     * Переход на активность с торговлей
     */
    private void goingToTrading() {
        startActivity(new Intent(this, TradingActivity.class));
        finish();
    }

    /**
     * saveChanges - процедура
     * Сохранение изменений
     */
    private void saveChanges() {
        dialogActivityHelper.saveChanges(array, itemsToAdd, plusStatistics, binding.getRoot(), getApplicationContext());
        Arrays.fill(array, 0);
        itemsToAdd.clear();
        Arrays.fill(plusStatistics, (short) 0);
    }

    /**
     * goingToMap - процедура
     * Переход на активность с картой
     */
    private void goingToMap() {
        startActivity(new Intent(DialogActivity.this, Map.class));
        finish();
    }

    /**
     * goingToCreatingCharacter - процедура
     * Переход на активность с созданием персонажа
     */
    private void goingToCreatingCharacter() {
        startActivity(new Intent(DialogActivity.this, CreatingCharacter.class));
        finish();
    }

    /**
     * fillReplicas - процедура
     * Заполнение текста говорящего персонажа, его изображения, имени, заполнения реплик главного персонажа
     *
     * @param quote - содержит в себе ссылки на всё перечисленное выше
     */
    private void fillReplicas(Dialogs.Quote quote) {
        binding.containerForReplicasVariants.removeAllViews();
        settingTalkingCharacterImage(quote.getImage());
        settingTalkingCharacterName(quote.getName());
        settingTalkingCharacterQuote(quote.getQuote());
        for (Dialogs.Quote.CharacterQuote characterQuote : quote.getCharacterQuotes()) {
            ReplicaButtonBinding buttonBinding = ReplicaButtonBinding.inflate(getLayoutInflater(), binding.containerForReplicasVariants, false);
            buttonBinding.getRoot().setText(dialogActivityHelper.getSettingValueForCharacterQuote(characterQuote.getQuote(), getApplicationContext()));
            buttonBinding.getRoot().setOnClickListener(listener -> replicaListener(characterQuote));
            binding.containerForReplicasVariants.addView(buttonBinding.getRoot());
        }
    }

    /**
     * settingTalkingCharacterQuote - процедура
     * Заполнение текста говорящего персонажа
     *
     * @param quote - фраза говорящего персонажа
     */
    private void settingTalkingCharacterQuote(int quote) {
        binding.text.setText(dialogActivityHelper.getTalkingCharacterQuote(quote, getApplicationContext()));
        binding.text.startAnimation(animation);
    }

    /**
     * replicaListener - процедура
     * "Слушатель" реплики, выбранной игроком
     *
     * @param characterQuote - выбранная реплика игроком
     */
    private void replicaListener(Dialogs.Quote.CharacterQuote characterQuote) {
        startQuote(dialogActivityHelper.replicaListener(characterQuote, array, plusStatistics));
    }

    /**
     * settingTalkingCharacterName - процедура
     * Заполнение имени говорящего персонажа
     *
     * @param name - имя говорящего персонажа
     */
    private void settingTalkingCharacterName(int name) {
        binding.name.setText(name);
    }

    /**
     * settingTalkingCharacterImage - процедура
     * Заполнение изображения говорящего персонажа
     *
     * @param image - ссылка на изображение говорящего персонажа
     */
    private void settingTalkingCharacterImage(int image) {
        binding.imageOfCharacter.setImageDrawable(DialogActivityHelper.getTalkingCharacterImage(image, getApplicationContext()));
    }
}