package com.ceylonapz.myforex.view.ui;

import android.os.Bundle;
import android.view.MenuItem;

import com.ceylonapz.myforex.R;
import com.ceylonapz.myforex.databinding.ActivityHistoryBinding;
import com.ceylonapz.myforex.viewmodel.HistoryViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setSubtitle("History");
    }

    private void initDataBinding(Bundle savedInstanceState) {
        ActivityHistoryBinding historyBinding = DataBindingUtil.setContentView(this, R.layout.activity_history);

        HistoryViewModel historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        if (savedInstanceState == null) {
            historyViewModel.init();
        }
        historyBinding.setHistoryVm(historyViewModel);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
