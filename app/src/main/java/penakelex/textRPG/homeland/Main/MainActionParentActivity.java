package penakelex.textRPG.homeland.Main;

import static penakelex.textRPG.homeland.Main.Constants.First_Visit_Talents;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.TopPanel.Menu.MenuActivity;
import penakelex.textRPG.homeland.TopPanel.Person.PersonActivity;
import penakelex.textRPG.homeland.TopPanel.Quests.QuestsActivity;

/** MainActionParentActivity
 *      Родительский класс-активность для активностей главного действия
 * */
public class MainActionParentActivity extends ActionParentActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolBar);
        switch (item.getItemId()) {
            case R.id.personToolBar:
                //Переход на активность с информацией о персонаже, если установлена о нём информация
                if (sharedPreferences.getBoolean(First_Visit_Talents, true)) {
                    toolbar.setTitle(getResources().getString(R.string.you8));
                } else {
                    startActivity(new Intent(this, PersonActivity.class));
                }
                break;
                //Переход на активность с заданиями
            case R.id.questsToolBar:
                startActivity(new Intent(this, QuestsActivity.class));
                break;
                //Переход на активность-меню
            case R.id.menuToolBar:
                startActivity(new Intent(this, MenuActivity.class));
                break;
        }
        return false;
    }

    /** onBackPressed - переопределённая процедура
     *      Убирает все действия с нажатия "Назад"
     * */
    @Override
    public void onBackPressed() {
    }
}
