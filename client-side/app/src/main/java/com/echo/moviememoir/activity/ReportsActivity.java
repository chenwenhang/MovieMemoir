package com.echo.moviememoir.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.echo.moviememoir.R;
import com.echo.moviememoir.entity.User;
import com.echo.moviememoir.restful.RestClient;
import com.echo.moviememoir.utils.LocalStorage;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.edittext.materialedittext.MaterialEditText;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportsActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {
    private TitleBar titleBar;
    private MaterialEditText startDate;
    private MaterialEditText endDate;
    private Button pieConfirm;
    private PieChart mPieChart;
    private MaterialSpinner yearSpinner;
    private BarChart mBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Strict mode, since new thread must be created after android4.0 if we want to create a request
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_reports);
        initView();
//        initData();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        final User user = LocalStorage.getUser();
        titleBar = findViewById(R.id.reports_title_bar);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        yearSpinner = findViewById(R.id.report_year);
        mBarChart = findViewById(R.id.bar_chart);

        mPieChart = (PieChart) findViewById(R.id.pie_chart);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);
        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        mPieChart.setCenterText("Cinema postcode percentage");
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);
        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);
        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);
        mPieChart.setDrawCenterText(true);
        mPieChart.setRotationAngle(0);
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);
        mPieChart.setOnChartValueSelectedListener(this);

        startDate = findViewById(R.id.report_start);
        endDate = findViewById(R.id.report_end);
        startDate.setFocusable(false);
        endDate.setFocusable(false);
        startDate.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    hideInput();
                    switch (view.getId()) {
                        case R.id.report_start:
                            showDatePickDialog(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    startDate.setText(year + "-" + (month + 1) + "-" + day);
                                }
                            }, startDate.getText().toString());
                            break;
                    }
                }
                return false;
            }
        });
        endDate.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    hideInput();
                    switch (view.getId()) {
                        case R.id.report_end:
                            showDatePickDialog(new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    endDate.setText(year + "-" + (month + 1) + "-" + day);
                                }
                            }, endDate.getText().toString());
                            break;
                    }
                }
                return false;
            }
        });
        pieConfirm = findViewById(R.id.report_pie_confirm);
        pieConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startString = startDate.getText().toString().trim();
                String endString = endDate.getText().toString().trim();
                if (!startString.equals("")
                        && !endString.equals("")) {
                    String str = RestClient.findNumByUserIdDuringPeriod(user.getUserId(), startString, endString);
                    JSONArray res = null;
                    List<PieEntry> entries = new ArrayList<PieEntry>();
                    try {
                        res = new JSONArray(str);
                        for (int i = 0; i < res.length(); i++) {
                            PieEntry entry = new PieEntry(Integer.parseInt((String) res.getJSONObject(i).get("total")), (String) res.getJSONObject(i).get("postcode"));
                            entries.add(entry);
                        }
                        initPieChartData(entries);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        List<String> items = new ArrayList<>();
        items.add("2015");
        items.add("2016");
        items.add("2017");
        items.add("2018");
        items.add("2019");
        items.add("2020");
        yearSpinner.setItems(items);
        yearSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                String str = RestClient.findNumPerMonthByUserIdYear(user.getUserId(), (String) item);
                JSONArray res = null;
                List<BarEntry> entries = new ArrayList<BarEntry>();
                try {
                    res = new JSONArray(str);
                    for (int i = 0; i < res.length(); i++) {
                        BarEntry entry = new BarEntry(Integer.parseInt((String) res.getJSONObject(i).get("month")), Integer.parseInt((String) res.getJSONObject(i).get("count")));
                        entries.add(entry);
                    }
                    initBarChartData(entries);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initBarChartData(List<BarEntry> entries) {
        BarDataSet barDataSet = new BarDataSet(entries, "");
        BarData barData = new BarData(barDataSet);
        mBarChart.setData(barData);

        mBarChart.getDescription().setEnabled(false);
        mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.getAxisRight().setEnabled(false);
    }

    private void initPieChartData(List<PieEntry> entries) {
        setPieChartData(entries);
        mPieChart.animateY(1400, Easing.EaseInOutQuad);
//        Legend l = mPieChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setXEntrySpace(0f);
//        l.setYEntrySpace(0f);
//        l.setYOffset(0f);
        mPieChart.setDrawEntryLabels(true);
        mPieChart.setEntryLabelColor(Color.WHITE);
        mPieChart.setEntryLabelTextSize(12f);

    }

    private void setPieChartData(List<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "Percentage");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        mPieChart.invalidate();

    }

    public void showDatePickDialog(DatePickerDialog.OnDateSetListener listener, String curDate) {
        Calendar calendar = Calendar.getInstance();
        int year = 0, month = 0, day = 0;
        try {
            year = Integer.parseInt(curDate.substring(0, curDate.indexOf("-")));
            month = Integer.parseInt(curDate.substring(curDate.indexOf("-") + 1, curDate.lastIndexOf("-"))) - 1;
            day = Integer.parseInt(curDate.substring(curDate.lastIndexOf("-") + 1, curDate.length()));
        } catch (Exception e) {
            e.printStackTrace();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_LIGHT, listener, year, month, day);
        datePickerDialog.show();
    }


    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
