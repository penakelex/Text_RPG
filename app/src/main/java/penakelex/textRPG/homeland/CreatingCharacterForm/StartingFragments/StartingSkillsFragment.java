package penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Main_Skills;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import penakelex.textRPG.homeland.Adapters.StartingSkills.StartingSkillsAdapter;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabaseHelper;


import penakelex.textRPG.homeland.Main.Constants;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentStartingSkillsBinding;


public class StartingSkillsFragment extends Fragment {
    private FragmentStartingSkillsBinding binding;
    private StartingSkillsAdapter startingSkillsAdapter;
    private boolean[] isMains;
    private int currentPosition = -1;
    private SharedPreferences sharedPreferences;
    private StartingSkillsAdapter.OnSkillItemClickListener clickListener = new StartingSkillsAdapter.OnSkillItemClickListener() {
        @Override
        public void onClickListener(String name, int position) {
            binding.skillName.setText(name);
            switch (position) {
                case 0:
                    binding.skillDescription.setText(getResources().getString(R.string.light_weapons_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.light_weapons_base_value));
                    break;
                case 1:
                    binding.skillDescription.setText(getResources().getString(R.string.heavy_weapons_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.heavy_weapons_base_value));
                    break;

                case 2:
                    binding.skillDescription.setText(getResources().getString(R.string.melee_weapons_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.melee_weapons_base_value));
                    break;

                case 3:
                    binding.skillDescription.setText(getResources().getString(R.string.communication_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.communication_base_value));
                    break;

                case 4:
                    binding.skillDescription.setText(getResources().getString(R.string.trading_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.trading_base_value));
                    break;

                case 5:
                    binding.skillDescription.setText(getResources().getString(R.string.survival_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.survival_base_value));
                    break;

                case 6:
                    binding.skillDescription.setText(getResources().getString(R.string.medicine_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.medicine_base_value));
                    break;

                case 7:
                    binding.skillDescription.setText(getResources().getString(R.string.science_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.science_base_value));
                    break;

                case 8:
                    binding.skillDescription.setText(getResources().getString(R.string.repair_description));
                    binding.skillBaseValue.setText(getResources().getString(R.string.repair_base_value));
                    break;
            }
            currentPosition = position;
            if (isMains == null) {
                isMains = SkillsDatabase.getIsMains(new SkillsDatabaseHelper(getActivity()).getReadableDatabase());
            }
            if (isMains[position]) {
                binding.chooseAsMainSkill.setText(getResources().getString(R.string.choose_as_not_main_skill));
            } else {
                binding.chooseAsMainSkill.setText(getResources().getString(R.string.choose_as_main_skill));
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartingSkillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startingSkillsAdapter = new StartingSkillsAdapter(clickListener);
        startingSkillsAdapter.setInformation(getActivity());
        binding.containerForSSkills.setAdapter(startingSkillsAdapter);
        setPointsInformation();
        binding.chooseAsMainSkill.setOnClickListener(l -> choosingAsMainSkill());
        binding.buttonToCharacteristics.setOnClickListener(l -> settingCharacteristicsFragment());
        binding.buttonToTalents.setOnClickListener(l -> settingAbilitiesFragment());
    }

    private void choosingAsMainSkill() {
        SQLiteDatabase skillsDatabase = new SkillsDatabaseHelper(getActivity()).getWritableDatabase();
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (currentPosition != -1) {
            if (isMains[currentPosition]) {
                SkillsDatabase.setSkillNotIsMain(skillsDatabase, currentPosition + 1);
                sharedPreferences.edit().putInt(Main_Skills, sharedPreferences.getInt(Main_Skills, 3) + 1).apply();
                binding.chooseAsMainSkill.setText(getResources().getString(R.string.choose_as_main_skill));
                isMains[currentPosition] = false;
                setNewInformation();
            } else if (sharedPreferences.getInt(Main_Skills, 3) >= 1) {
                SkillsDatabase.setSkillIsMain(skillsDatabase, currentPosition + 1);
                sharedPreferences.edit().putInt(Main_Skills, sharedPreferences.getInt(Main_Skills, 3) - 1).apply();
                binding.chooseAsMainSkill.setText(getResources().getString(R.string.choose_as_not_main_skill));
                isMains[currentPosition] = true;
                setNewInformation();
            } else {
                Snackbar.make(binding.getRoot(), getResources().getString(R.string.not_enough_main_skills_points), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
            }
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.did_not_choosen_starting_skill), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }

    private void setNewInformation() {
        startingSkillsAdapter.setInformation(getActivity());
        setPointsInformation();
    }

    @SuppressLint("DefaultLocale")
    private void setPointsInformation() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        binding.skillsPoints.setText(String.format("%s %d", getResources().getString(R.string.rest_of_main_skills_points), sharedPreferences.getInt(Main_Skills, 3)));
    }

    private void settingCharacteristicsFragment() {
        if (getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).getInt(Constants.Main_Skills, 3) == 0) {
            binding = null;
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingCharacteristicsFragment()).commit();
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.have_not_used_all_main_skills_points_yet), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }

    private void settingAbilitiesFragment() {
        if (getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).getInt(Constants.Main_Skills, 3) == 0) {
            binding = null;
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingTalentsFragment()).commit();
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.have_not_used_all_main_skills_points_yet), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }
    /*private void settingCharacteristicsFragment() {
        if (getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).getInt(Constants.Main_Skills, 3) == 0) {
            binding = null;
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingCharacteristicsFragment()).commit();
        } else {
            binding.message.setText("Вы ещё не определили все основные навыки...");
        }
    }

    private void settingAbilitiesFragment() {
        if (getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE).getInt(Constants.Main_Skills, 3) == 0) {
            binding = null;
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingTalentsFragment()).commit();
        } else {
            binding.message.setText("Вы ещё не определили все основные навыки...");
        }
    }

    @SuppressLint("DefaultLocale")
    private void choosingAsMainSkill(SQLiteDatabase database) {
        binding.message.setText("");
        int id = getIdByText(), value = SkillsDatabase.getValueSkill(database, id);
        boolean isMain = SkillsDatabase.getIsMainSkill(database, id);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (id != 0) {
            int mainSkills = sharedPreferences.getInt(Constants.Main_Skills, 3);
            boolean isMainSkillChosen;
            if (isMain) {
                if (mainSkills + 1 <= 3) {
                    SkillsDatabase.setSkillNotIsMain(database, id);
                    SkillsDatabase.setSKillValueNotMain(database, id, value);
                    sharedPreferences.edit().putInt(Constants.Main_Skills, sharedPreferences.getInt(Constants.Main_Skills, 3) + 1).apply();
                    binding.mainSkillsPoints.setText(String.format("Осталось очков основных навыков: %d", sharedPreferences.getInt(Constants.Main_Skills, 3)));
                    isMainSkillChosen = true;
                } else {
                    isMainSkillChosen = false;
                    binding.message.setText("Вы ещё не выбрали ни один основной навык");
                }
            } else {
                if (mainSkills - 1 >= 0) {
                    SkillsDatabase.setSkillIsMain(database, id);
                    SkillsDatabase.setSkillValueIsMain(database, id, value);
                    sharedPreferences.edit().putInt(Constants.Main_Skills, mainSkills - 1).apply();
                    binding.mainSkillsPoints.setText(String.format("Осталось очков основных навыков: %d", sharedPreferences.getInt(Constants.Main_Skills, 3)));
                    isMainSkillChosen = true;
                } else {
                    isMainSkillChosen = false;
                    binding.message.setText("Вы уже выбрали максимальное количество основных навыков");
                }
            }
            if (isMainSkillChosen) {
                setNewValue(id, SkillsDatabase.getValueSkill(database, id), database);
                if (SkillsDatabase.getIsMainSkill(database, id)) {
                    binding.chooseAsMainSkill.setText("Выбрать как неосновной навык");
                } else {
                    binding.chooseAsMainSkill.setText("Выбрать как основной навык");
                }
            }

        }
    }

    private void setNewValue(int ID, int newValue, SQLiteDatabase skillDatabase) {
        switch (ID) {
            case 1:
                binding.lightWeaponsValue.setText(String.valueOf(newValue));
                binding.lightWeaponsValue.setTextColor(SkillsDatabase.getIsMainSkill(skillDatabase, ID) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 2:
                binding.heavyWeaponsValue.setText(String.valueOf(newValue));
                binding.heavyWeaponsValue.setTextColor(SkillsDatabase.getIsMainSkill(skillDatabase, ID) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 3:
                binding.meleeWeaponsValue.setText(String.valueOf(newValue));
                binding.meleeWeaponsValue.setTextColor(SkillsDatabase.getIsMainSkill(skillDatabase, ID) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 4:
                binding.communicationValue.setText(String.valueOf(newValue));
                binding.communicationValue.setTextColor(SkillsDatabase.getIsMainSkill(skillDatabase, ID) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 5:
                binding.tradingValue.setText(String.valueOf(newValue));
                binding.tradingValue.setTextColor(SkillsDatabase.getIsMainSkill(skillDatabase, ID) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 6:
                binding.survivalValue.setText(String.valueOf(newValue));
                binding.survivalValue.setTextColor(SkillsDatabase.getIsMainSkill(skillDatabase, ID) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 7:
                binding.medicineValue.setText(String.valueOf(newValue));
                binding.medicineValue.setTextColor(SkillsDatabase.getIsMainSkill(skillDatabase, ID) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 8:
                binding.scinceValue.setText(String.valueOf(newValue));
                binding.scinceValue.setTextColor(SkillsDatabase.getIsMainSkill(skillDatabase, ID) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 9:
                binding.repairValue.setText(String.valueOf(newValue));
                binding.repairValue.setTextColor(SkillsDatabase.getIsMainSkill(skillDatabase, ID) ? Color.BLUE : Color.parseColor("#474747"));
                break;
        }
    }

    private int getIdByText() {
        int id = 0;
        switch (binding.skillName.getText().toString()) {
            case "Лёгкое оружие":
                id = 1;
                break;
            case "Тяжёлое оружие":
                id = 2;
                break;
            case "Оружие ближнего боя":
                id = 3;
                break;
            case "Общение":
                id = 4;
                break;
            case "Торговля":
                id = 5;
                break;
            case "Выживание":
                id = 6;
                break;
            case "Медицина":
                id = 7;
                break;
            case "Наука":
                id = 8;
                break;
            case "Ремонт":
                id = 9;
                break;
            default:
                binding.message.setText("Вы не выбрали навык...");
                break;
        }
        return id;
    }

    @SuppressLint({"Range", "DefaultLocale"})
    private void settingSkillsInDatabase(SQLiteDatabase database, SQLiteDatabase characteristicDatabase) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(Constants.First_Visit_Skills, true)) {
            sharedPreferences.edit().putBoolean(Constants.First_Visit_Skills, false).putInt(Constants.Main_Skills, 3).apply();
            SkillsDatabase.settingStartingSkillsInDatabase(database, characteristicDatabase);
        } else {
            SkillsDatabase.settingNotStartingSkillsInDatabase(database, characteristicDatabase, new TalentsDatabaseHelper(getActivity()).getReadableDatabase());
        }
        binding.mainSkillsPoints.setText(String.format("Осталось очков основных навыков: %d",
                sharedPreferences.getInt(Constants.Main_Skills, 3)));
        int[] values = SkillsDatabase.getSkillValues(database);
        boolean[] isMains = SkillsDatabase.getIsMains(database);
        Log.d("isMains", Arrays.toString(isMains));
        binding.lightWeaponsValue.setText(String.valueOf(values[0]));
        if (isMains[0]) binding.lightWeaponsValue.setTextColor(Color.BLUE);
        binding.heavyWeaponsValue.setText(String.valueOf(values[1]));
        if (isMains[1]) binding.heavyWeaponsValue.setTextColor(Color.BLUE);
        binding.meleeWeaponsValue.setText(String.valueOf(values[2]));
        if (isMains[2]) binding.meleeWeaponsValue.setTextColor(Color.BLUE);
        binding.communicationValue.setText(String.valueOf(values[3]));
        if (isMains[3]) binding.communicationValue.setTextColor(Color.BLUE);
        binding.tradingValue.setText(String.valueOf(values[4]));
        if (isMains[4]) binding.tradingValue.setTextColor(Color.BLUE);
        binding.survivalValue.setText(String.valueOf(values[5]));
        if (isMains[5]) binding.survivalValue.setTextColor(Color.BLUE);
        binding.medicineValue.setText(String.valueOf(values[6]));
        if (isMains[6]) binding.medicineValue.setTextColor(Color.BLUE);
        binding.scinceValue.setText(String.valueOf(values[7]));
        if (isMains[7]) binding.scinceValue.setTextColor(Color.BLUE);
        binding.repairValue.setText(String.valueOf(values[8]));
        if (isMains[8]) binding.repairValue.setTextColor(Color.BLUE);
        settingLightWeaponsInformation(database);
    }

    @SuppressLint("SetTextI18n")
    private void settingRepairInformation(SQLiteDatabase database) {
        if (isNotAlreadyThatSkill(9)) {
            binding.message.setText("");
            binding.chooseAsMainSkill.setText(SkillsDatabase.getIsMainSkill(database, 9) ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
            binding.skillName.setText("Ремонт");
            binding.baseValue.setText("Базовое значение: Ум × 2 + Наблюдательность + Сноровка + 10.");
            binding.descriptionSkills.setText("Часть науки не с теоретической части.");
        } else {
            binding.message.setText("Вы уже выбрали этот навык...");
        }
    }

    @SuppressLint("SetTextI18n")
    private void settingScienceInformation(SQLiteDatabase database) {
        if (isNotAlreadyThatSkill(8)) {
            binding.message.setText("");
            binding.chooseAsMainSkill.setText(SkillsDatabase.getIsMainSkill(database, 8) ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
            binding.skillName.setText("Наука");
            binding.baseValue.setText("Базовое значение: Ум × 2 + Наблюдательность + 10.");
            binding.descriptionSkills.setText("Сочетание из многих наук: математики, физики, химии, биологии и многих других.");
        } else {
            binding.message.setText("Вы уже выбрали этот навык...");
        }
    }

    @SuppressLint("SetTextI18n")
    private void settingMedicineInformation(SQLiteDatabase database) {
        if (isNotAlreadyThatSkill(7)) {
            binding.message.setText("");
            binding.chooseAsMainSkill.setText(SkillsDatabase.getIsMainSkill(database, 7) ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
            binding.skillName.setText("Медицина");
            binding.baseValue.setText("Базовое значение: Ум × 2 + Наблюдательность + 10.");
            binding.descriptionSkills.setText("Общие и углублённые знания лечения.");
        } else {
            binding.message.setText("Вы уже выбрали этот навык...");
        }
    }

    @SuppressLint("SetTextI18n")
    private void settingSurvivalInformation(SQLiteDatabase database) {
        if (isNotAlreadyThatSkill(6)) {
            binding.message.setText("");
            binding.chooseAsMainSkill.setText(SkillsDatabase.getIsMainSkill(database, 6) ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
            binding.skillName.setText("Выживание");
            binding.baseValue.setText("Базовое значение: Телосложение × 2 + Сноровка + Ум + 10.");
            binding.descriptionSkills.setText("Практические знания правил жизни в космосе и на других планетах, сложившиеся из теоретических.");
        } else {
            binding.message.setText("Вы уже выбрали этот навык...");
        }
    }

    @SuppressLint("SetTextI18n")
    private void settingTradingInformation(SQLiteDatabase database) {
        if (isNotAlreadyThatSkill(5)) {
            binding.message.setText("");
            binding.chooseAsMainSkill.setText(SkillsDatabase.getIsMainSkill(database, 5) ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
            binding.skillName.setText("Торговля");
            binding.baseValue.setText("Базовое значение: Привлекательность × 3 + Наблюдательность + 10.");
            binding.descriptionSkills.setText("Исскуство сделать для себя сделку, покупку, продажу, бартер выгоднее.");
        } else {
            binding.message.setText("Вы уже выбрали этот навык...");
        }
    }

    @SuppressLint("SetTextI18n")
    private void settingCommunicationInformation(SQLiteDatabase database) {
        if (isNotAlreadyThatSkill(4)) {
            binding.message.setText("");
            binding.chooseAsMainSkill.setText(SkillsDatabase.getIsMainSkill(database, 4) ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
            binding.skillName.setText("Общение");
            binding.baseValue.setText("Базовое значение: Привлекательность × 3 + Удачливость + Ум + 10.");
            binding.descriptionSkills.setText("Отображает Ваше мастерство в ораторском искусстве: убеждении других.");
        } else {
            binding.message.setText("Вы уже выбрали этот навык...");
        }
    }

    @SuppressLint("SetTextI18n")
    private void settingMeleeWeaponsInformation(SQLiteDatabase database) {
        if (isNotAlreadyThatSkill(3)) {
            binding.message.setText("");
            binding.chooseAsMainSkill.setText(SkillsDatabase.getIsMainSkill(database, 3) ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
            binding.skillName.setText("Оружие ближнего боя");
            binding.baseValue.setText("Базовое значение: Наблюдательность + Удачливость + Телосложение + Сила × 2 + 10.");
            binding.descriptionSkills.setText("Ближний бой с оружием и без, что говорит о Вашем умении использовать Ваши руки и ноги не только в мирных целях.");
        } else {
            binding.message.setText("Вы уже выбрали этот навык...");
        }
    }

    @SuppressLint("SetTextI18n")
    private void settingHeavyWeaponsInformation(SQLiteDatabase database) {
        if (isNotAlreadyThatSkill(2)) {
            binding.message.setText("");
            binding.chooseAsMainSkill.setText(SkillsDatabase.getIsMainSkill(database, 2) ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
            binding.skillName.setText("Тяжёлое оружие");
            binding.baseValue.setText("Базовое значение: Сила × 2 + Наблюдательность + Удачливость + 10.");
            binding.descriptionSkills.setText("Ваше умение управления на самом деле нелёгких орудий.");
        } else {
            binding.message.setText("Вы уже выбрали этот навык...");
        }
    }

    @SuppressLint("SetTextI18n")
    private void settingLightWeaponsInformation(SQLiteDatabase database) {
        if (isNotAlreadyThatSkill(1)) {
            binding.message.setText("");
            binding.chooseAsMainSkill.setText(SkillsDatabase.getIsMainSkill(database, 1) ? "Выбрать как неосновной навык" : "Выбрать как основной навык");
            binding.skillName.setText("Лёгкое оружие");
            binding.baseValue.setText("Базовое значение: Наблюдательность × 2 + Удачливость + Телосложение + 10.");
            binding.descriptionSkills.setText("Отображает Ваше умение пользоваться лёгким оружием.");
        } else {
            binding.message.setText("Вы уже выбрали этот навык...");
        }
    }

    private boolean isNotAlreadyThatSkill(int ID) {
        return ID != getIdByText();
    }*/
}