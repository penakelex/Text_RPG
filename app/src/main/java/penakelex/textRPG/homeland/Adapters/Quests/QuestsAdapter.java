package penakelex.textRPG.homeland.Adapters.Quests;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import penakelex.textRPG.homeland.Databases.QuestsDatabase.Quest;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.QuestsDatabase;
import penakelex.textRPG.homeland.databinding.QuestItemBinding;

public class QuestsAdapter extends RecyclerView.Adapter<QuestsAdapter.ViewHolder> {
    private ArrayList<QuestInformation> information = new ArrayList<>();
    private final OnQuestClickListener clickListener;

    public QuestsAdapter(OnQuestClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(QuestsDatabase questsDatabase) {
        List<Quest> questsInformation = questsDatabase.questsDao().getQuests();
        Log.d("quests", String.valueOf(questsInformation));
        ArrayList<QuestInformation> questInformation = new ArrayList<>();
        for (int i = 0; i < questsInformation.size(); i++)
            questInformation.add(new QuestInformation(questsInformation.get(i).getID(), questsInformation.get(i).getStages(), questsInformation.get(i).getName()));
        this.information = questInformation;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(QuestItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    public interface OnQuestClickListener {
        void onQuestClick(int position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position));
        holder.itemView.setOnClickListener(listener -> clickListener.onQuestClick(position));
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final QuestItemBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = QuestItemBinding.bind(itemView);
        }

        public void bind(QuestInformation questInformation) {
            binding.questName.setText(questInformation.getName());
        }
    }
}
