package penakelex.textRPG.homeland.Main;

import static penakelex.textRPG.homeland.Main.Constants.Current_Top_Panel_Activity;
import static penakelex.textRPG.homeland.Main.Constants.First_Visit_Talents;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.TopPanel.Menu.MenuActivity;
import penakelex.textRPG.homeland.TopPanel.Person.PersonActivity;
import penakelex.textRPG.homeland.TopPanel.Quests.QuestsActivity;

/** TopPanelParentActivity
 *      Родительский класс-активность для активностей верхней панели
 * */
public class TopPanelParentActivity extends ActionParentActivity {
    private SharedPreferences sharedPreferences;

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolBar);
        switch (item.getItemId()) {
            //Установка активности с информацией о персонаже, если она не текущая
            case R.id.personToolBar:
                if (sharedPreferences.getBoolean(First_Visit_Talents, true)) {
                    toolbar.setTitle(getResources().getString(R.string.you8));
                } else {
                    if (sharedPreferences.getInt(Current_Top_Panel_Activity, 0) != 1) {
                        startActivity(new Intent(this, PersonActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.you_already_here), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                //Установка активности с заданиями, если она не текущая
            case R.id.questsToolBar:
                if (sharedPreferences.getBoolean(First_Visit_Talents, true)) {
                    toolbar.setTitle(getResources().getString(R.string.notes8));
                } else {
                    if (sharedPreferences.getInt(Current_Top_Panel_Activity, 0) != 2) {
                        startActivity(new Intent(this, QuestsActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.you_already_here), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                //Установка активности с внутриигровым меню
            case R.id.menuToolBar:
                if (sharedPreferences.getInt(Current_Top_Panel_Activity, 0) != 3) {
                    startActivity(new Intent(this, MenuActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, getResources().getString(R.string.you_already_here), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return false;
    }

    /** handlingToolBar - процедура
     *      Усправление виджетом
     * */
    @Override
    public void handlingToolBar(Toolbar toolbar) {
        sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        toolbar.setTitleTextColor(getResources().getColor(R.color.golden_yellow));
        toolbar.setTitleTextAppearance(this, R.style.gameFont);
        switch (sharedPreferences.getInt(Current_Top_Panel_Activity, 0)) {
            case 1 -> toolbar.setTitle(getResources().getString(R.string.you));
            case 2 -> toolbar.setTitle(getResources().getString(R.string.notes));
            case 3 -> toolbar.setTitle(getResources().getString(R.string.menu));
        }
    }

    /** onBackPressed - переопределение метода
     *      Добавление к методу родительского класса завершение активности
     * */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
