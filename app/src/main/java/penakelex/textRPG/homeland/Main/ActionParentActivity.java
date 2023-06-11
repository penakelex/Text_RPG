package penakelex.textRPG.homeland.Main;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Main_Character_Name;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import penakelex.textRPG.homeland.R;

/** ActionParentActivity
 *      Родительский класс-активность для всех активностей
 * */
public class ActionParentActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tool_bar_menu, menu);
        return true;
    }
    /** handlingToolBar - процедура
     *      Управление виджетом
     *  @param toolbar - виджет
     * */
    public void handlingToolBar(Toolbar toolbar) {
        SharedPreferences sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
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
