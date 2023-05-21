package penakelex.textRPG.homeland.Adapters.TradingAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import penakelex.textRPG.homeland.databinding.ItemTradingBinding;

public class TradingAdapter extends RecyclerView.Adapter<TradingAdapter.ViewHolder> {
    private ArrayList<TradingInformation> information = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTradingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position));
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    public void setInformation(ArrayList<TradingInformation> information) {
        this.information = information;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemTradingBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemTradingBinding.bind(itemView);
        }

        public void bind(TradingInformation tradingInformation) {
            binding.nameOfItem.setText(tradingInformation.getName());
            binding.valueOfItem.setText(String.valueOf(tradingInformation.getValue()));
        }
    }
}
