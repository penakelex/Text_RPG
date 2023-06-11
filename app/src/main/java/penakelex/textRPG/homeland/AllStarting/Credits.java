package penakelex.textRPG.homeland.AllStarting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import penakelex.textRPG.homeland.databinding.ActivityCreditsBinding;

/** Credits
 *      Активность, содержащая информацию о приложении
 * */
public class Credits extends AppCompatActivity {
    private ActivityCreditsBinding activityCreditsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreditsBinding = ActivityCreditsBinding.inflate(getLayoutInflater());
        setContentView(activityCreditsBinding.getRoot());
        activityCreditsBinding.exitButton.setOnClickListener(l -> {
            activityCreditsBinding = null;
            startActivity(new Intent(Credits.this, MainMenu.class));
            finish();
        });
    }
}