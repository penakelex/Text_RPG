package penakelex.textRPG.homeland.TopPanel.Person.Fragments;

import static penakelex.textRPG.homeland.Main.Constants.First_Visit_Skills;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Question_Mark;
import static penakelex.textRPG.homeland.Main.Constants.Skill_Points;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabaseHelper;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentSkillsBinding;

public class SkillsFragment extends android.app.Fragment {
    private FragmentSkillsBinding binding;
    private SkillsDatabaseHelper skillsDatabaseHelper;
    private SQLiteDatabase skillsDatabase;
    private SharedPreferences sharedPreferences;
    private int[] skillsValues;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSkillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}