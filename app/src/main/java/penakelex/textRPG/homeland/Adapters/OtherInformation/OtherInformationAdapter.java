package penakelex.textRPG.homeland.Adapters.OtherInformation;

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
import penakelex.textRPG.homeland.databinding.OtherInformationItemBinding;

public class OtherInformationAdapter extends RecyclerView.Adapter<OtherInformationAdapter.ViewHolder> {
    private ArrayList<OtherInformationInformation> information = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(OtherInformationItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
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
        this.information = OtherInformationDatabase.getInformation(new OtherInformationDatabaseHelper(context).getReadableDatabase());
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private final OtherInformationItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = OtherInformationItemBinding.bind(itemView);
        }

        public void bind(OtherInformationInformation otherInformationInformation) {
            binding.nameOfOtherInformation.setText(otherInformationInformation.getName());
            binding.valueOfOtherInformation.setText(String.valueOf(otherInformationInformation.getValue()));
        }
    }
}
