package penakelex.textRPG.homeland.Adapters.StartingTalents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import penakelex.textRPG.homeland.Databases.TalentsDatabase.TalentsDatabase;
import penakelex.textRPG.homeland.Databases.TalentsDatabase.TalentsDatabaseHelper;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ItemStartingTalentBinding;

public class StartingTalentsAdapter extends RecyclerView.Adapter<StartingTalentsAdapter.ViewHolder> {
    private ArrayList<StartingTalentsInformation> information = new ArrayList<>();
    private final OnStartingTalentItemClickListener clickListener;
    private int lastPosition = -1;
    private Context context;

    public StartingTalentsAdapter(OnStartingTalentItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnStartingTalentItemClickListener {
        void onClickListener(String name, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemStartingTalentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position), context, position, lastPosition);
        holder.itemView.setOnClickListener(listener -> isClicked(holder, position));
    }

    private void isClicked(ViewHolder holder, int position) {
        if (lastPosition != position) {
            clickListener.onClickListener(information.get(position).getName(), position);
            holder.binding.containerForCharacteristicItem.setBackgroundColor(context.getResources().getColor(R.color.gray));
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
        this.information = TalentsDatabase.getStartingTalentsInformation(new TalentsDatabaseHelper(context).getReadableDatabase());
        this.context = context;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemStartingTalentBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemStartingTalentBinding.bind(itemView);
        }

        public void bind(StartingTalentsInformation startingTalentsInformation, Context context, int position, int lastPosition) {
            if (lastPosition == position)
                binding.containerForCharacteristicItem.setBackgroundColor(context.getResources().getColor(R.color.gray));
            else
                binding.containerForCharacteristicItem.setBackgroundColor(context.getResources().getColor(R.color.white));
            binding.nameOfTalent.setText(startingTalentsInformation.getName());
            binding.checkBox.setChecked(startingTalentsInformation.isHaving());
        }
    }
}
