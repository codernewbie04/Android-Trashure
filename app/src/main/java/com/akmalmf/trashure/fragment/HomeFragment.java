package com.akmalmf.trashure.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.akmalmf.trashure.R;
import com.akmalmf.trashure.utils.PrefManager;
import com.akmalmf.trashure.utils.Utility;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    PrefManager userData;
    TextView saldo;
    Context mContext;
    AnyChartView anyChartView;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        startInit(root);
        return root;
    }

    private void startInit(View root) {
        initUI(root);
        initValue();
        initEvent();
    }

    private void initUI(View v) {
        mContext = v.getContext();
        userData = new PrefManager(mContext);
        saldo = v.findViewById(R.id.saldo);

        anyChartView = v.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(v.findViewById(R.id.progress_bar));

    }

    private void initValue() {
        Utility.currencyTXT(saldo, userData.getSaldo(), mContext);
    }

    private void initEvent() {
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Jan", 10));
        data.add(new ValueDataEntry("Feb", 15));
        data.add(new ValueDataEntry("Mar", 6));
        data.add(new ValueDataEntry("Apr", 21));
        data.add(new ValueDataEntry("Mei", 0));
        data.add(new ValueDataEntry("Jun", 0));
        data.add(new ValueDataEntry("Jul", 0));
        data.add(new ValueDataEntry("Agu", 0));
        data.add(new ValueDataEntry("Sep", 0));
        data.add(new ValueDataEntry("Okt", 0));
        data.add(new ValueDataEntry("Nov", 0));
        data.add(new ValueDataEntry("Des", 0));

        Column column = cartesian.column(data);

        column.color("red");

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("{%Value}{groupsSeparator: } Kg");

        cartesian.animation(true);


        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: } Kg");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        anyChartView.setChart(cartesian);

    }
}