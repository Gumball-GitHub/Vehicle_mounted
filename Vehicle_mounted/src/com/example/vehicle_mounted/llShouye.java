package com.example.vehicle_mounted;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class llShouye extends Fragment implements OnInitListener {

	private llShouye shouye;
	private ListView msgListView;
	private List<Msg> msgList = new ArrayList<Msg>(),msgList2 = new ArrayList<Msg>();
	private MsgAdapter adapter;
	TextToSpeech tts;
	private String yuYin = "",shuJu = "";
	private Handler mHandler;
	private boolean doTts = true, hujiuflag = true;
	private String time, weight, temperature, heart, hpressure, lpressure, fat;
	TextView phone;
	private String hujiu = "关闭自动呼救";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.llshouye, container, false);
		shouye = this;
		phone = (TextView) view.findViewById(R.id.llgr_phone);
		mHandler = new Handler();
		Button button = (Button) view.findViewById(R.id.llsy_btn),
				button2 = (Button) view.findViewById(R.id.llsy_btn2);
		Drawable drawable1 = getResources().getDrawable(R.drawable.speaker);
		drawable1.setBounds(0, 0, 40, 40);
		button.setCompoundDrawables(drawable1, null, null, null);
		tts = new TextToSpeech(this.getActivity(), this);
		mHandler.post(new Runnable() {
			@Override
			public synchronized void run() {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
						"yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
				// 获取当前时间
				Date date = new Date(System.currentTimeMillis());
				time = simpleDateFormat.format(date);
				Random rand = new Random();
				DecimalFormat df=new DecimalFormat("0.00");
				weight = rand.nextInt(600) / 10.0 + 40 + "";
				temperature = rand.nextInt(20) / 10.0 + 36 + ""; 
				heart = rand.nextInt(40) + 60 + ""; 
				hpressure = rand.nextInt(120) + 1+ ""; 
				lpressure = rand.nextInt(80) + 1 + ""; 
				fat = df.format(rand.nextInt(114) / 100 + 0.56 );
				msgList.clear();
				initMsg(time, weight, temperature, heart, hpressure, lpressure, fat);
				if(hujiuflag==true&&(Integer.parseInt(heart)<60||Integer.parseInt(heart)>100)){callPhone();}
				for (Msg m : msgList) {
					shuJu += (m.content + "\n");
				}
				saveData();
				shuJu = "";
				for (Msg m : msgList) {
					yuYin += (m.content + "." + m.suggestion);
				}
				if(doTts==true){startTts();}
				yuYin = "";
				msgList2.addAll(msgList);
				adapter.notifyDataSetChanged();
				mHandler.postDelayed(this, 120000);

			}
		});
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doTts = !doTts;
				if(doTts==true){
					Toast.makeText(shouye.getActivity(), "语音播报已打开", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(shouye.getActivity(), "语音播报已关闭", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
		      AlertDialog.Builder dialog = new AlertDialog.
		              Builder(shouye.getActivity());
		      dialog.setTitle("紧急呼救");//对话框最上面的字
		      dialog.setMessage("请选择：");//对话框中部的字
		      dialog.setCancelable(false);//这里设置为false，按退出键不能退出，这个地方默认为true
		//以下为对话框最下面的选择项
		      dialog.setPositiveButton("取消", new DialogInterface.
		              OnClickListener() {
		          @Override
		        public void onClick(DialogInterface dialog, int which) {
		        	  Toast.makeText(shouye.getActivity(), "取消", Toast.LENGTH_SHORT).show();
		        }

		    });
		      dialog.setNegativeButton("立即呼救", new DialogInterface.
		              OnClickListener() {
		          @Override
		        public void onClick(DialogInterface dialog, int which) {
		        	  callPhone();
		        }

		    });
		      dialog.setNeutralButton(hujiu, new DialogInterface.
		              OnClickListener() {
		          @Override
		        public void onClick(DialogInterface dialog, int which) {
		        	  if(hujiu.equals("关闭自动呼救")){
			        	  hujiu = "打开自动呼救";
			        	  Toast.makeText(shouye.getActivity(), "自动呼救已关闭", Toast.LENGTH_SHORT).show();
			        	  hujiuflag = false;
		        	  }else{
		        		  hujiu = "关闭自动呼救"; 
		        		  Toast.makeText(shouye.getActivity(), "自动呼救已打开", Toast.LENGTH_SHORT).show();
		        		  hujiuflag = true;
		        	  }
		        }
		    });
		      dialog.show();  
		  }
				
			
		});

		adapter = new MsgAdapter(this.getActivity(), R.layout.llshouye2,
				msgList2);
		msgListView = (ListView) view.findViewById(R.id.lv_msg);
		msgListView.setAdapter(adapter);
		return view;
	}

	public class Msg {

		private String content = "";// 信息的内容
		private String suggestion;

		public Msg(String content, String msg) {

			switch (content) {
			case "时间：":
				this.content = msg;
				suggestion = "";
				break;
			case "体重：":
				this.content = "您的" + content + msg + "千克";
				double bmi = 0;
				String yiyi = "";
				DecimalFormat df = new DecimalFormat("#.00");
				try {
					bmi = Integer.parseInt(FileStream.readInternal(getActivity(), "userheight"));
					bmi = Double.parseDouble(msg)/(bmi*bmi)*10000;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(bmi<18.4){yiyi = "过轻";}
				else if(bmi<24){yiyi = "适中";}
				else if(bmi<28){yiyi = "超重";}
				else if(bmi<35){yiyi = "肥胖";}
				else{yiyi = "非常肥胖";}
				
				suggestion = "您的体重指数为"+df.format(bmi)+","+yiyi+".";
				break;
			case "体温：":
				this.content = "您的" + content + msg + "摄氏度";
				String yiyi1 = "";
				double tiwen = 0;
				tiwen = Double.parseDouble(msg);
				if(tiwen<37.2){yiyi1="正常";}
				else if(tiwen<38){yiyi1="低热";}
				else if(tiwen<39){yiyi1="中等度发热";}
				else if(tiwen<41){yiyi1="高热";}
				else{yiyi1="超高热";}
				suggestion = "您的体温："+yiyi1+".";
				break;
			case "心率：":
				this.content = "您的" + content + msg + "次每分钟";
				suggestion = "健康成年人安静状态下，心率平均每分钟75次.";
				break;
			case "收缩血压：":
				this.content = "您的" + content + msg + "毫米汞柱";
				suggestion = "正常血压：收缩压<120mmHg和舒张压<80mmHg。";
				break;
			case "舒张血压：":
				this.content = "您的" + content + msg + "毫米汞柱";
				suggestion = "正常血压：收缩压<120mmHg和舒张压<80mmHg。";
				break;
			case "甘油三酯：":
				this.content = "您的" + content + msg + "毫摩尔每升";
				suggestion = "总胆固醇正常值：＜5.2毫摩尔每升，增高提示：动脉硬化、高脂血症等，降低提示：甲亢、贫血、感染等。";
				break;
			default:
				break; 
			}
		}

		public String getContent() {
			return content;
		}

		public String getSuggestion() {
			return suggestion;

		}
	}

	private void initMsg(String time, String weight, String temperature,
			String heart, String hpressure, String lpressure, String fat) {
		Msg msg = new Msg("时间：", time);
		msgList.add(msg);

		Msg msg1 = new Msg("体重：", weight);
		msgList.add(msg1);

		Msg msg2 = new Msg("体温：", temperature);
		msgList.add(msg2);

		Msg msg3 = new Msg("心率：", heart);
		msgList.add(msg3);

		Msg msg4 = new Msg("收缩血压：", hpressure);
		msgList.add(msg4);

		Msg msg5 = new Msg("舒张血压：", lpressure);
		msgList.add(msg5);

		Msg msg6 = new Msg("甘油三酯：", fat);
		msgList.add(msg6);
		
		Msg msg7 = new Msg("", "");
		msgList.add(msg7);

	}
	
	private void startTts() {
		// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
		tts.setPitch(1.0f);
		// 设置语速
		tts.setSpeechRate(0.7f);
		if (yuYin.length() >= 1) {
			tts.speak(yuYin, TextToSpeech.QUEUE_FLUSH, null);
		} else {
			tts.speak("Nothing to say", TextToSpeech.QUEUE_FLUSH, null);
		}

	}
	
	private void saveData() {
		Time t=new Time(); // 
		t.setToNow(); // 取得系统时间。
		int year = t.year;
		int month = t.month+1;
		int day = t.monthDay;
		int hour = t.hour; // 0-23
		//int minute = t.minute;
		//int second = t.second;
		try {
			FileStream.writeInternal(getActivity(), year+"_"+month+"_"+day+"_"+hour, shuJu, true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public class MsgAdapter extends ArrayAdapter<Msg> {
		private int resourceId;

		public MsgAdapter(Context llShouye, int textViewresourceId,
				List<Msg> objects) {
			super(llShouye, textViewresourceId, objects);
			resourceId = textViewresourceId;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Msg msg = getItem(position);
			View view;
			ViewHolder viewHolder;

			if (convertView == null) {
				view = LayoutInflater.from(getContext()).inflate(resourceId,
						null);
				viewHolder = new ViewHolder();
				viewHolder.msg = (TextView) view.findViewById(R.id.llsy_msg);
				viewHolder.ins = (TextView) view.findViewById(R.id.llsy_ins);
				view.setTag(viewHolder);
			} else {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
			}

			viewHolder.msg.setText(msg.getContent());
			viewHolder.ins.setText(msg.getSuggestion());
			return view;
		}

		class ViewHolder {
			TextView msg;
			TextView ins;
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void onInit(int status) {
		if (status == tts.SUCCESS) {
			// Toast.makeText(MainActivity.this,"成功输出语音",
			// Toast.LENGTH_SHORT).show();
			// Locale loc1=new Locale("us");
			// Locale loc2=new Locale("china");
			int result1 = tts.setLanguage(Locale.US);
			int result2 = tts.setLanguage(Locale.CHINESE);
			if (result1 == TextToSpeech.LANG_MISSING_DATA
					|| result1 == TextToSpeech.LANG_NOT_SUPPORTED
					|| result2 == TextToSpeech.LANG_MISSING_DATA
					|| result2 == TextToSpeech.LANG_NOT_SUPPORTED)

			{
				Toast.makeText(this.getActivity(), "数据丢失或不支持",
						Toast.LENGTH_SHORT).show();
			}
		}

	}
	
	public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = null;
		try {
			data = Uri.parse("tel:" + FileStream.readInternal(getActivity(), "userphone"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        intent.setData(data);
        startActivity(intent);
    }

	@Override
	public void onDestroy() {
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}
		super.onStop();
	}

}