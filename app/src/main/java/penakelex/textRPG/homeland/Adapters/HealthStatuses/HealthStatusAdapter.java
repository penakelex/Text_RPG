package penakelex.textRPG.homeland.Adapters.HealthStatuses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.HealthDatabase.HealthItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ItemHealthBinding;

public class HealthStatusAdapter extends RecyclerView.Adapter<HealthStatusAdapter.ViewHolder> {
    private List<HealthItem> information = new ArrayList<>();
    private Context context;
    private final OnHealthItemClickListener clickListener;
    private int lastPosition = -1;
    public interface OnHealthItemClickListener {
        void onClickListener(HealthItem healthItem);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemHealthBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position), context);
        holder.itemView.setOnClickListener(listener -> onClicked(holder, position));
    }

    private void onClicked(ViewHolder holder, int position) {
        if (lastPosition != position) {
            clickListener.onClickListener(information.get(position));
            holder.binding.containerForHealthItem.setBackgroundColor(context.getResources().getColor(R.color.gray));
            notifyItemChanged(lastPosition);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    public HealthStatusAdapter(OnHealthItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context, List<HealthItem> healthItems) {
        this.context = context;
        this.information = healthItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemHealthBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemHealthBinding.bind(itemView);
        }

        @SuppressLint("DefaultLocale")
        public void bind(HealthItem healthItem, Context context) {
            binding.healthName.setText(context.getResources().getString(healthItem.getName()));
            binding.containerForHealthItem.setBackgroundColor(context.getResources().getColor(R.color.white));
            binding.healthStatus.setText(String.format("%d/%d", healthItem.getValue(), healthItem.getBaseValue()));
            if (healthItem.getValue() == 0) {
                binding.healthName.setTextColor(context.getResources().getColor(R.color.black));
                binding.healthStatus.setTextColor(context.getResources().getColor(R.color.black));
            }
        }
    }
}
