package penakelex.textRPG.homeland.CreatingCharacterForm.StartingFragments;

import static penakelex.textRPG.homeland.Main.Constants.First_Visit_Talents;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Talents_Points;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;

import penakelex.textRPG.homeland.Adapters.StartingTalents.StartingTalentsAdapter;


import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationDatabaseHelper;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabaseHelper;
import penakelex.textRPG.homeland.Databases.TalentsDatabase.TalentsDatabase;
import penakelex.textRPG.homeland.Databases.TalentsDatabase.TalentsDatabaseHelper;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentStartingTalentsBinding;


public class StartingTalentsFragment extends Fragment {
    private FragmentStartingTalentsBinding binding;

    private SharedPreferences sharedPreferences;
    private StartingTalentsAdapter startingTalentsAdapter;
    private int currentPosition = -1;
    private boolean[] isHaving;
    private StartingTalentsAdapter.OnStartingTalentItemClickListener clickListener = new StartingTalentsAdapter.OnStartingTalentItemClickListener() {
        @Override
        public void onClickListener(String name, int position) {
            binding.talentName.setText(name);
            switch (position) {
                case 0:
                    binding.talentDescription.setText(getResources().getString(R.string.description_singer));
                    binding.talentMeaning.setText(getResources().getString(R.string.meaning_singer));
                    break;
                case 1:
                    binding.talentDescription.setText(getResources().getString(R.string.description_bull));
                    binding.talentMeaning.setText(getResources().getString(R.string.meaning_bull));
                    break;
                case 2:
                    binding.talentDescription.setText(getResources().getString(R.string.description_strong_kick));
                    binding.talentMeaning.setText(getResources().getString(R.string.meaning_strong_kick));
                    break;
                case 3:
                    binding.talentDescription.setText(getResources().getString(R.string.description_experienced));
                    binding.talentMeaning.setText(getResources().getString(R.string.meaning_experienced));
                    break;
                case 4:
                    binding.talentDescription.setText(getResources().getString(R.string.description_trained));
                    binding.talentMeaning.setText(getResources().getString(R.string.meaning_trained));
                    break;
                case 5:
                    binding.talentDescription.setText(getResources().getString(R.string.description_heavyweight));
                    binding.talentMeaning.setText(getResources().getString(R.string.meaning_heavyweight));
                    break;
                case 6:
                    binding.talentDescription.setText(getResources().getString(R.string.description_kind_one));
                    binding.talentMeaning.setText(getResources().getString(R.string.meaning_kind_one));
                    break;
            }
            if (isHaving == null) {
                isHaving = TalentsDatabase.getIsHaving(new TalentsDatabaseHelper(getActivity()).getReadableDatabase());
            }
            currentPosition = position;
            if (isHaving[position]) {
                binding.chooseTalent.setText(R.string.unchoose_talent);
            } else {
                binding.chooseTalent.setText(R.string.choose_talent);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStartingTalentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startingTalentsAdapter = new StartingTalentsAdapter(clickListener);
        startingTalentsAdapter.setInformation(getActivity());
        binding.containerForSTalents.setAdapter(startingTalentsAdapter);
        setPointsInformation();
        binding.chooseTalent.setOnClickListener(listener -> choosingTalent());
        binding.buttonToSkills.setOnClickListener(lisetner -> backToSkills());
        binding.buttonToEndForm.setOnClickListener(listener -> endForm());
    }

    @SuppressLint("DefaultLocale")
    private void setPointsInformation() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        binding.talentsPoints.setText(String.format("%s %d", getResources().getString(R.string.rest_of_talents_points), sharedPreferences.getInt(Talents_Points, 2)));
    }

    private void choosingTalent() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (currentPosition != -1) {
            boolean isAllGood = false;
            if (isHaving[currentPosition] || (!isHaving[currentPosition] && sharedPreferences.getInt(Talents_Points, 2) >= 1)){
                switch (currentPosition) {
                    case 0:
                        isAllGood = TalentsDatabase.choosingSinger(new TalentsDatabaseHelper(getActivity()).getWritableDatabase(), new CharacteristicsDatabaseHelper(getActivity()).getWritableDatabase());
                        break;
                    case 1:
                        isAllGood = TalentsDatabase.choosingBull(new TalentsDatabaseHelper(getActivity()).getWritableDatabase(), new CharacteristicsDatabaseHelper(getActivity()).getWritableDatabase(), new OtherInformationDatabaseHelper(getActivity()).getWritableDatabase());
                        break;
                    case 2:
                        isAllGood = TalentsDatabase.choosingStrongKick(new TalentsDatabaseHelper(getActivity()).getWritableDatabase(), new OtherInformationDatabaseHelper(getActivity()).getWritableDatabase());
                        break;
                    case 3:
                        isAllGood = TalentsDatabase.choosingExperienced(new TalentsDatabaseHelper(getActivity()).getWritableDatabase(), new SkillsDatabaseHelper(getActivity()).getWritableDatabase(), new CharacteristicsDatabaseHelper(getActivity()).getWritableDatabase());
                        break;
                    case 4:
                        isAllGood = TalentsDatabase.choosingTrained(new TalentsDatabaseHelper(getActivity()).getWritableDatabase(), new CharacteristicsDatabaseHelper(getActivity()).getWritableDatabase(), new SkillsDatabaseHelper(getActivity()).getWritableDatabase());
                        break;
                    case 5:
                        isAllGood = TalentsDatabase.choosingHeavyweight(new TalentsDatabaseHelper(getActivity()).getWritableDatabase(), new OtherInformationDatabaseHelper(getActivity()).getWritableDatabase());
                        break;
                    case 6:
                        isAllGood = TalentsDatabase.choosingKindOne(new TalentsDatabaseHelper(getActivity()).getWritableDatabase(), new SkillsDatabaseHelper(getActivity()).getWritableDatabase(), new CharacteristicsDatabaseHelper(getActivity()).getWritableDatabase());
                        break;
                }
                if (isHaving[currentPosition]) {
                    if (isAllGood) {
                        isHaving[currentPosition] = false;
                        binding.chooseTalent.setText(R.string.choose_talent);
                        sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) + 1).apply();
                        setNewAdapterInformation();
                    } else {
                        Snackbar.make(binding.getRoot(), getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
                    }
                } else {
                    if (isAllGood) {
                        isHaving[currentPosition] = true;
                        binding.chooseTalent.setText(R.string.unchoose_talent);
                        sharedPreferences.edit().putInt(Talents_Points, sharedPreferences.getInt(Talents_Points, 2) - 1).apply();
                        setNewAdapterInformation();
                    } else {
                        Snackbar.make(binding.getRoot(), getResources().getString(R.string.talent_is_not_used), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
                    }
                }
            } else {
                Snackbar.make(binding.getRoot(), getResources().getString(R.string.not_enough_talents_points), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
            }
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.have_not_chosen_talent), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }

    private void setNewAdapterInformation() {
        startingTalentsAdapter.setInformation(getActivity());
        setPointsInformation();
    }

    private void endForm() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt(Talents_Points, 2) == 0) {
            sharedPreferences.edit().putBoolean(First_Visit_Talents, false).apply();
            new EndingForm().show(getFragmentManager().beginTransaction(), "end form");
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.have_not_chosen_ur_talents), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }

    private void backToSkills() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt(Talents_Points, 2) == 0) {
            sharedPreferences.edit().putBoolean(First_Visit_Talents, false).apply();
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingSkillsFragment()).commit();
        } else {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.have_not_chosen_ur_talents), Snackbar.LENGTH_SHORT).setTextColor(getResources().getColor(R.color.golden_yellow)).setBackgroundTint(getResources().getColor(R.color.dark_purple)).show();
        }
    }
    /*private void endForm() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt(Constants.Talents_Points, 2) == 0) {
            new EndingForm().show(getFragmentManager().beginTransaction(), "end form");
        } else {
            binding.message.setText("Вы не выбрали свои таланты");
        }
    }

    private void backToSkills() {
        sharedPreferences = getActivity().getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt(Constants.Talents_Points, 2) == 0) {
            getActivity().getFragmentManager().beginTransaction().replace(R.id.containerForCreatingCharacter, new StartingSkillsFragment()).commit();
        } else {
            binding.message.setText("Вы не выбрали свои таланты");
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
            binding.message.setText("Талант не полностью раскрыт...");
        } else if (!permission) {
            binding.message.setText("Не хватает очков талантов...");
        } else if (isAllGood && permission) {
            binding.message.setText("");
            updatePointsValue(id, talentsDatabase);
        }
        binding.talentsPoints.setText(String.format("Осталось очков талантов: %d", sharedPreferences.getInt(Constants.Talents_Points, 2)));
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
        switch (binding.talentName.getText().toString()) {
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
                binding.message.setText("Вы не выбрали талант...");
                return 0;
        }
    }

    private void settingKindOneInformation(SQLiteDatabase talentsDatabase) {
        setChosen(7, talentsDatabase);
        binding.chooseTalent.setText(!TalentsDatabase.isHaving(7, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        binding.talentName.setText("Добрый малый");
        binding.meaning.setText("Общение, Медицина, Ремонт, Наука, Бартер: +5, Лёгкое оружие, Тяжёлое оружие, Оружие ближнего боя: -5");
        binding.shortDescription.setText("Вас воспитали быть добрым и порядочным, и Вы были того же мнения.");
    }

    @SuppressLint("SetTextI18n")
    private void settingHeavyweightInformation(SQLiteDatabase talentsDatabase) {
        setChosen(6, talentsDatabase);
        binding.chooseTalent.setText(!TalentsDatabase.isHaving(6, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        binding.talentName.setText("Тяжеловес");
        binding.meaning.setText("Максимальная грузоподъёмность: +20");
        binding.shortDescription.setText("Донести пакеты с магазина - легкотня!");
    }

    @SuppressLint("SetTextI18n")
    private void settingTrainedInformation(SQLiteDatabase talentsDatabase) {
        setChosen(5, talentsDatabase);
        binding.chooseTalent.setText(!TalentsDatabase.isHaving(5, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        binding.talentName.setText("Натренированный");
        binding.meaning.setText("Все характеристики: +1, Все навыки% -10%");
        binding.shortDescription.setText("Самосовершенствование - это Ваше качество.");
    }

    @SuppressLint("SetTextI18n")
    private void settingExperiencedInformation(SQLiteDatabase talentsDatabase) {
        setChosen(4, talentsDatabase);
        binding.chooseTalent.setText(!TalentsDatabase.isHaving(4, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        binding.talentName.setText("Опытный");
        binding.meaning.setText("Все навыки: +10%");
        binding.shortDescription.setText("Вы уже прошли немало испытаний по жизни, что сильно отразилось на Ваших возможностях.");
    }

    private void settingStrongKickInformation(SQLiteDatabase talentsDatabase) {
        setChosen(3, talentsDatabase);
        binding.chooseTalent.setText(!TalentsDatabase.isHaving(3, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        binding.talentName.setText("Сильный удар");
        binding.meaning.setText("Урон оружием ближнего боя: +1, Критические урон: -5%");
        binding.shortDescription.setText("Бедная подушка, зато есть набитый удар.");
    }

    private void settingBullInformation(SQLiteDatabase talentsDatabase) {
        setChosen(2, talentsDatabase);
        binding.chooseTalent.setText(!TalentsDatabase.isHaving(2, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        binding.talentName.setText("Бугай");
        binding.meaning.setText("Сила: +1, Телосложение: +1, Очки действия: -2");
        binding.shortDescription.setText("Как-то так сложилось, что Вы здорово выросли в детстве, так ещё и накачались...");
    }

    private void settingSingerInformation(SQLiteDatabase talentsDatabase) {
        setChosen(1, talentsDatabase);
        binding.chooseTalent.setText(!TalentsDatabase.isHaving(1, talentsDatabase) ? "Выбрать талант" : "Убрать талант");
        binding.talentName.setText("Певец");
        binding.meaning.setText("Привлекательность: +1");
        binding.shortDescription.setText("У Вас от природы красивейший голос, за счёт чего Вы становитесь более привлекательными для окружающих.");
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
        binding.talentsPoints.setText(String.format("Осталось очков талантов: %d", sharedPreferences.getInt(Constants.Talents_Points, 2)));
        settingSingerInformation(talentsDatabase);
    }

    private void setChosen(int ID, SQLiteDatabase talentsDatabase) {
        switch (ID) {
            case 1:
                binding.singer.setTextColor(TalentsDatabase.isHaving(ID, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 2:
                binding.bull.setTextColor(TalentsDatabase.isHaving(ID, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 3:
                binding.strongKick.setTextColor(TalentsDatabase.isHaving(ID, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 4:
                binding.experienced.setTextColor(TalentsDatabase.isHaving(ID, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 5:
                binding.trained.setTextColor(TalentsDatabase.isHaving(ID, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 6:
                binding.heavyweight.setTextColor(TalentsDatabase.isHaving(ID, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 7:
                binding.kindOne.setTextColor(TalentsDatabase.isHaving(ID, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
            case 0:
                binding.singer.setTextColor(TalentsDatabase.isHaving(1, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                binding.bull.setTextColor(TalentsDatabase.isHaving(2, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                binding.strongKick.setTextColor(TalentsDatabase.isHaving(3, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                binding.experienced.setTextColor(TalentsDatabase.isHaving(4, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                binding.trained.setTextColor(TalentsDatabase.isHaving(5, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                binding.heavyweight.setTextColor(TalentsDatabase.isHaving(6, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                binding.kindOne.setTextColor(TalentsDatabase.isHaving(7, talentsDatabase) ? Color.BLUE : Color.parseColor("#474747"));
                break;
        }
    }*/
}