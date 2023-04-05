package com.example.textrpg.StartingFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.textrpg.R;
import com.example.textrpg.databinding.FragmentStartingTalentsBinding;


public class StartingTalentsFragment extends Fragment {
    private FragmentStartingTalentsBinding startingTalentsBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        startingTalentsBinding = FragmentStartingTalentsBinding.inflate(inflater, container, false);
        return startingTalentsBinding.getRoot();
    }
}