package penakelex.textRPG.homeland.Adapters.Quests;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.QuestsDatabase.QuestItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.QuestItemBinding;

public class QuestsAdapter extends RecyclerView.Adapter<QuestsAdapter.ViewHolder> {
    private List<QuestItem> information = new ArrayList<>();
    private final OnQuestClickListener clickListener;
    private int lastPosition = -1;
    private Context context;

    public QuestsAdapter(OnQuestClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(List<QuestItem> quests, Context context) {
        this.information = quests;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(QuestItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    public interface OnQuestClickListener {
        void onQuestClick(QuestItem questItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position), context);
        holder.itemView.setOnClickListener(listener -> onClicked(holder, position));
    }

    private void onClicked(ViewHolder holder, int position) {
        if (lastPosition != position) {
            clickListener.onQuestClick(information.get(position));
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

        public void bind(QuestItem quest, Context context) {
            binding.containerForQuestItem.setBackgroundColor(context.getResources().getColor(R.color.purple));
            binding.questName.setText(context.getResources().getString(quest.getName()));
        }
    }
}
