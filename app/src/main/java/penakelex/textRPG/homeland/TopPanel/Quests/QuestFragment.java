package penakelex.textRPG.homeland.TopPanel.Quests;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import penakelex.textRPG.homeland.Databases.QuestsDatabase.Quest;
import penakelex.textRPG.homeland.databinding.FragmentQuestBinding;

public class QuestFragment extends Fragment {
    private FragmentQuestBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQuestBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public static android.app.Fragment questFragment(List<Quest> questsInformation, int position) {
        QuestFragment questFragment = new QuestFragment();
        Bundle bundle = new Bundle();

        Quest questInformation = questsInformation.get(position);
        bundle.putInt("ID", questInformation.getID());
        bundle.putString("Name", questInformation.getName());
        questFragment.setArguments(bundle);
        return questFragment;
    }
}