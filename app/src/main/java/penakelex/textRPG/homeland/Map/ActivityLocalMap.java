package penakelex.textRPG.homeland.Map;

import static penakelex.textRPG.homeland.Main.Constants.Current_Activity;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import penakelex.textRPG.homeland.databinding.ActivityLocalMapBinding;

public class ActivityLocalMap extends AppCompatActivity {
    private ActivityLocalMapBinding binding;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocalMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        sharedPreferences.edit().putInt(Current_Activity, 4).apply();
    }
}