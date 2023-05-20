package penakelex.textRPG.homeland.Adapters.StartingCharacteristics;

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
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.databinding.ItemStartingCharacteristicBinding;


public class StartingCharacteristicsAdapter extends RecyclerView.Adapter<StartingCharacteristicsAdapter.ViewHolder> {
    private ArrayList<StartingCharacteristicInformation> information;
    private final OnStartingCharacteristicItemClickListener listener;
    private int lastPosition = -1;
    private Context context;

    public StartingCharacteristicsAdapter(OnStartingCharacteristicItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnStartingCharacteristicItemClickListener {
        void onClickListener(String name, int position);
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
            listener.onClickListener(information.get(position).getName(), position);
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
    public void setInformation(Context context) {
        this.information = CharacteristicsDatabase.getStartingCharacteristicsInformation(new CharacteristicsDatabaseHelper(context).getReadableDatabase());
        this.context = context;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemStartingCharacteristicBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemStartingCharacteristicBinding.bind(itemView);
        }

        public void bind(StartingCharacteristicInformation startingCharacteristicInformation, Context context, int position, int lastPosition) {
            if (position == lastPosition)
                binding.containerForStartingCharacteristicItem.setBackgroundColor(context.getResources().getColor(R.color.gray));
            else
                binding.containerForStartingCharacteristicItem.setBackgroundColor(context.getResources().getColor(R.color.white));
            binding.characteristicName.setText(startingCharacteristicInformation.getName());
            binding.characteristicValue.setText(String.valueOf(startingCharacteristicInformation.getValue()));
        }
    }
}
