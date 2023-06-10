package penakelex.textRPG.homeland.Adapters.StartingCharacteristics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import penakelex.textRPG.homeland.Databases.Tables.CharacteristicsDatabase.CharacteristicItem;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ItemStartingCharacteristicBinding;


public class StartingCharacteristicsAdapter extends RecyclerView.Adapter<StartingCharacteristicsAdapter.ViewHolder> {
    private List<CharacteristicItem> information = new ArrayList<>();
    private final OnStartingCharacteristicItemClickListener listener;
    private int lastPosition = -1;
    private Context context;

    public StartingCharacteristicsAdapter(OnStartingCharacteristicItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnStartingCharacteristicItemClickListener {
        void onClickListener(CharacteristicItem item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StartingCharacteristicsAdapter.ViewHolder(ItemStartingCharacteristicBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position), context, position, lastPosition);
        holder.itemView.setOnClickListener(l -> onClicked(holder, position));
    }

    private void onClicked(ViewHolder holder, int position) {
        if (lastPosition != position) {
            listener.onClickListener(information.get(position));
            holder.binding.containerForStartingCharacteristicItem.setBackgroundColor(context.getResources().getColor(R.color.gray));
            notifyItemChanged(lastPosition);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(List<CharacteristicItem> characteristicItems, Context context) {
        this.information = characteristicItems;
        this.context = context;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemStartingCharacteristicBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemStartingCharacteristicBinding.bind(itemView);
        }

        public void bind(CharacteristicItem item, Context context, int position, int lastPosition) {
            if (position == lastPosition) {
                binding.containerForStartingCharacteristicItem.setBackgroundColor(context.getResources().getColor(R.color.gray));
            } else {
                binding.containerForStartingCharacteristicItem.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
            binding.characteristicName.setText(context.getResources().getString(item.getName()));
            binding.characteristicValue.setText(String.valueOf(item.getValue()));
        }
    }
}
