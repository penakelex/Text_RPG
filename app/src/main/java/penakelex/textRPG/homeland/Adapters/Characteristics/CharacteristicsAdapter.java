package penakelex.textRPG.homeland.Adapters.Characteristics;

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
import penakelex.textRPG.homeland.databinding.ItemCharacteristicBinding;


public class CharacteristicsAdapter extends RecyclerView.Adapter<CharacteristicsAdapter.ViewHolder> {
    private List<CharacteristicItem> information = new ArrayList<>();
    private final OnCharacteristicItemClickListener clickListener;
    private int lastPosition = -1;
    private Context context;

    public interface OnCharacteristicItemClickListener {
        void onClickListener(CharacteristicItem characteristic);
    }

    public CharacteristicsAdapter(OnCharacteristicItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CharacteristicsAdapter.ViewHolder(ItemCharacteristicBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false).getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(information.get(position), context);
        holder.itemView.setOnClickListener(listener -> onClicked(position, holder));
    }

    private void onClicked(int position, ViewHolder holder) {
        if (lastPosition != position) {
            clickListener.onClickListener(information.get(position));
            holder.binding.containerForCharacteristicItem.setBackgroundColor(context.getResources().getColor(R.color.gray));
            notifyItemChanged(lastPosition);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context, List<CharacteristicItem> characteristics) {
        this.information = characteristics;
        this.context = context;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCharacteristicBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemCharacteristicBinding.bind(itemView);
        }

        public void bind(CharacteristicItem characteristic, Context context) {
            binding.containerForCharacteristicItem.setBackgroundColor(context.getResources().getColor(R.color.white));
            binding.characteristicName.setText(context.getResources().getString(characteristic.getName()));
            binding.characteristicValue.setText(String.valueOf(characteristic.getValue()));
        }
    }
}