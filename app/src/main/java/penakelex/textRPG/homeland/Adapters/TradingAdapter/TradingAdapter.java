package penakelex.textRPG.homeland.Adapters.TradingAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Databases.InventoryDatabase.InventoryDatabaseHelper;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ItemTradingBinding;

public class TradingAdapter extends RecyclerView.Adapter<TradingAdapter.ViewHolder> {
    private ArrayList<TradingInformation> information = new ArrayList<>();
    private final OnTradingItemClickListener clickListener;
    private long lastPosition = -1;
    private Context context;

    public interface OnTradingItemClickListener {
        void onClickListener(long ID);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTradingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position), context);
        holder.itemView.setOnClickListener(listener -> onClicked(position, holder));
    }

    private void onClicked(int position, ViewHolder holder) {
        if (lastPosition != position) {
            clickListener.onClickListener(information.get(position).getPrimaryID());
            holder.binding.containerForTradingItem.setBackgroundColor(context.getResources().getColor(R.color.blue_green));
            notifyItemChanged((int) lastPosition);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context, int ID, boolean typeTrading) {
        this.information = InventoryDatabaseHelper.getTradingInformation(context, ID, typeTrading);
        this.context = context;
        notifyDataSetChanged();
    }

    public TradingAdapter(OnTradingItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTradingBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemTradingBinding.bind(itemView);
        }

        public void bind(TradingInformation tradingInformation, Context context) {
            binding.containerForTradingItem.setBackgroundColor(context.getResources().getColor(R.color.light_blue_green));
            binding.nameOfItem.setText(tradingInformation.getName());
            binding.valueOfItem.setText(String.valueOf(tradingInformation.getValue()));
            Log.d("name", tradingInformation.getName());
            Log.d("value", String.valueOf(tradingInformation.getValue()));
        }
    }
}
