package penakelex.textRPG.homeland.Main;

import static penakelex.textRPG.homeland.Main.Constants.First_Visit_Talents;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import penakelex.textRPG.homeland.AllStarting.MainMenu;
import penakelex.textRPG.homeland.CreatingCharacterForm.CreatingCharacter;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.TopPanel.Menu.MenuActivity;
import penakelex.textRPG.homeland.TopPanel.Person.PersonActivity;
import penakelex.textRPG.homeland.TopPanel.Quests.QuestsActivity;

public class MainActionParentActivity extends ActionParentActivity {
    private SharedPreferences sharedPreferences;

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolBar);
        switch (item.getItemId()) {
            case R.id.personToolBar:
                if (sharedPreferences.getBoolean(First_Visit_Talents, true)) {
                    toolbar.setTitle(getResources().getString(R.string.you8));
                } else {
                    startActivity(new Intent(this, PersonActivity.class));
                }
                break;
            case R.id.questsToolBar:
                if (sharedPreferences.getBoolean(First_Visit_Talents, true)) {
                    toolbar.setTitle(getResources().getString(R.string.notes8));
                } else {
                    startActivity(new Intent(this, QuestsActivity.class));
                }
                break;
            case R.id.menuToolBar:
                startActivity(new Intent(this, MenuActivity.class));
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
    }
}
