package penakelex.textRPG.homeland.Map.MapFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import penakelex.textRPG.homeland.Map.OnMapClickListener;
import penakelex.textRPG.homeland.databinding.MapFragmentBinding;

public class MapFragment extends Fragment {
    private MapFragmentBinding binding;
    private byte currentLocation = -1;
    private MapHelper mapHelper;
    private final OnMapClickListener clickListener = new OnMapClickListener() {
        @Override
        public void goingWest() {
            if (currentLocation != 1 && currentLocation != 4 && currentLocation != 7) {
                currentLocation--;
                mapHelper.changeImage(currentLocation);
            }
        }

        @Override
        public void goingSouth() {
            if (currentLocation != 1 && currentLocation != 2 && currentLocation != 3) {
                currentLocation -= 3;
                mapHelper.changeImage(currentLocation);
            }
        }

        @Override
        public void goingNorth() {
            if (currentLocation != 7 && currentLocation != 8 && currentLocation != 9) {
                currentLocation += 3;
                mapHelper.changeImage(currentLocation);
            }
        }

        @Override
        public void goingEast() {
            if (currentLocation != 9 && currentLocation != 6 && currentLocation != 3) {
                currentLocation++;
                mapHelper.changeImage(currentLocation);
            }
        }

        @Override
        public void startLocation(byte location) {
            currentLocation = location;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MapFragmentBinding.inflate(inflater, container, false);
        mapHelper = new MapHelper(requireActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapHelper.setBinding(binding);
        mapHelper.changeImage(currentLocation);
        mapHelper.startLocalLocation();
        mapHelper.setClickable();
        buttons();
    }

    private void buttons() {
        binding.button1.setOnClickListener(listener -> mapHelper.firstButton());
        binding.button2.setOnClickListener(listener -> mapHelper.secondButton());
        binding.button3.setOnClickListener(listener -> mapHelper.thirdButton());
        binding.button4.setOnClickListener(listener -> mapHelper.fourthButton());
        binding.button5.setOnClickListener(listener -> mapHelper.fifthButton());
        binding.button6.setOnClickListener(listener -> mapHelper.sixthButton());
        binding.button7.setOnClickListener(listener -> mapHelper.seventhButton());
        binding.button8.setOnClickListener(listener -> mapHelper.eighthButton());
        binding.button9.setOnClickListener(listener -> mapHelper.ninthButton());
    }

    public OnMapClickListener getClickListener() {
        return clickListener;
    }
}
