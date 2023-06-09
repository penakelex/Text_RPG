package penakelex.textRPG.homeland.TopPanel.Quests;

import static penakelex.textRPG.homeland.Main.Constants.Current_Top_Panel_Activity;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import penakelex.textRPG.homeland.Adapters.QuestStages.QuestStagesAdapter;
import penakelex.textRPG.homeland.Adapters.Quests.QuestsAdapter;
import penakelex.textRPG.homeland.Databases.Tables.QuestsDatabase.QuestItem;
import penakelex.textRPG.homeland.Main.TopPanelParentActivity;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.ViewModels.QuestsViewModel.QuestsViewModel;
import penakelex.textRPG.homeland.databinding.ActivityQuestsBinding;

/** QuestsActivity
 *      Активность с заданиями
 * */
public class QuestsActivity extends TopPanelParentActivity {
    private ActivityQuestsBinding binding;
    private QuestStagesAdapter stagesAdapter;
    private QuestsViewModel questsViewModel;

    private final QuestsAdapter.OnQuestClickListener clickListener = new QuestsAdapter.OnQuestClickListener() {
        @Override
        public void onQuestClick(QuestItem quest) {
            binding.questName.setText(getResources().getString(quest.getName()));
            stagesAdapter.setInformation(quest, getApplicationContext());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questsViewModel = new ViewModelProvider(this).get(QuestsViewModel.class);
        questsViewModel.initiate(getApplication());
        binding = ActivityQuestsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolBar();
        //Установка адаптера с заданиями
        questsAdapter();
        //Установка адаптера с этапами задания
        questsStagesAdapter();
    }

    /** questsStagesAdapter - процедура
     *      Установка адаптера с этапами выбранного задания
     * */
    private void questsStagesAdapter() {
        stagesAdapter = new QuestStagesAdapter();
        binding.containerForQuestStages.setAdapter(stagesAdapter);
    }

    /** questsAdapter - процедура
     *      Установка адаптера с заданиями
     * */
    private void questsAdapter() {
        QuestsAdapter questsAdapter = new QuestsAdapter(clickListener);
        questsAdapter.setInformation(questsViewModel.getAllQuests(), getApplicationContext());
        binding.containerForQuests.setAdapter(questsAdapter);
    }

    /** toolBar - процедура
     *      Установка и управление виджетом
     * */
    private void toolBar() {
        SharedPreferences sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        sharedPreferences.edit().putInt(Current_Top_Panel_Activity, 2).apply();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar1);
        setSupportActionBar(toolbar);
        handlingToolBar(toolbar);
        toolbar.setNavigationOnClickListener(listener -> onNavigationClick());
    }

    /** onNavigationClick - процедура
     *      Возвращает назад при нажатии на кнопку
     * */
    private void onNavigationClick() {
        binding = null;
        onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}