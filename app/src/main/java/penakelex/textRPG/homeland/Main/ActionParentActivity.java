package penakelex.textRPG.homeland.Main;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.TopPanel.Menu.MenuActivity;
import penakelex.textRPG.homeland.TopPanel.Person.PersonActivity;
import penakelex.textRPG.homeland.TopPanel.Quests.QuestsActivity;

public class ActionParentActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_menu, menu);
        return true;
    }

    public void handlingToolBar(Toolbar toolbar) {
        sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        String name = sharedPreferences.getString(Main_Character_Name, "");
        toolbar.setTitleTextColor(Color.parseColor("#EFDB3D"));
        toolbar.setTitleTextAppearance(this, R.style.gameFont);
        if (name.equals("")) {
            toolbar.setTitle("");
        } else {
            toolbar.setTitle(name);
        }
    }
}
