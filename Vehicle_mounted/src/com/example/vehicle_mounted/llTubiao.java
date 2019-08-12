package com.example.vehicle_mounted;

import java.util.Calendar;

import com.github.mikephil.charting.charts.LineChart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class llTubiao extends LineChartActivity implements OnClickListener {
	private llTubiao tubiao;
	TextView tv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = (View) inflater.inflate(R.layout.lltb, container,false);
		tv = (TextView) view.findViewById(R.id.lltb_tv);
		tv.setText("");
		return view;
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void init() {
		normalImage=R.drawable.heart_beat_w;
		warningImage=R.drawable.heart_beat_warning;
		iconImage=(ImageView)findViewById(R.id.heart_beat_icon);
		warnningTextView=(TextView)findViewById(R.id.heartBeatWarning);
		week = (TextView) findViewById(R.id.heartBeatView_week);
		week.setOnClickListener(this);
		avgV=(TextView)findViewById(R.id.heartBeatViewAvgV);
		minV=(TextView)findViewById(R.id.heartBeatViewMinV);
		maxV=(TextView)findViewById(R.id.heartBeatViewMaxV);
		chart = (LineChart) findViewById(R.id.heartBeatChart_main);
		back=(ImageView)findViewById(R.id.heartBeatBack);
		back.setOnClickListener(this);
		
		/**
		 * ��ʼ��week
		 */
		Calendar calendar=Calendar.getInstance();
		week.setText("��"+calendar.get(Calendar.WEEK_OF_YEAR)+"��");
		
		// ����ͼ��
		descriptionName = "����";
		lineL = 60;
		lineH = 100;
		min = 30;	
		max = 150;
		makeDate();
		makeDateDialog();
		//�Զ�����
//		Timer timer=new Timer();
//		timer.schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				
//			}
//		}, 0, 1000*60*60*24);

	}
	
	//����datePick��ʱ����
	@Override
	protected void warnTimeToLate() {
		// TODO Auto-generated method stub
		Toast.makeText(tubiao.getActivity(), "���޼�¼", Toast.LENGTH_LONG).show();
	}

	// ѡ���Թ���ͼ��(��/��)

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		 switch (view.getId()) {
			 case R.id.heartBeatView_week: {
				 dateDialog.show();
			 } break;
		 }
	}
}
