package penakelex.textRPG.homeland.Adapters.Quests;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import penakelex.textRPG.homeland.Databases.QuestsDatabase.Quest;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.QuestsDatabase;
import penakelex.textRPG.homeland.databinding.QuestItemBinding;

public class QuestsAdapter extends RecyclerView.Adapter<QuestsAdapter.ViewHolder> {
    private ArrayList<QuestInformation> information;
    private final ClickListener clickListener;

    public QuestsAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(QuestsDatabase questsDatabase) {
        List<Quest> questsInformation = questsDatabase.questsDao().getQuests();
        ArrayList<QuestInformation> questInformation = new ArrayList<>();
        for (int i = 0; i < questsInformation.size(); i++)
            questInformation.add(new QuestInformation(questsInformation.get(i).getID(), questsInformation.get(i).getStage(), questsInformation.get(i).getName()));
        this.information = questInformation;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(QuestItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    public interface ClickListener {
        void OnQuestClick(ViewHolder viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position));
        holder.itemView.setOnClickListener(listener -> clickListener.OnQuestClick(holder));
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
