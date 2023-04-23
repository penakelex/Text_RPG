package com.example.textrpg.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.textrpg.databinding.ActivityLocalMapBinding;

public class ActivityLocalMap extends AppCompatActivity {
    private ActivityLocalMapBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocalMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}