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

import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryDatabase;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryDatabaseHelper;
import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.InventoryItemBinding;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    private ArrayList<InventoryItemInformation> information = new ArrayList<>();
    private OnInventoryItemClickListener clickListener;
    private int lastPosition = -1;
    private Context context;

    public interface OnInventoryItemClickListener {
        void onClickListener(long primaryID, int ID);
    }

    public InventoryAdapter(OnInventoryItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(InventoryDatabase inventoryDatabase, Context context, int ownersID) {
        List<InventoryItem> inventoryItems = inventoryDatabase.inventoryDao().getInventory(ownersID);
        ArrayList<InventoryItemInformation> inventoryItemsInformation = new ArrayList<>();
        for (int i = 0; i < inventoryItems.size(); i++)
            inventoryItemsInformation.add(new InventoryItemInformation(inventoryItems.get(i).getId(), inventoryItems.get(i).getPrimaryID()));
        this.information = (ArrayList<InventoryItemInformation>) inventoryItemsInformation.clone();
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
        holder.bind(information.get(position), context);
        holder.itemView.setOnClickListener(listener -> onClicked(position, holder));
    }

    private void onClicked(int position, ViewHolder holder) {
        if (position != lastPosition) {
            clickListener.onClickListener(information.get(position).getPrimaryID(), information.get(position).getID());
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

        public void bind(InventoryItemInformation inventoryItemInformation, Context context) {
            String[] itemInformation = InventoryDatabaseHelper.getInventoryItemShortInformation(itemView.getContext(), inventoryItemInformation.getID());
            binding.containerForInventoryItem.setBackgroundColor(context.getResources().getColor(R.color.light_blue_green));
            binding.itemName.setText(itemInformation[0]);
            binding.itemType.setText(itemInformation[1]);
        }
    }
}
