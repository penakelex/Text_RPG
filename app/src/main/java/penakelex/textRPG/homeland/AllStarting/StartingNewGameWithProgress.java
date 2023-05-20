package penakelex.textRPG.homeland.AllStarting;

import static penakelex.textRPG.homeland.Main.Constants.Characteristics_Points;
import static penakelex.textRPG.homeland.Main.Constants.First_Visit_Talents;
import static penakelex.textRPG.homeland.Main.Constants.Gender;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.ID_Dialog;
import static penakelex.textRPG.homeland.Main.Constants.Is_Game_Started;
import static penakelex.textRPG.homeland.Main.Constants.Level;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Age;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Height;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;
import static penakelex.textRPG.homeland.Main.Constants.Main_Skills;
import static penakelex.textRPG.homeland.Main.Constants.Talents_Points;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;


import androidx.appcompat.widget.AppCompatTextView;

import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabase;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryDatabase;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.Quest;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.QuestsDao;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.QuestsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.TalentsDatabase.TalentsDatabase;
import penakelex.textRPG.homeland.Databases.TalentsDatabase.TalentsDatabaseHelper;
import penakelex.textRPG.homeland.Dialogs.DialogActivity;
import penakelex.textRPG.homeland.R;


public class StartingNewGameWithProgress extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.starting_new_game_with_progress, null);
        builder.setView(view);
        AppCompatTextView yesButton = view.findViewById(R.id.yesButton), noButton = view.findViewById(R.id.noButton);
        yesButton.setOnClickListener(listener -> startingNewGame());
        noButton.setOnClickListener(listener -> dismiss());
        return builder.create();
    }

    private void startingNewGame() {
        getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).edit().putInt(ID_Dialog, 0).
                putBoolean(Is_Game_Started, true).putBoolean(First_Visit_Talents, true).
                putInt(Main_Character_Age, 0).putInt(Main_Character_Height, 0).
                putString(Main_Character_Name, "").putInt(Gender, 0).
                putInt(Characteristics_Points, 2).putInt(Main_Skills, 3).putInt(Talents_Points, 2).
                putInt(Level, 0).apply();
        SQLiteDatabase characteristicsDatabase = new CharacteristicsDatabaseHelper(getActivity()).getWritableDatabase();
        CharacteristicsDatabase.settingStartValuesInDatabase(characteristicsDatabase,
                new String[]{getActivity().getResources().getString(R.string.strength), getActivity().getResources().getString(R.string.physique), getActivity().getResources().getString(R.string.dexterity), getActivity().getResources().getString(R.string.mentality), getActivity().getResources().getString(R.string.luckiness), getActivity().getResources().getString(R.string.watchfulness), getActivity().getResources().getString(R.string.attractiveness)});
        SkillsDatabase.settingStartingSkillsInDatabase(new SkillsDatabaseHelper(getActivity()).getWritableDatabase(), characteristicsDatabase,
                new String[]{getResources().getString(R.string.lightWeapons), getResources().getString(R.string.heavyWeapons), getResources().getString(R.string.meleeWeapons), getResources().getString(R.string.communication), getResources().getString(R.string.trading), getResources().getString(R.string.survival), getResources().getString(R.string.medicine), getResources().getString(R.string.scince), getResources().getString(R.string.repair)});
        TalentsDatabase.settingStartingValuesInDatabase(new TalentsDatabaseHelper(getActivity()).getWritableDatabase(),
                new String[]{getResources().getString(R.string.singer), getResources().getString(R.string.bull), getResources().getString(R.string.strong_kick), getResources().getString(R.string.experienced), getResources().getString(R.string.trained), getResources().getString(R.string.heavyweight),getResources().getString(R.string.kind_one)});
        InventoryDatabase.getDatabase(getActivity()).inventoryDao().deleteAll();
        QuestsDao dao = QuestsDatabase.getDatabase(getActivity()).questsDao();
        dao.deleteAll();
        dao.addQuest(new Quest(getActivity().getString(R.string.quest_registration)));
        startActivity(new Intent(getActivity(), DialogActivity.class));
        getActivity().finish();
    }
}
