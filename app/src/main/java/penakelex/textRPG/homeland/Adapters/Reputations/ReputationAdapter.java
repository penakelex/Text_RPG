package penakelex.textRPG.homeland.Adapters.Reputations;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Databases.ReputationsDatabase.ReputationDatabase;
import penakelex.textRPG.homeland.Databases.ReputationsDatabase.ReputationDatabaseHelper;
import penakelex.textRPG.homeland.databinding.ItemReputationBinding;

public class ReputationAdapter extends RecyclerView.Adapter<ReputationAdapter.ViewHolder> {
    private ArrayList<String> information = new ArrayList<>();
    private final OnReputationItemClickListener clickListener;
    public interface OnReputationItemClickListener {
        void onClickListener(String name, int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemReputationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position));
        holder.itemView.setOnClickListener(listener -> clickListener.onClickListener(information.get(position), position));
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context) {
        this.information = ReputationDatabaseHelper.getReputationsInformation(context.getApplicationContext());
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

        public void bind(String name) {
            binding.reputationsName.setText(name);
        }
    }
}
