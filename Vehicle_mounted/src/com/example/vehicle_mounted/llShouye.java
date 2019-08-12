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
	private String hujiu = "�ر��Զ�����";

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
						"yyyy��MM��dd�� HH:mm:ss");// HH:mm:ss
				// ��ȡ��ǰʱ��
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
					Toast.makeText(shouye.getActivity(), "���������Ѵ�", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(shouye.getActivity(), "���������ѹر�", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
		      AlertDialog.Builder dialog = new AlertDialog.
		              Builder(shouye.getActivity());
		      dialog.setTitle("��������");//�Ի������������
		      dialog.setMessage("��ѡ��");//�Ի����в�����
		      dialog.setCancelable(false);//��������Ϊfalse�����˳��������˳�������ط�Ĭ��Ϊtrue
		//����Ϊ�Ի����������ѡ����
		      dialog.setPositiveButton("ȡ��", new DialogInterface.
		              OnClickListener() {
		          @Override
		        public void onClick(DialogInterface dialog, int which) {
		        	  Toast.makeText(shouye.getActivity(), "ȡ��", Toast.LENGTH_SHORT).show();
		        }

		    });
		      dialog.setNegativeButton("��������", new DialogInterface.
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
		        	  if(hujiu.equals("�ر��Զ�����")){
			        	  hujiu = "���Զ�����";
			        	  Toast.makeText(shouye.getActivity(), "�Զ������ѹر�", Toast.LENGTH_SHORT).show();
			        	  hujiuflag = false;
		        	  }else{
		        		  hujiu = "�ر��Զ�����"; 
		        		  Toast.makeText(shouye.getActivity(), "�Զ������Ѵ�", Toast.LENGTH_SHORT).show();
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

		private String content = "";// ��Ϣ������
		private String suggestion;

		public Msg(String content, String msg) {

			switch (content) {
			case "ʱ�䣺":
				this.content = msg;
				suggestion = "";
				break;
			case "���أ�":
				this.content = "����" + content + msg + "ǧ��";
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
				if(bmi<18.4){yiyi = "����";}
				else if(bmi<24){yiyi = "����";}
				else if(bmi<28){yiyi = "����";}
				else if(bmi<35){yiyi = "����";}
				else{yiyi = "�ǳ�����";}
				
				suggestion = "��������ָ��Ϊ"+df.format(bmi)+","+yiyi+".";
				break;
			case "���£�":
				this.content = "����" + content + msg + "���϶�";
				String yiyi1 = "";
				double tiwen = 0;
				tiwen = Double.parseDouble(msg);
				if(tiwen<37.2){yiyi1="����";}
				else if(tiwen<38){yiyi1="����";}
				else if(tiwen<39){yiyi1="�еȶȷ���";}
				else if(tiwen<41){yiyi1="����";}
				else{yiyi1="������";}
				suggestion = "�������£�"+yiyi1+".";
				break;
			case "���ʣ�":
				this.content = "����" + content + msg + "��ÿ����";
				suggestion = "���������˰���״̬�£�����ƽ��ÿ����75��.";
				break;
			case "����Ѫѹ��":
				this.content = "����" + content + msg + "���׹���";
				suggestion = "����Ѫѹ������ѹ<120mmHg������ѹ<80mmHg��";
				break;
			case "����Ѫѹ��":
				this.content = "����" + content + msg + "���׹���";
				suggestion = "����Ѫѹ������ѹ<120mmHg������ѹ<80mmHg��";
				break;
			case "����������":
				this.content = "����" + content + msg + "��Ħ��ÿ��";
				suggestion = "�ܵ��̴�����ֵ����5.2��Ħ��ÿ����������ʾ������Ӳ������֬Ѫ֢�ȣ�������ʾ���׿���ƶѪ����Ⱦ�ȡ�";
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
		Msg msg = new Msg("ʱ�䣺", time);
		msgList.add(msg);

		Msg msg1 = new Msg("���أ�", weight);
		msgList.add(msg1);

		Msg msg2 = new Msg("���£�", temperature);
		msgList.add(msg2);

		Msg msg3 = new Msg("���ʣ�", heart);
		msgList.add(msg3);

		Msg msg4 = new Msg("����Ѫѹ��", hpressure);
		msgList.add(msg4);

		Msg msg5 = new Msg("����Ѫѹ��", lpressure);
		msgList.add(msg5);

		Msg msg6 = new Msg("����������", fat);
		msgList.add(msg6);
		
		Msg msg7 = new Msg("", "");
		msgList.add(msg7);

	}
	
	private void startTts() {
		// ����������ֵԽ������Խ�⣨Ů������ֵԽС��������,1.0�ǳ���
		tts.setPitch(1.0f);
		// ��������
		tts.setSpeechRate(0.7f);
		if (yuYin.length() >= 1) {
			tts.speak(yuYin, TextToSpeech.QUEUE_FLUSH, null);
		} else {
			tts.speak("Nothing to say", TextToSpeech.QUEUE_FLUSH, null);
		}

	}
	
	private void saveData() {
		Time t=new Time(); // 
		t.setToNow(); // ȡ��ϵͳʱ�䡣
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
			// Toast.makeText(MainActivity.this,"�ɹ��������",
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
				Toast.makeText(this.getActivity(), "���ݶ�ʧ��֧��",
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