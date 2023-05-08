package penakelex.textRPG.homeland.Adapters.QuestStages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Databases.QuestsDatabase.QuestsDatabase;
import penakelex.textRPG.homeland.databinding.QuestStageItemBinding;

public class QuestStagesAdapter extends RecyclerView.Adapter<QuestStagesAdapter.ViewHolder> {
    private ArrayList<QuestStageInformation> information = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(QuestStageItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position));
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(int ID, Context context) {
        short stages = QuestsDatabase.getDatabase(context).questsDao().getQuests().get(ID).getStages();
        ArrayList<QuestStageInformation> arrayList = new ArrayList<>();
        for (int stage = 0; stage < stages; stage++) arrayList.add(new QuestStageInformation(ID, stage, context));
        this.information = (ArrayList<QuestStageInformation>) arrayList.clone();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private QuestStageItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = QuestStageItemBinding.bind(itemView);
        }

        public void bind(QuestStageInformation questStageInformation) {
            binding.stage.setText(questStageInformation.getStage());
        }
    }
}
