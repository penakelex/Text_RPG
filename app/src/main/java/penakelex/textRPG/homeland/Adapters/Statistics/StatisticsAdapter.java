package penakelex.textRPG.homeland.Adapters.Statistics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Databases.StatisticsDatabase.StatisticsDatabaseHelper;
import penakelex.textRPG.homeland.databinding.ItemStatisticsBinding;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ViewHolder> {
    private ArrayList<StatisticInformation> information = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemStatisticsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position));

    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context) {
        this.information = StatisticsDatabaseHelper.getStatisticsInformation(context);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemStatisticsBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemStatisticsBinding.bind(itemView);
        }

        public void bind(StatisticInformation statisticInformation) {
            binding.nameOfStatistic.setText(statisticInformation.getName());
            binding.countOfStatistic.setText(String.valueOf(statisticInformation.getCount()));
        }
    }
}
