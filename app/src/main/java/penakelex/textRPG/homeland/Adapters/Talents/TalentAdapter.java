package penakelex.textRPG.homeland.Adapters.Talents;

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

public class TalentAdapter extends RecyclerView.Adapter<TalentAdapter.ViewHolder> {
    private ArrayList<TalentInformation> information;

    @SuppressLint("NotifyDataSetChanged")
    public void setInformation(Context context) {
        this.information = TalentsDatabase.getHavingTalents(context);
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
        public void bind(TalentInformation talentInformation) {
            binding.capabilityName.setText(talentInformation.getTalentName());
            switch (talentInformation.getTalentID()) {
                case 1:
                    binding.capabilityDescription.setText("У Вас от природы красивейший голос, за счёт чего Вы становитесь более привлекательными для окружающих.");
                    binding.capabilityMeaning.setText("Привлекательность: +1");
                    break;
                case 2:
                    binding.capabilityDescription.setText("Как-то так сложилось, что Вы здорово выросли в детстве, так ещё и накачались...");
                    binding.capabilityMeaning.setText("Сила: +1, Телосложение: +1, Очки действия: -2");
                    break;
                case 3:
                    binding.capabilityDescription.setText("Бедная подушка, зато есть набитый удар.");
                    binding.capabilityMeaning.setText("Урон оружием ближнего боя: +1, Критические урон: -5%");
                    break;
                case 4:
                    binding.capabilityDescription.setText("Вы уже прошли немало испытаний по жизни, что сильно отразилось на Ваших возможностях.");
                    binding.capabilityMeaning.setText("Все навыки: +10%");
                    break;
                case 5:
                    binding.capabilityDescription.setText("Самосовершенствование - это Ваше качество.");
                    binding.capabilityMeaning.setText("Все характеристики: +1, Все навыки% -10%");
                    break;
                case 6:
                    binding.capabilityDescription.setText("Донести пакеты с магазина - легкотня!");
                    binding.capabilityMeaning.setText("Максимальная грузоподъёмность: +20");
                    break;
                case 7:
                    binding.capabilityDescription.setText("Вас воспитали быть добрым и порядочным, и Вы были того же мнения.");
                    binding.capabilityMeaning.setText("Общение, Медицина, Ремонт, Наука, Бартер: +5, Лёгкое оружие, Тяжёлое оружие, Оружие ближнего боя: -5");
                    break;
            }
        }
    }
}
