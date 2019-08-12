package com.example.vehicle_mounted;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import android.R.integer;
import android.graphics.Color;

public class MakeChart {
	public static void makeChart(LineChart chart,int min,int max,int lineL,int lineH, String x[], String y[]) {
		// 设置不可滑动触摸
		chart.setTouchEnabled(false);
		chart.setDragEnabled(false);
		chart.setScaleEnabled(false);
		chart.setDrawBorders(false);
		// 设置坐标轴的显示状态
		chart.getAxisLeft().setEnabled(false);
		chart.getAxisRight().setEnabled(false);
		chart.getXAxis().setEnabled(true);
		chart.getXAxis().setDrawGridLines(false);
		// 设置X,Y轴字体大小
		chart.getAxisLeft().setTextSize(10f);
		chart.getXAxis().setTextSize(10f);
		// 设置Y坐标起始刻度不为0，并设置最大值与最小值
		chart.getAxisLeft().setStartAtZero(false);
		chart.getAxisLeft().setAxisMinValue(min);
		chart.getAxisLeft().setAxisMaxValue(max);
		// 设置背景色
		chart.setBackgroundColor(Color.TRANSPARENT);
		// 设置x轴刻度位于底部
		chart.getXAxis().setPosition(XAxisPosition.BOTTOM);
		// 设置警戒线
		LimitLine lineHH = new LimitLine(lineH, "高于正常人");
		LimitLine lineLL = new LimitLine(lineL, "低于正常人");
		lineHH.setTextSize(12f);
		lineLL.setTextSize(12f);
		chart.getAxisLeft().addLimitLine(lineHH);
		chart.getAxisLeft().addLimitLine(lineLL);

		LineData data = getData(x, y);
		chart.setData(data);
		chart.animateX(3500);// 动画
	}

	private static LineData getData(String x[], String y[]) {
		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<Entry> yVals = new ArrayList<Entry>();
		for (int i = 0; i < x.length; i++) {
			xVals.add(x[i]);
		}
		for (int i = 0; i < y.length; i++) {
			yVals.add(new Entry(Float.parseFloat(y[i]), i));
		}
		LineDataSet dataSet = new LineDataSet(yVals, "");
		dataSet.setDrawCubic(false);
		dataSet.setDrawCircles(true);
		dataSet.setLineWidth(2f);
		dataSet.setDrawFilled(false);
		dataSet.setCircleSize(5f); // 设置小圆的大小
		dataSet.setDrawValues(false);
		dataSet.setDrawCircleHole(false);
		dataSet.setDrawValues(true);
		dataSet.setDrawFilled(true);
		LineData data = new LineData(xVals, dataSet);
		return data;

	}
}
