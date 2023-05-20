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
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.CapabilitiesItemBinding;

public class CapabilitiesAdapter extends RecyclerView.Adapter<CapabilitiesAdapter.ViewHolder> {
    private ArrayList<CapabilitiesInformation> information;
    private OnCapabilityItemClickListener clickListener;
    private Context context;
    private int lastPosition = -1;

    public void onClicked(ViewHolder viewHolder, int position) {
        if (lastPosition != position) {
            clickListener.onClickListener(information.get(position).getCapabilityName(), information.get(position).getCapabilityID());
            viewHolder.binding.capability.setBackgroundColor(context.getResources().getColor(R.color.darker_light_blue));
            notifyItemChanged(lastPosition);
            lastPosition = position;
        }
    }

    public interface OnCapabilityItemClickListener {
        void onClickListener(String name, int ID);
    }

    public CapabilitiesAdapter(OnCapabilityItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context) {
        this.information = TalentsDatabase.getHavingTalents(context);
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(CapabilitiesItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position), context);
        holder.itemView.setOnClickListener(listener -> onClicked(holder, position));
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
        public void bind(CapabilitiesInformation capabilitiesInformation, Context context) {
            binding.capability.setBackgroundColor(context.getResources().getColor(R.color.light_blue));
            binding.capabilityName.setText(capabilitiesInformation.getCapabilityName());
        }
    }
}
