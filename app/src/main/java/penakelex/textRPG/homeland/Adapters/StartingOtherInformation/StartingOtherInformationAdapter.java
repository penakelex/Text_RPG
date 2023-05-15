package penakelex.textRPG.homeland.Adapters.StartingOtherInformation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationDatabase;
import penakelex.textRPG.homeland.Databases.OtherInfromationDatabase.OtherInformationDatabaseHelper;
import penakelex.textRPG.homeland.databinding.ItemStartingOtherInformationBinding;

public class StartingOtherInformationAdapter extends RecyclerView.Adapter<StartingOtherInformationAdapter.ViewHolder> {
    private ArrayList<StartingOtherInformationInformation> information = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StartingOtherInformationAdapter.ViewHolder(ItemStartingOtherInformationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
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
        this.information = OtherInformationDatabase.getStartingInformation(new OtherInformationDatabaseHelper(context).getReadableDatabase());
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemStartingOtherInformationBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemStartingOtherInformationBinding.bind(itemView);
        }

        public void bind(StartingOtherInformationInformation information) {
            binding.name.setText(information.getName());
            binding.value.setText(String.valueOf(information.getValue()));
        }
    }
}
