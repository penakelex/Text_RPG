package penakelex.textRPG.homeland.Adapters.StartingSkills;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabaseHelper;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ItemStartingSkillBinding;

public class StartingSkillsAdapter extends RecyclerView.Adapter<StartingSkillsAdapter.ViewHolder> {
    private ArrayList<StartingSkillInformation> information = new ArrayList<>();
    private OnSkillItemClickListener clickListener;
    private Context context;
    private int lastPosition = -1;

    public StartingSkillsAdapter(OnSkillItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnSkillItemClickListener {
        void onClickListener(String name, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StartingSkillsAdapter.ViewHolder(ItemStartingSkillBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position), context, position, lastPosition);
        holder.itemView.setOnClickListener(l -> onClicked(holder, position));
    }

    private void onClicked(ViewHolder holder, int position) {
        if (lastPosition != position) {
            clickListener.onClickListener(information.get(position).getName(), position);
            holder.binding.containerForSkillItem.setBackgroundColor(context.getResources().getColor(R.color.gray));
            notifyItemChanged(lastPosition);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context) {
        this.information = SkillsDatabase.getStartingSkillsInformation(new SkillsDatabaseHelper(context).getReadableDatabase());
        this.context = context;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemStartingSkillBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemStartingSkillBinding.bind(itemView);
        }

        public void bind(StartingSkillInformation startingSkillInformation, Context context, int position, int lastPosition) {
            if (position == lastPosition)
                binding.containerForSkillItem.setBackgroundColor(context.getResources().getColor(R.color.gray));
            else
                binding.containerForSkillItem.setBackgroundColor(context.getResources().getColor(R.color.white));
            binding.nameOfSkill.setText(startingSkillInformation.getName());
            binding.checkBox.setChecked(startingSkillInformation.isMain());
        }
    }
}
