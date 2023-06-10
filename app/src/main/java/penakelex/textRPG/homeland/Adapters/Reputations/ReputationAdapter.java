package penakelex.textRPG.homeland.Adapters.Reputations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.ReputationsDatabase.ReputationItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ItemReputationBinding;

public class ReputationAdapter extends RecyclerView.Adapter<ReputationAdapter.ViewHolder> {
    //TODO: Возможно механика нажатия не работает
    private List<ReputationItem> information = new ArrayList<>();
    private final OnReputationItemClickListener clickListener;
    private int lastPosition = -1;
    private Context context;
    public interface OnReputationItemClickListener {
        void onClickListener(ReputationItem reputationItem);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemReputationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position), context);
        holder.itemView.setOnClickListener(listener -> onClicked(holder, position));
    }

    private void onClicked(ViewHolder holder, int position) {
        if (lastPosition != position) {
            clickListener.onClickListener(information.get(position));
            holder.binding.reputationsName.setBackgroundColor(context.getResources().getColor(R.color.gray));
            notifyItemChanged(lastPosition);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context, List<ReputationItem> reputationItems) {
        this.information = reputationItems;
        this.context = context;
        notifyDataSetChanged();
    }

    public ReputationAdapter(OnReputationItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemReputationBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemReputationBinding.bind(itemView);
        }

        public void bind(ReputationItem reputationItem, Context context) {
            binding.reputationsName.setBackgroundColor(context.getResources().getColor(R.color.white));
            binding.reputationsName.setText(context.getResources().getString(reputationItem.getName()));
        }
    }
}
