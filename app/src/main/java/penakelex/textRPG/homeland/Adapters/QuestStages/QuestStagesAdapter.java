package penakelex.textRPG.homeland.Adapters.QuestStages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Databases.Tables.QuestsDatabase.QuestItem;
import penakelex.textRPG.homeland.databinding.QuestStageItemBinding;

public class QuestStagesAdapter extends RecyclerView.Adapter<QuestStagesAdapter.ViewHolder> {
    private final ArrayList<QuestStageInformation> information = new ArrayList<>();

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
    public void setInformation(QuestItem questItem, Context context) {
        information.clear();
        for (int stage = 0; stage < questItem.getStages(); stage++)
            information.add(new QuestStageInformation(questItem.getID(), stage, context));
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final QuestStageItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = QuestStageItemBinding.bind(itemView);
        }

        public void bind(QuestStageInformation questStageInformation) {
            binding.stage.setText(questStageInformation.getStage());
        }
    }
}
