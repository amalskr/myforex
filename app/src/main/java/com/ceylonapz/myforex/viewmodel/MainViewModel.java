package com.ceylonapz.myforex.viewmodel;

import android.util.Log;
import android.view.View;

import com.ceylonapz.myforex.R;
import com.ceylonapz.myforex.model.ExchangeRate;
import com.ceylonapz.myforex.model.ForexRate;
import com.ceylonapz.myforex.util.interfaces.StatusInterface;
import com.ceylonapz.myforex.util.services.ForexApi;
import com.ceylonapz.myforex.view.adapters.RateAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    public ObservableField<String> baseCurrency;
    public ObservableInt mainProgress;
    private RateAdapter adapter;
    private ForexRate forexRate;

    public void init() {
        adapter = new RateAdapter();
        mainProgress = new ObservableInt(View.GONE);
        baseCurrency = new ObservableField<>();

        callApi();
    }


    private void callApi() {

        mainProgress.set(View.VISIBLE);

        ForexApi forexApi = ForexApi.getInstance();
        forexApi.getLatest(new StatusInterface() {
            @Override
            public void success(JsonObject jsonObject) {

                forexRate = new Gson().fromJson(jsonObject, ForexRate.class);

                //SET BASE CURRENCY NAME
                baseCurrency.set(forexRate.getBase());

                //GET AND SET RATE LIST
                ForexRate.RatesBean ratesBean = forexRate.getRates();
                setupExchangeList(ratesBean);
            }

            @Override
            public void fail(String message) {
                stopProgressView();
                Log.e("MAIN-CallApi", message);

            }
        });
    }


    private void setupExchangeList(ForexRate.RatesBean ratesBean) {

        List<ExchangeRate> exRateList = new ArrayList<>();
        exRateList.add(new ExchangeRate("AUD", ratesBean.getAUD()));
        exRateList.add(new ExchangeRate("BGN", ratesBean.getBGN()));
        exRateList.add(new ExchangeRate("CAD", ratesBean.getCAD()));
        exRateList.add(new ExchangeRate("DKK", ratesBean.getDKK()));
        exRateList.add(new ExchangeRate("GBP", ratesBean.getGBP()));
        exRateList.add(new ExchangeRate("HKD", ratesBean.getHKD()));
        exRateList.add(new ExchangeRate("MYR", ratesBean.getMYR()));
        exRateList.add(new ExchangeRate("INR", ratesBean.getINR()));
        exRateList.add(new ExchangeRate("JPY", ratesBean.getJPY()));
        exRateList.add(new ExchangeRate("KRW", ratesBean.getKRW()));
        exRateList.add(new ExchangeRate("SGD", ratesBean.getSGD()));
        exRateList.add(new ExchangeRate("TRY", ratesBean.getTRY()));
        exRateList.add(new ExchangeRate("USD", ratesBean.getUSD()));
        exRateList.add(new ExchangeRate("ZAR", ratesBean.getZAR()));

        MutableLiveData<List<ExchangeRate>> liveDataExRateList = new MutableLiveData<>();
        liveDataExRateList.postValue(exRateList);

        stopProgressView();

        if (exRateList.size() > 0) {
            adapter.setItemList(exRateList);
            adapter.notifyDataSetChanged();
        }
    }

    public RateAdapter getAdapter() {
        return adapter;
    }

    private void stopProgressView() {
        mainProgress.set(View.GONE);
    }

}
