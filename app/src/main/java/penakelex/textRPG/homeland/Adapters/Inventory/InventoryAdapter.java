package penakelex.textRPG.homeland.Adapters.Inventory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryTableHelper;
import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.InventoryItemBinding;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    private List<InventoryItem> information = new ArrayList<>();
    private final OnInventoryItemClickListener clickListener;
    private int lastPosition = -1;
    private Context context;
    private final InventoryTableHelper tableHelper;

    public interface OnInventoryItemClickListener {
        void onClickListener(InventoryItem inventoryItem);
    }

    public InventoryAdapter(OnInventoryItemClickListener clickListener, InventoryTableHelper tableHelper) {
        this.tableHelper = tableHelper;
        this.clickListener = clickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context, List<InventoryItem> inventoryItems) {
        this.information = inventoryItems;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(InventoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position), context, tableHelper);
        holder.itemView.setOnClickListener(listener -> onClicked(position, holder));
    }

    private void onClicked(int position, ViewHolder holder) {
        if (position != lastPosition) {
            clickListener.onClickListener(information.get(position));
            holder.binding.containerForInventoryItem.setBackgroundColor(context.getResources().getColor(R.color.blue_green));
            notifyItemChanged(lastPosition);
            lastPosition = position;
        }
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final InventoryItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = InventoryItemBinding.bind(itemView);
        }

        public void bind(InventoryItem inventoryItem, Context context, InventoryTableHelper tableHelper) {
            String[] itemInformation = tableHelper.getInventoryItemShortInformation(context, inventoryItem.getId());
            binding.containerForInventoryItem.setBackgroundColor(context.getResources().getColor(R.color.light_blue_green));
            binding.itemName.setText(itemInformation[0]);
            binding.itemType.setText(itemInformation[1]);
        }
    }
}
