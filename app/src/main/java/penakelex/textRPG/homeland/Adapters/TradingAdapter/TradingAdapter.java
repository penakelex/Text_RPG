package penakelex.textRPG.homeland.Adapters.TradingAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryItem;
import penakelex.textRPG.homeland.Databases.Tables.InventoryDatabase.InventoryTableHelper;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ItemTradingBinding;

public class TradingAdapter extends RecyclerView.Adapter<TradingAdapter.ViewHolder> {
    private List<InventoryItem> information = new ArrayList<>();
    private final OnTradingItemClickListener clickListener;
    private long lastPosition = -1;
    private Context context;
    //TODO: Возможно не доделана механика сохранения позиции
    private final InventoryTableHelper tableHelper;

    public interface OnTradingItemClickListener {
        void onClickListener(InventoryItem inventoryItem);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTradingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position), context, tableHelper);
        holder.itemView.setOnClickListener(listener -> onClicked(position, holder));
    }

    private void onClicked(int position, ViewHolder holder) {
        if (lastPosition != position) {
            clickListener.onClickListener(information.get(position));
            holder.binding.containerForTradingItem.setBackgroundColor(context.getResources().getColor(R.color.blue_green));
            notifyItemChanged((int) lastPosition);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    public void setLastPosition(long lastPosition) {
        this.lastPosition = lastPosition;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context, List<InventoryItem> inventoryItems) {
        this.information = inventoryItems;
        this.context = context;
        notifyDataSetChanged();
    }

    public TradingAdapter(OnTradingItemClickListener clickListener, InventoryTableHelper tableHelper) {
        this.clickListener = clickListener;
        this.tableHelper = tableHelper;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTradingBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemTradingBinding.bind(itemView);
        }

        public void bind(InventoryItem item, Context context, InventoryTableHelper tableHelper) {
            binding.containerForTradingItem.setBackgroundColor(context.getResources().getColor(R.color.light_blue_green));
            binding.nameOfItem.setText(tableHelper.getAllInventoryItemInformation(context, item.getId())[0]);
            binding.valueOfItem.setText(String.valueOf(item.getPrice()));
        }
    }
}
