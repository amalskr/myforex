package com.ceylonapz.myforex.viewmodel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.ceylonapz.myforex.R;
import com.ceylonapz.myforex.model.ExchangeRate;
import com.ceylonapz.myforex.model.ForexRate;
import com.ceylonapz.myforex.util.adapters.RateAdapter;
import com.ceylonapz.myforex.util.interfaces.StatusInterface;
import com.ceylonapz.myforex.util.services.ForexApi;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel {

    public ObservableField<String> baseCurrency;
    public ObservableInt historyProgress;
    private RateAdapter adapter;
    private ForexRate forexRate;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Calendar myCalendar;
    private Activity activity;
    public ObservableField<String> datetext;
    private ArrayList<ExchangeRate> exRateList;

    public void init(@NonNull Activity activity) {
        this.activity = activity;
        setupDatePicker();
        adapter = new RateAdapter();
        historyProgress = new ObservableInt(View.GONE);
        baseCurrency = new ObservableField<>(activity.getString(R.string.currency_type));
        datetext = new ObservableField<>("Select a date");
    }

    /*Date Picker*/
    public void openDatePicker() {
        DatePickerDialog datePicker = new DatePickerDialog(activity, dateSetListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePicker.show();
    }

    private void setupDatePicker() {
        myCalendar = Calendar.getInstance();

        dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String selectedDate = DateFormat.format("yyyy-MM-dd", myCalendar).toString();

                datetext.set(selectedDate);
            }

        };
    }
    /*Date Picker End*/

    /*Search History */
    public void searchHistoryNow() {
        String dateText = datetext.get();
        assert dateText != null;
        if (!dateText.equals("Select a date")) {
            resetAdapter();
            callApi(dateText);
        } else {
            Toast.makeText(activity, "Please select a date", Toast.LENGTH_SHORT).show();
        }
    }

    private void callApi(String searchDate) {

        historyProgress.set(View.VISIBLE);

        ForexApi forexApi = ForexApi.getInstance();

        forexApi.getHistorical(searchDate, new StatusInterface() {
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
                System.out.println("latestForexJobj e " + message);

            }
        });
    }

    private void resetAdapter() {
        if (exRateList != null) {
            exRateList.clear();
            adapter.notifyDataSetChanged();
        }

    }

    private void setupExchangeList(ForexRate.RatesBean ratesBean) {

        exRateList = new ArrayList<>();

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

        stopProgressView();

        if (exRateList.size() > 0) {
            adapter.setItemList(exRateList);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(activity, "No Results...!", Toast.LENGTH_SHORT).show();
        }

    }

    public RateAdapter getAdapter() {
        return adapter;
    }

    private void stopProgressView() {
        historyProgress.set(View.GONE);
    }

    /*Search History End*/

}
