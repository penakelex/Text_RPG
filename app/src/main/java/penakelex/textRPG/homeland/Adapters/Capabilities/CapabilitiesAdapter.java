package penakelex.textRPG.homeland.Adapters.Capabilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import penakelex.textRPG.homeland.Databases.TalentsDatabase.TalentsDatabase;
import penakelex.textRPG.homeland.databinding.CapabilitiesItemBinding;

public class CapabilitiesAdapter extends RecyclerView.Adapter<CapabilitiesAdapter.ViewHolder> {
    private ArrayList<CapabilitiesInformation> information;
    private OnCapabilityItemClickListener clickListener;
    public interface OnCapabilityItemClickListener {
        void onClickListener(String name, int ID);
    }

    public CapabilitiesAdapter(OnCapabilityItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context) {
        this.information = TalentsDatabase.getHavingTalents(context);
        //this.information.addAll()
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CapabilitiesItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position));
        holder.itemView.setOnClickListener(listener -> clickListener.onClickListener(information.get(position).getCapabilityName(), information.get(position).getCapabilityID()));
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final CapabilitiesItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = CapabilitiesItemBinding.bind(itemView);
        }

        @SuppressLint("SetTextI18n")
        public void bind(CapabilitiesInformation capabilitiesInformation) {
            binding.capabilityName.setText(capabilitiesInformation.getCapabilityName());
        }
    }
}
