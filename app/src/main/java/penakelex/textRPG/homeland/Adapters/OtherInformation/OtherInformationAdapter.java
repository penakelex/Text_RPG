package penakelex.textRPG.homeland.Adapters.OtherInformation;

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
import penakelex.textRPG.homeland.databinding.OtherInformationItemBinding;

public class OtherInformationAdapter extends RecyclerView.Adapter<OtherInformationAdapter.ViewHolder> {
    private List<OtherInformationItem> information = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(OtherInformationItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
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
    public void setInformation(Context context, List<OtherInformationItem> otherInformationItems) {
        this.information = otherInformationItems;
        this.context = context;
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private final OtherInformationItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = OtherInformationItemBinding.bind(itemView);
        }

        public void bind(OtherInformationItem otherInformationItem, Context context) {
            binding.nameOfOtherInformation.setText(context.getResources().getString(otherInformationItem.getName()));
            binding.valueOfOtherInformation.setText(String.valueOf(otherInformationItem.getValue()));
        }
    }
}
