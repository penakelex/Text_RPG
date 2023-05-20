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

import penakelex.textRPG.homeland.Databases.QuestsDatabase.Quest;
import penakelex.textRPG.homeland.Databases.QuestsDatabase.QuestsDatabase;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.QuestItemBinding;

public class QuestsAdapter extends RecyclerView.Adapter<QuestsAdapter.ViewHolder> {
    private ArrayList<QuestInformation> information = new ArrayList<>();
    private final OnQuestClickListener clickListener;
    private int lastPosition = -1;
    private Context context;

    public QuestsAdapter(OnQuestClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(QuestsDatabase questsDatabase, Context context) {
        List<Quest> questsInformation = questsDatabase.questsDao().getQuests();
        ArrayList<QuestInformation> questInformation = new ArrayList<>();
        for (int i = 0; i < questsInformation.size(); i++)
            questInformation.add(new QuestInformation(questsInformation.get(i).getID(), questsInformation.get(i).getStages(), questsInformation.get(i).getName()));
        this.information = questInformation;
        this.context = context;
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
        holder.bind(information.get(position), context);
        holder.itemView.setOnClickListener(listener -> onClicked(holder, position));
    }

    private void onClicked(ViewHolder holder, int position) {
        if (lastPosition != position) {
            clickListener.onQuestClick(position);
            holder.binding.containerForQuestItem.setBackgroundColor(context.getResources().getColor(R.color.dark_purple));
            notifyItemChanged(lastPosition);
            lastPosition = position;
        }
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

        public void bind(QuestInformation questInformation, Context context) {
            binding.containerForQuestItem.setBackgroundColor(context.getResources().getColor(R.color.purple));
            binding.questName.setText(questInformation.getName());
        }
    }
}
