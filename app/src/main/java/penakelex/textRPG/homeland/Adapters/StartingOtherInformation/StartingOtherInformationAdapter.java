package penakelex.textRPG.homeland.Adapters.StartingOtherInformation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase.OtherInformationItem;
import penakelex.textRPG.homeland.databinding.ItemStartingOtherInformationBinding;

public class StartingOtherInformationAdapter extends RecyclerView.Adapter<StartingOtherInformationAdapter.ViewHolder> {
    private List<OtherInformationItem> information = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StartingOtherInformationAdapter.ViewHolder(ItemStartingOtherInformationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
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
    public void setInformation(List<OtherInformationItem> otherInformationItems,Context context) {
        this.information = otherInformationItems;
        this.context = context;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemStartingOtherInformationBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemStartingOtherInformationBinding.bind(itemView);
        }

        public void bind(OtherInformationItem information, Context context) {
            binding.name.setText(context.getResources().getString(information.getName()));
            binding.value.setText(String.valueOf(information.getValue()));
        }
    }
}
