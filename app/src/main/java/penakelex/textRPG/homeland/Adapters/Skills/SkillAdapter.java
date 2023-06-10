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

import penakelex.textRPG.homeland.Databases.Tables.SkillsDatabase.SkillsItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ItemSkillBinding;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.ViewHolder> {
    private SkillsItem[] information = new SkillsItem[]{}, currentSavedSkills = new SkillsItem[]{};
    private final OnSkillItemClickListener clickListener;
    private Context context;
    private final View view;
    private int lastPosition = -1;
    private SharedPreferences sharedPreferences;

    public interface OnSkillItemClickListener {
        void onClickListener(SkillsItem skill);

        void setNewPoints();
    }

    public SkillAdapter(OnSkillItemClickListener clickListener, View view) {
        this.view = view;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SkillAdapter.ViewHolder(ItemSkillBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information[position], context);
        holder.itemView.setOnClickListener(listener -> onClicked(holder, position));
        holder.binding.raiseSkill.setOnClickListener(listener -> raisingSkill(position));
        holder.binding.downgradeSkill.setOnClickListener(listener -> downgradingSkill(position));
    }

    private void onClicked(ViewHolder holder, int position) {
        if (position != lastPosition) {
            clickListener.onClickListener(information[position]);
            holder.binding.containerForSkillItem.setBackgroundColor(context.getResources().getColor(R.color.gray));
            notifyItemChanged(lastPosition);
            lastPosition = position;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void raisingSkill(int position) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        if (sharedPreferences.getInt(Skill_Points, 0) >= 1 && information[position].getValue() <= 99 && !information[position].isMain()) {
            information[position].setValue((byte) (information[position].getValue() + 1));
            sharedPreferences.edit().putInt(Skill_Points, sharedPreferences.getInt(Skill_Points, 0) - 1).apply();
            clickListener.setNewPoints();
            notifyDataSetChanged();
        } else if (sharedPreferences.getInt(Skill_Points, 0) >= 1 && information[position].getValue() <= 98 && information[position].isMain()) {
            information[position].setValue((byte) (information[position].getValue() + 2));
            sharedPreferences.edit().putInt(Skill_Points, sharedPreferences.getInt(Skill_Points, 0) - 1).apply();
            clickListener.setNewPoints();
            notifyDataSetChanged();
        } else if (information[position].getValue() == 100) {
            Snackbar.make(view, context.getResources().getString(R.string.maximum_value_skill), Snackbar.LENGTH_SHORT).setTextColor(context.getResources().getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        } else {
            Snackbar.make(view, context.getResources().getString(R.string.not_enough_skill_points), Snackbar.LENGTH_SHORT).setTextColor(context.getResources().getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void downgradingSkill(int position) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(Homeland_Tag, Context.MODE_PRIVATE);
        }
        if (information[position].getValue() > currentSavedSkills[position].getValue()) {
            information[position].setValue((byte) (information[position].getValue() - 1));
            sharedPreferences.edit().putInt(Skill_Points, sharedPreferences.getInt(Skill_Points, 0) + 1).apply();
            clickListener.setNewPoints();
            notifyDataSetChanged();
        } else {
            Snackbar.make(view, context.getResources().getString(R.string.minimum_value_skill), Snackbar.LENGTH_SHORT).setTextColor(context.getResources().getColor(R.color.golden_yellow)).setBackgroundTint(context.getResources().getColor(R.color.dark_purple)).show();
        }
    }

    @Override
    public int getItemCount() {
        return information.length;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context, SkillsItem[] skillsItems) {
        this.context = context;
        this.information = skillsItems;
        this.currentSavedSkills = skillsItems;
        notifyDataSetChanged();
    }

    public SkillsItem[] getInformation() {
        return information;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemSkillBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemSkillBinding.bind(itemView);
        }

        public void bind(SkillsItem skillsItem, Context context) {
            binding.containerForSkillItem.setBackgroundColor(context.getResources().getColor(R.color.white));
            binding.skillName.setText(context.getResources().getString(skillsItem.getName()));
            binding.skillValue.setText(String.valueOf(skillsItem.getValue()));
        }
    }
}
