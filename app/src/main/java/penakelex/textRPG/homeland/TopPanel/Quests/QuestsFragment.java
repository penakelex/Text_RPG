package penakelex.textRPG.homeland.TopPanel.Quests;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Adapters.Quests.QuestsAdapter;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.Quest;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.QuestsDatabase;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.FragmentQuestsBinding;

public class QuestsFragment extends Fragment {
    private FragmentQuestsBinding binding;
    private QuestsAdapter questsAdapter;
    private QuestsDatabase questsDatabase;
    private QuestsAdapter.ClickListener clickListener = new QuestsAdapter.ClickListener() {
        @Override
        public void OnQuestClick(QuestsAdapter.ViewHolder viewHolder) {
            getFragmentManager().beginTransaction().
                    replace(R.id.containerForQuestsFragments, QuestFragment.questFragment(questsDatabase.questsDao().getQuests(), viewHolder.getAdapterPosition()));
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuestsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questsAdapter = new QuestsAdapter(clickListener);
        questsDatabase.questsDao().addQuest(new Quest("Квест", (short) 1));
        questsDatabase = QuestsDatabase.getDatabase(getActivity());
        questsAdapter.setInformation(questsDatabase);
    }
}