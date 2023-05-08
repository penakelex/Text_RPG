package penakelex.textRPG.homeland.Adapters.Characteristics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabase;
import penakelex.textRPG.homeland.Databases.CharacteristicsDatabase.CharacteristicsDatabaseHelper;
import penakelex.textRPG.homeland.TopPanel.Person.Fragments.CharacteristicsFragment;
import penakelex.textRPG.homeland.databinding.ItemCharacteristicBinding;


public class CharacteristicsAdapter extends RecyclerView.Adapter<CharacteristicsAdapter.ViewHolder> {
    private ArrayList<CharacteristicInformation> information = new ArrayList<>();
    private final OnCharacteristicItemClickListener clickListener;
    public interface OnCharacteristicItemClickListener {
        void onClickListener(String name, int position);
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
        holder.bind(information.get(position));
        holder.itemView.setOnClickListener(listener -> clickListener.onClickListener(information.get(position).getName(), position));
    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context) {
        this.information = CharacteristicsDatabase.getCharacteristicsInformation(new CharacteristicsDatabaseHelper(context).getReadableDatabase());
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemCharacteristicBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemCharacteristicBinding.bind(itemView);
        }

        public void bind(CharacteristicInformation characteristicInformation) {
            binding.characteristicName.setText(characteristicInformation.getName());
            binding.characteristicValue.setText(String.valueOf(characteristicInformation.getValue()));
        }
    }
}
