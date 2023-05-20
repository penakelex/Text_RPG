package penakelex.textRPG.homeland.Adapters.Skills;

import static penakelex.textRPG.homeland.Main.Constants.Homeland_Tag;
import static penakelex.textRPG.homeland.Main.Constants.Skill_Points;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabase;
import penakelex.textRPG.homeland.Databases.SkillsDatabase.SkillsDatabaseHelper;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ItemSkillBinding;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.ViewHolder> {
    private ArrayList<SkillInformation> information = new ArrayList<>();
    private OnSkillItemClickListener clickListener;
    private Context context;
    private View view;
    private int lastPosition = -1;
    private SharedPreferences sharedPreferences;

    public interface OnSkillItemClickListener {
        void onClickListener(String name, int position);

        void setNewPoints();
    }

    public SkillAdapter(OnSkillItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SkillAdapter.ViewHolder(ItemSkillBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position), context);
        holder.itemView.setOnClickListener(listener -> onClicked(holder, position));
        holder.binding.raiseSkill.setOnClickListener(listener -> raisingSkill(position));
        holder.binding.downgradeSkill.setOnClickListener(listener -> downgradingSkill(position));
    }

    private void onClicked(ViewHolder holder, int position) {
        if (position != lastPosition) {
            clickListener.onClickListener(information.get(position).getName(), position);
            holder.binding.containerForSkillItem.setBackgroundColor(context.getResources().getColor(R.color.gray));
            notifyItemChanged(lastPosition);
            lastPosition = position;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void raisingSkill(int position) {
        sharedPreferences = context.getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (sharedPreferences.getInt(Skill_Points, 0) >= 1 && information.get(position).getValue() <= 99) {
            ArrayList<SkillInformation> newInformation = new ArrayList<>();
            for (int i = 0; i < getItemCount(); i++) {
                if (i == position) {
                    newInformation.add(new SkillInformation(information.get(i).getName(), information.get(i).getValue() + 1));
                    continue;
                }
                newInformation.add(new SkillInformation(information.get(i).getName(), information.get(i).getValue()));
            }
            information = (ArrayList<SkillInformation>) newInformation.clone();
            sharedPreferences.edit().putInt(Skill_Points, sharedPreferences.getInt(Skill_Points, 0) - 1).apply();
            clickListener.setNewPoints();
            notifyDataSetChanged();
        } else if (information.get(position).getValue() == 100) {
            Snackbar.make(view, context.getResources().getString(R.string.maximum_value_skill), Snackbar.LENGTH_SHORT).setTextColor(context.getResources().getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        } else {
            Snackbar.make(view, context.getResources().getString(R.string.not_enough_skill_points), Snackbar.LENGTH_SHORT).setTextColor(context.getResources().getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void downgradingSkill(int position) {
        sharedPreferences = context.getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        if (information.get(position).getValue() > SkillsDatabase.getValueSkill(new SkillsDatabaseHelper(context).getReadableDatabase(), position + 1)) {
            ArrayList<SkillInformation> newInformation = new ArrayList<>();
            for (int i = 0; i < getItemCount(); i++) {
                if (i == position) {
                    newInformation.add(new SkillInformation(information.get(i).getName(), information.get(i).getValue() - 1));
                    continue;
                }
                newInformation.add(new SkillInformation(information.get(i).getName(), information.get(i).getValue()));
            }
            information = (ArrayList<SkillInformation>) newInformation.clone();
            sharedPreferences.edit().putInt(Skill_Points, sharedPreferences.getInt(Skill_Points, 0) + 1).apply();
            clickListener.setNewPoints();
            notifyDataSetChanged();
        } else {
            Snackbar.make(view, context.getResources().getString(R.string.minimum_value_skill), Snackbar.LENGTH_SHORT).setTextColor(context.getResources().getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        }
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    public ArrayList<SkillInformation> getInformation() {
        return (ArrayList<SkillInformation>) information.clone();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context, View view) {
        this.context = context;
        this.view = view;
        this.information = SkillsDatabase.getSkillsInformation(new SkillsDatabaseHelper(context).getReadableDatabase());
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemSkillBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemSkillBinding.bind(itemView);
        }

        public void bind(SkillInformation skillInformation, Context context) {
            binding.containerForSkillItem.setBackgroundColor(context.getResources().getColor(R.color.white));
            binding.skillName.setText(skillInformation.getName());
            binding.skillValue.setText(String.valueOf(skillInformation.getValue()));
        }
    }
}
