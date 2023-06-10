package penakelex.textRPG.homeland.Adapters.Statistics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.StatisticsDatabase.StatisticItem;
import penakelex.textRPG.homeland.databinding.ItemStatisticsBinding;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ViewHolder> {
    private List<StatisticItem> information = new ArrayList<>();
    private Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemStatisticsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position), context);

    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context, List<StatisticItem> statisticItems) {
        this.information = statisticItems;
        this.context = context;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemStatisticsBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemStatisticsBinding.bind(itemView);
        }

        public void bind(StatisticItem statisticItem, Context context) {
            binding.nameOfStatistic.setText(context.getResources().getString(statisticItem.getName()));
            binding.countOfStatistic.setText(String.valueOf(statisticItem.getCount()));
        }
    }
}
