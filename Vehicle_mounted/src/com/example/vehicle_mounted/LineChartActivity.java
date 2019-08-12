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
	protected int normalImage; //����ʱ��ͼƬ
	protected int warningImage; //ʧ��ʱ��ͼƬ
	protected ImageView iconImage;
	protected TextView week; //����ѡ����ʾ
	protected LineChart chart; //ͼ��
	protected DatePickerDialog dateDialog; //����ѡ���
	protected String descriptionName; //ͼ�������ֶ�
	protected TextView  avgV; //ƽ��ֵ
	protected TextView maxV; //���ֵ��ʾ
	protected TextView minV; //��Сֵ��ʾ
	protected TextView warnningTextView; //������
	protected int lineH;  //��߾�����
	protected int lineL; //��;�����
	protected int min; //��������Сֵ
	protected int max;  //���������ֵ
	
	protected ImageView back;
	// ��ȡ���ڸ�ʽ������
	protected Calendar calendar = Calendar.getInstance(Locale.CHINA);
	// ͼ�������̶�
	protected String[] dayX = new String[24]; // ��init��ͨ��ѭ�����г�ʼ��
	protected String[] weekX = { "����һ", "���ڶ�", "������", "������", "������", "������", "������" };
	// ͼ������
	protected int[] weekValues = { 68, 110, 130, 89, 76, 88, 65 };
	protected int[] dayValues = { 68, 110, 130, 89, 76, 88, 65, 88, 76, 90, 98, 56, 77, 130, 120, 110, 125, 89, 89, 60,
			67, 77, 88, 99 };
	
	public LineChartActivity() {
		// ѭ����ʼ��dayX
		for (int i = 0; i < 24; i++)
			dayX[i] = i + ":00";
		
		//����datadialog
	}
	
	
	/**
	 * ��������ѡ����
	 */
	protected void makeDateDialog() {
		dateDialog = new DatePickerDialog(lca.getActivity(), new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int month, int day) {
				// �ص���������������
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DAY_OF_MONTH, day);
				
				//����ͳ���ж�
//				switch (FunctionSetting.getCountMethod()) {
//					case FunctionSetting.DAY_COUNT:
//						int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
//						week.setText("��" + weekNumber + "��");
//						break;
//					case FunctionSetting.WEEK_COUNT:
//						int dayNumber = calendar.get(Calendar.DATE);
//						week.setText("��" + dayNumber + "��");
//						break;
//	
//					default:
//						break;
//				}
				if(calendar.before(Calendar.getInstance())) {
					int weekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
					week.setText("��" + weekNumber + "��");
					makeDate();
					
				}else {
					warnTimeToLate();
				}
				
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	protected void makeChart() {
		//����ͳ���ж�
		chart.setDescription(descriptionName);
		MakeChart.makeChart(chart,min,max,lineL,lineH ,weekX, DataChange.intToStringArr(weekValues));
//		switch (FunctionSetting.getCountMethod()) {
//			case FunctionSetting.DAY_COUNT:		
//				// ���ñ���
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
    	//��̬��������
		int total=0;
		for (int i = 0; i < weekValues.length; i++) {
			weekValues[i] = (int) (Math.random() * (max - min) + min);
			total+=weekValues[i];
		}
		//��ͼ
		makeChart();
		//����
		Arrays.sort(weekValues);
//		DecimalFormat format=new DecimalFormat(".0");
		maxV.setText(String.valueOf(weekValues[weekValues.length-1]));
		minV.setText(String.valueOf(weekValues[0]));
		int avg=(int)(total/weekValues.length);
		avgV.setText(String.valueOf(avg));
		/**
		 * ���ֱ�����ʾ
		 */
		if(avg>lineH) {
			warnningTextView.setTextColor(Color.RED);
			warnningTextView.setText("����");
			iconImage.setImageResource(warningImage);
		}else if(avg<lineL){
			warnningTextView.setTextColor(Color.RED);
			warnningTextView.setText("����");
			iconImage.setImageResource(warningImage);
		}else {
			warnningTextView.setTextColor(Color.WHITE);
			warnningTextView.setText("����");
			iconImage.setImageResource(normalImage);
		}
    }

}
