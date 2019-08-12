package com.example.vehicle_mounted;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import com.github.mikephil.charting.charts.LineChart;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import jdk.nashorn.internal.runtime.regexp.joni.WarnCallback;

public class LineChartActivity extends Fragment {
	private LineChartActivity lca;
	protected int normalImage; //正常时的图片
	protected int warningImage; //失常时的图片
	protected ImageView iconImage;
	protected TextView week; //周期选择提示
	protected LineChart chart; //图表
	protected DatePickerDialog dateDialog; //周期选择框
	protected String descriptionName; //图表描述字段
	protected TextView  avgV; //平均值
	protected TextView maxV; //最大值显示
	protected TextView minV; //最小值显示
	protected TextView warnningTextView; //字提醒
	protected int lineH;  //最高警戒线
	protected int lineL; //最低警戒线
	protected int min; //纵坐标最小值
	protected int max;  //纵坐标最大值
	
	protected ImageView back;
	// 获取日期格式器对象
	protected Calendar calendar = Calendar.getInstance(Locale.CHINA);
	// 图表横坐标刻度
	protected String[] dayX = new String[24]; // 在init中通过循环进行初始化
	protected String[] weekX = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
	// 图表数据
	protected int[] weekValues = { 68, 110, 130, 89, 76, 88, 65 };
	protected int[] dayValues = { 68, 110, 130, 89, 76, 88, 65, 88, 76, 90, 98, 56, 77, 130, 120, 110, 125, 89, 89, 60,
			67, 77, 88, 99 };
	
	public LineChartActivity() {
		// 循环初始化dayX
		for (int i = 0; i < 24; i++)
			dayX[i] = i + ":00";
		
		//创建datadialog
	}
	
	
	/**
	 * 构建日期选择器
	 */
	protected void makeDateDialog() {
		dateDialog = new DatePickerDialog(lca.getActivity(), new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int month, int day) {
				// 回调方法，计算周数
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DAY_OF_MONTH, day);
				
				//进行统计判断
//				switch (FunctionSetting.getCountMethod()) {
//					case FunctionSetting.DAY_COUNT:
//						int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
//						week.setText("第" + weekNumber + "周");
//						break;
//					case FunctionSetting.WEEK_COUNT:
//						int dayNumber = calendar.get(Calendar.DATE);
//						week.setText("第" + dayNumber + "天");
//						break;
//	
//					default:
//						break;
//				}
				if(calendar.before(Calendar.getInstance())) {
					int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
					week.setText("第" + weekNumber + "周");
					makeDate();
					
				}else {
					warnTimeToLate();
				}
				
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	protected void makeChart() {
		//进行统计判断
		chart.setDescription(descriptionName);
		MakeChart.makeChart(chart,min,max,lineL,lineH ,weekX, DataChange.intToStringArr(weekValues));
//		switch (FunctionSetting.getCountMethod()) {
//			case FunctionSetting.DAY_COUNT:		
//				// 设置标题
//				chart.setDescription(descriptionName);
//				chart.setDescriptionTextSize(12f);
//				
//				MakeChart.makeChart(chart,min,max,lineL,lineH ,dayX, DataChange.floatToStringArr(dayValues));
//				break;
//			case FunctionSetting.WEEK_COUNT:
//				MakeChart.makeChart(chart,min,max,lineL,lineH ,weekX, DataChange.floatToStringArr(weekValues));
//				break;
//
//			default:
//				break;
//		}
	}
	
    protected void warnTimeToLate() {
    	
    };
    
    protected void makeDate() {
    	//动态生成数据
		int total=0;
		for (int i = 0; i < weekValues.length; i++) {
			weekValues[i] = (int) (Math.random() * (max - min) + min);
			total+=weekValues[i];
		}
		//绘图
		makeChart();
		//排序
		Arrays.sort(weekValues);
//		DecimalFormat format=new DecimalFormat(".0");
		maxV.setText(String.valueOf(weekValues[weekValues.length-1]));
		minV.setText(String.valueOf(weekValues[0]));
		int avg=(int)(total/weekValues.length);
		avgV.setText(String.valueOf(avg));
		/**
		 * 文字报警提示
		 */
		if(avg>lineH) {
			warnningTextView.setTextColor(Color.RED);
			warnningTextView.setText("过高");
			iconImage.setImageResource(warningImage);
		}else if(avg<lineL){
			warnningTextView.setTextColor(Color.RED);
			warnningTextView.setText("过低");
			iconImage.setImageResource(warningImage);
		}else {
			warnningTextView.setTextColor(Color.WHITE);
			warnningTextView.setText("正常");
			iconImage.setImageResource(normalImage);
		}
    }

}
