package penakelex.textRPG.homeland.TopPanel.Quests;

import static penakelex.textRPG.homeland.Main.Constants.Current_Top_Panel_Activity;
import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import penakelex.textRPG.homeland.Adapters.QuestStages.QuestStagesAdapter;
import penakelex.textRPG.homeland.Adapters.Quests.QuestsAdapter;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.Quest;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.QuestsDatabase;
import penakelex.textRPG.homeland.Main.TopPanelParentActivity;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ActivityQuestsBinding;

public class QuestsActivity extends TopPanelParentActivity {
    private ActivityQuestsBinding binding;
    private QuestsDatabase questsDatabase;
    private final QuestsAdapter.OnQuestClickListener clickListener = new QuestsAdapter.OnQuestClickListener() {
        @Override
        public void onQuestClick(int position) {
            List<Quest> questList = questsDatabase.questsDao().getQuests();
            binding.questName.setText(questList.get(position).getName());
            QuestStagesAdapter stagesAdapter = new QuestStagesAdapter();
            stagesAdapter.setInformation(position, getApplicationContext());
            binding.containerForQuestStages.setAdapter(stagesAdapter);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestsBinding.inflate(getLayoutInflater());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        toolBar();
        questsDatabase = QuestsDatabase.getDatabase(getApplicationContext());
        QuestsAdapter questsAdapter = new QuestsAdapter(clickListener);
        questsAdapter.setInformation(questsDatabase);
        binding.containerForQuests.setAdapter(questsAdapter);
    }

    private void toolBar() {
        SharedPreferences sharedPreferences = getSharedPreferences(Homeland_Tag, MODE_PRIVATE);
        sharedPreferences.edit().putInt(Current_Top_Panel_Activity, 2).apply();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar1);
        setSupportActionBar(toolbar);
        handlingToolBar(toolbar);
        toolbar.setNavigationOnClickListener(listener -> onBackPressed());
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