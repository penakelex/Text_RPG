package penakelex.textRPG.homeland.Trading;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import penakelex.textRPG.homeland.Main.MainActionParentActivity;
import penakelex.textRPG.homeland.R;
import penakelex.textRPG.homeland.Trading.Fragments.PurchaseFragment;
import penakelex.textRPG.homeland.Trading.Fragments.SaleFragment;
import penakelex.textRPG.homeland.databinding.ActivityTradingBinding;

public class TradingActivity extends MainActionParentActivity {
    private ActivityTradingBinding binding;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTradingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toolBar();
        binding.navigationViewForTrading.setOnNavigationItemSelectedListener(listener -> {
            switch (listener.getItemId()) {
                case R.id.sale:
                    getSupportFragmentManager().beginTransaction().replace(binding.containerForTradingFragments.getId(), new PurchaseFragment()).commit();
                    return true;
                case R.id.purchase:
                    getSupportFragmentManager().beginTransaction().replace(binding.containerForTradingFragments.getId(), new SaleFragment()).commit();
                    return true;
            }
            return false;
        });
    }

    private void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        handlingToolBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
