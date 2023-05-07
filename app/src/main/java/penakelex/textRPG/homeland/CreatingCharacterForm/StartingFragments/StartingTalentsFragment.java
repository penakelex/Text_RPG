package penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationDatabase;
import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationDatabaseHelper;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.TalentsDatabase.TalentsDatabase;
import penakelex.textRPG.homeland.Databases.TalentsDatabase.TalentsDatabaseHelper;


import penakelex.textRPG.homeland.Main.Constants;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentStartingTalentsBinding;


public class StartingTalentsFragment extends Fragment {
    private FragmentStartingTalentsBinding startingTalentsBinding;
    private TalentsDatabaseHelper talentsDatabaseHelper;
    private SkillsDatabaseHelper skillsDatabaseHelper;
    private CharacteristicsDatabaseHelper characteristicsDatabaseHelper;
    private OtherInformationDatabaseHelper infoDatabaseHelper;
    private SharedPreferences sharedPreferences;
    public static boolean endForm = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        startingTalentsBinding = FragmentStartingTalentsBinding.inflate(inflater, container, false);
        return startingTalentsBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        talentsDatabaseHelper = new TalentsDatabaseHelper(getActivity());
        skillsDatabaseHelper = new SkillsDatabaseHelper(getActivity());
        characteristicsDatabaseHelper = new CharacteristicsDatabaseHelper(getActivity());
        infoDatabaseHelper = new OtherInformationDatabaseHelper(getActivity());
        SQLiteDatabase talentsDatabase = talentsDatabaseHelper.getWritableDatabase(),
                skillsDatabase = skillsDatabaseHelper.getWritableDatabase(),
                characteristicDatabase = characteristicsDatabaseHelper.getWritableDatabase(),
                infoDatabase = infoDatabaseHelper.getWritableDatabase();
        settingStartingValues(talentsDatabase, infoDatabase, characteristicDatabase, skillsDatabase);
        startingTalentsBinding.singer.setOnClickListener(listener -> settingSingerInformation(talentsDatabase));
        startingTalentsBinding.bull.setOnClickListener(listener -> settingBullInformation(talentsDatabase));
        startingTalentsBinding.strongKick.setOnClickListener(listener -> settingStrongKickInformation(talentsDatabase));
        startingTalentsBinding.experienced.setOnClickListener(listener -> settingExperiencedInformation(talentsDatabase));
        startingTalentsBinding.trained.setOnClickListener(listener -> settingTrainedInformation(talentsDatabase));
        startingTalentsBinding.heavyweight.setOnClickListener(listener -> settingHeavyweightInformation(talentsDatabase));
        startingTalentsBinding.kindOne.setOnClickListener(listener -> settingKindOneInformation(talentsDatabase));
        startingTalentsBinding.chooseTalent.setOnClickListener(listener -> choosingTalent(talentsDatabase, skillsDatabase, characteristicDatabase, infoDatabase));
        startingTalentsBinding.buttonToSkills.setOnClickListener(listener -> backToSkills());
        startingTalentsBinding.buttonToEndForm.setOnClickListener(listener -> endForm());
    }

    private void endForm() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt(Constants.Talents_Points, 2) == 0) {
            new AlertWindow().show(getFragmentManager().beginTransaction(), "end form");
        } else {
            startingTalentsBinding.message.setText("Вы не выбрали свои таланты");
        }
    }

    private void backToSkills() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt(Constants.Talents_Points, 2) == 0) {
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingSkillsFragment()).commit();
        } else {
            startingTalentsBinding.message.setText("Вы не выбрали свои таланты");
        }
    }

    @SuppressLint("DefaultLocale")
    private void choosingTalent(SQLiteDatabase talentsDatabase, SQLiteDatabase skillsDatabase, SQLiteDatabase characteristicDatabase, SQLiteDatabase infoDatabase) {
        int id = getIdByText();
        boolean isAllGood = false, permission = permissionToUpdate(id, talentsDatabase);
        if (permissionToUpdate(id, talentsDatabase)) {
            switch (id) {
                case 1:
                    isAllGood = TalentsDatabase.choosingSinger(talentsDatabase, characteristicDatabase);
                    break;
                case 2:
                    isAllGood = TalentsDatabase.choosingBull(talentsDatabase, characteristicDatabase, infoDatabase);
                    break;
                case 3:
                    isAllGood = TalentsDatabase.choosingStrongKick(talentsDatabase, infoDatabase);
                    break;
                case 4:
                    isAllGood = TalentsDatabase.choosingExperienced(talentsDatabase, skillsDatabase, characteristicDatabase);
                    break;
                case 5:
                    isAllGood = TalentsDatabase.choosingTrained(talentsDatabase, characteristicDatabase, skillsDatabase);
                    break;
                case 6:
                    isAllGood = TalentsDatabase.choosingHeavyweight(talentsDatabase, infoDatabase);
                    break;
                case 7:
                    isAllGood = TalentsDatabase.choosingKindOne(talentsDatabase, skillsDatabase, characteristicDatabase);
                    break;
            }
        }
        if (!isAllGood && permission) {
            startingTalentsBinding.message.setText("Талант не полностью раскрыт...");
        } else if (!permission) {
            startingTalentsBinding.message.setText("Не хватает очков талантов...");
        } else if (isAllGood && permission) {
            startingTalentsBinding.message.setText("");
            updatePointsValue(id, talentsDatabase);
        }
        startingTalentsBinding.talentsPoints.setText(String.format("Осталось очков талантов: %d", sharedPreferences.getInt(Constants.Talents_Points, 2)));
        setTextById(id, talentsDatabase);
    }

    private void updatePointsValue(int ID, SQLiteDatabase talentsDatabase) {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (TalentsDatabase.isHaving(ID, talentsDatabase)) {
            sharedPreferences.edit().putInt(Constants.Talents_Points, sharedPreferences.getInt(Constants.Talents_Points, 2) - 1).apply();
        } else {
            sharedPreferences.edit().putInt(Constants.Talents_Points, sharedPreferences.getInt(Constants.Talents_Points, 2) + 1).apply();
        }
    }

    @SuppressLint("DefaultLocale")
    private boolean permissionToUpdate(int ID, SQLiteDatabase talentsDatabase) {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        boolean isHaving = TalentsDatabase.isHaving(ID, talentsDatabase);
        return isHaving && sharedPreferences.getInt(Constants.Talents_Points, 2) + 1 <= 2 || !isHaving && sharedPreferences.getInt(Constants.Talents_Points, 2) - 1 >= 0;
    }

    private void setTextById(int ID, SQLiteDatabase talentsDatabase) {
        switch (ID) {
            case 1:
                settingSingerInformation(talentsDatabase);
                break;
            case 2:
                settingBullInformation(talentsDatabase);
                break;
            case 3:
                settingStrongKickInformation(talentsDatabase);
                break;
            case 4:
                settingExperiencedInformation(talentsDatabase);
                break;
            case 5:
                settingTrainedInformation(talentsDatabase);
                break;
            case 6:
                settingHeavyweightInformation(talentsDatabase);
                break;
            case 7:
                settingKindOneInformation(talentsDatabase);
                break;
        }
    }

    private int getIdByText() {
        switch (startingTalentsBinding.talentName.getText().toString()) {
            case "Певец":
                return 1;
            case "Бугай":
                return 2;
            case "Сильный удар":
                return 3;
            case "Опытный":
                return 4;
            case "Натренированный":
                return 5;
            case "Тяжеловес":
                return 6;
            case "Добрый малый":
                return 7;
            default:
                startingTalentsBinding.message.setText("Вы не выбрали талант...");
                return 0;
        }
    }

    private void settingKindOneInformation(SQLiteDatabase talentsDatabase) {
        setChosen(7, talentsDatabase);
        startingTalentsBinding.chooseTalent.setText(!TalentsDatabase.isHaving(7, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        startingTalentsBinding.talentName.setText("Добрый малый");
        startingTalentsBinding.meaning.setText("Общение, Медицина, Ремонт, Наука, Бартер: +5, Лёгкое оружие, Тяжёлое оружие, Оружие ближнего боя: -5");
        startingTalentsBinding.shortDescription.setText("Вас воспитали быть добрым и порядочным, и Вы были того же мнения.");
    }

    @SuppressLint("SetTextI18n")
    private void settingHeavyweightInformation(SQLiteDatabase talentsDatabase) {
        setChosen(6, talentsDatabase);
        startingTalentsBinding.chooseTalent.setText(!TalentsDatabase.isHaving(6, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        startingTalentsBinding.talentName.setText("Тяжеловес");
        startingTalentsBinding.meaning.setText("Максимальная грузоподъёмность: +20");
        startingTalentsBinding.shortDescription.setText("Донести пакеты с магазина - легкотня!");
    }

    @SuppressLint("SetTextI18n")
    private void settingTrainedInformation(SQLiteDatabase talentsDatabase) {
        setChosen(5, talentsDatabase);
        startingTalentsBinding.chooseTalent.setText(!TalentsDatabase.isHaving(5, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        startingTalentsBinding.talentName.setText("Натренированный");
        startingTalentsBinding.meaning.setText("Все характеристики: +1, Все навыки% -10%");
        startingTalentsBinding.shortDescription.setText("Самосовершенствование - это Ваше качество.");
    }

    @SuppressLint("SetTextI18n")
    private void settingExperiencedInformation(SQLiteDatabase talentsDatabase) {
        setChosen(4, talentsDatabase);
        startingTalentsBinding.chooseTalent.setText(!TalentsDatabase.isHaving(4, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        startingTalentsBinding.talentName.setText("Опытный");
        startingTalentsBinding.meaning.setText("Все навыки: +10%");
        startingTalentsBinding.shortDescription.setText("Вы уже прошли немало испытаний по жизни, что сильно отразилось на Ваших возможностях.");
    }

    private void settingStrongKickInformation(SQLiteDatabase talentsDatabase) {
        setChosen(3, talentsDatabase);
        startingTalentsBinding.chooseTalent.setText(!TalentsDatabase.isHaving(3, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        startingTalentsBinding.talentName.setText("Сильный удар");
        startingTalentsBinding.meaning.setText("Урон оружием ближнего боя: +1, Критические урон: -5%");
        startingTalentsBinding.shortDescription.setText("Бедная подушка, зато есть набитый удар.");
    }

    private void settingBullInformation(SQLiteDatabase talentsDatabase) {
        setChosen(2, talentsDatabase);
        startingTalentsBinding.chooseTalent.setText(!TalentsDatabase.isHaving(2, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        startingTalentsBinding.talentName.setText("Бугай");
        startingTalentsBinding.meaning.setText("Сила: +1, Телосложение: +1, Очки действия: -2");
        startingTalentsBinding.shortDescription.setText("Как-то так сложилось, что Вы здорово выросли в детстве, так ещё и накачались...");
    }

    private void settingSingerInformation(SQLiteDatabase talentsDatabase) {
        setChosen(1, talentsDatabase);
        startingTalentsBinding.chooseTalent.setText(!TalentsDatabase.isHaving(1, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        startingTalentsBinding.talentName.setText("Певец");
        startingTalentsBinding.meaning.setText("Привлекательность: +1");
        startingTalentsBinding.shortDescription.setText("У Вас от природы красивейший голос, за счёт чего Вы становитесь более привлекательными для окружающих.");
    }


    @SuppressLint("DefaultLocale")
    private void settingStartingValues(SQLiteDatabase talentsDatabase, SQLiteDatabase infoDatabase, SQLiteDatabase characteristicsDatabase, SQLiteDatabase skillsDatabase) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constants.First_Visit_Talents, true)) {
            sharedPreferences.edit().putBoolean(Constants.First_Visit_Talents, false).putInt(Constants.Talents_Points, 2).apply();
            TalentsDatabase.settingStartingValuesInDatabase(talentsDatabase);
            OtherInformationDatabase.settingStartingValuesInInformationDatabase(infoDatabase, characteristicsDatabase, skillsDatabase);
        } else {
            setChosen(0, talentsDatabase);
        }
        startingTalentsBinding.talentsPoints.setText(String.format("Осталось очков талантов: %d", sharedPreferences.getInt(Constants.Talents_Points, 2)));
        settingSingerInformation(talentsDatabase);
    }

    private void setChosen(int ID, SQLiteDatabase talentsDatabase) {
        switch (ID) {
            case 1:
                startingTalentsBinding.singer.setTextColor(TalentsDatabase.isHaving(ID, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 2:
                startingTalentsBinding.bull.setTextColor(TalentsDatabase.isHaving(ID, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 3:
                startingTalentsBinding.strongKick.setTextColor(TalentsDatabase.isHaving(ID, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 4:
                startingTalentsBinding.experienced.setTextColor(TalentsDatabase.isHaving(ID, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 5:
                startingTalentsBinding.trained.setTextColor(TalentsDatabase.isHaving(ID, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 6:
                startingTalentsBinding.heavyweight.setTextColor(TalentsDatabase.isHaving(ID, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 7:
                startingTalentsBinding.kindOne.setTextColor(TalentsDatabase.isHaving(ID, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 0:
                startingTalentsBinding.singer.setTextColor(TalentsDatabase.isHaving(1, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                startingTalentsBinding.bull.setTextColor(TalentsDatabase.isHaving(2, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                startingTalentsBinding.strongKick.setTextColor(TalentsDatabase.isHaving(3, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                startingTalentsBinding.experienced.setTextColor(TalentsDatabase.isHaving(4, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                startingTalentsBinding.trained.setTextColor(TalentsDatabase.isHaving(5, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                startingTalentsBinding.heavyweight.setTextColor(TalentsDatabase.isHaving(6, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                startingTalentsBinding.kindOne.setTextColor(TalentsDatabase.isHaving(7, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
        }
    }
}