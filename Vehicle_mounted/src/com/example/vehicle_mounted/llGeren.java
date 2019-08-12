package com.example.vehicle_mounted;

import java.io.IOException;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class llGeren extends Fragment{
	
	TextView name,height,phone,version;
	Button btn;
	EditText edname,edhei,edpho;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.llgeren, container,false);
		initView(view);
        
		return view;
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	public void initView(View view){
		
		String uname = "�û���", uheight = "170", uphone = "119";
		try {
			uname =  FileStream.readInternal(getActivity(), "username");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			uheight =  FileStream.readInternal(getActivity(), "userheight");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			uphone =  FileStream.readInternal(getActivity(), "userphone");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		name = (TextView) view.findViewById(R.id.llgr_name);
        Drawable drawable1 = getResources().getDrawable(R.drawable.name);
        drawable1.setBounds(0, 0, 40, 40);
        name.setCompoundDrawables(drawable1, null, null, null);
        name.setText("�û�����"+uname);
        
        height = (TextView) view.findViewById(R.id.llgr_height);
        Drawable drawable2 = getResources().getDrawable(R.drawable.name);
        drawable2.setBounds(0, 0, 40, 40);
        height.setCompoundDrawables(drawable2, null, null, null);
        height.setText("��ߣ�CM����"+uheight);
        
        phone = (TextView) view.findViewById(R.id.llgr_phone);
        Drawable drawable3 = getResources().getDrawable(R.drawable.name);
        drawable3.setBounds(0, 0, 40, 40);
        phone.setCompoundDrawables(drawable3, null, null, null);
        phone.setText("���ȵ绰��"+uphone);
        
        version = (TextView) view.findViewById(R.id.llgr_version);
        Drawable drawable4 = getResources().getDrawable(R.drawable.name);
        drawable4.setBounds(0, 0, 40, 40);
        version.setCompoundDrawables(drawable4, null, null, null);
        
        btn = (Button) view.findViewById(R.id.llgr_btn);
        edname = (EditText) view.findViewById(R.id.llgr_edname);
        edname.setVisibility(View.GONE); 
        edhei = (EditText) view.findViewById(R.id.llgr_edhei);
        edhei.setVisibility(View.GONE); 
        edpho = (EditText) view.findViewById(R.id.llgr_edpho);
        edpho.setVisibility(View.GONE); 
        
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(btn.getText().equals("�޸���Ϣ"))
				{
				btn.setText("������Ϣ");
				edname.setVisibility(View.VISIBLE); 
				edhei.setVisibility(View.VISIBLE); 
				edpho.setVisibility(View.VISIBLE); 
				}else{
					if(!("".equals(edname.getText().toString())||edname.getText().toString()==null)){
						name.setText("�û�����"+edname.getText());
						try {
							FileStream.writeInternal(getActivity(), "username", edname.getText().toString(), false);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{}
					if(!("".equals(edhei.getText().toString())||edhei.getText().toString()==null)){
						height.setText("��ߣ�CM����"+edhei.getText());
						try {
							FileStream.writeInternal(getActivity(), "userheight", edhei.getText().toString(), false);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{}
					if(!("".equals(edpho.getText().toString())||edpho.getText().toString()==null)){
						phone.setText("���ȵ绰��"+edpho.getText());
						try {
							FileStream.writeInternal(getActivity(), "userphone", edpho.getText().toString(), false);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{}

					btn.setText("�޸���Ϣ");
					edname.setVisibility(View.GONE); 
					edhei.setVisibility(View.GONE); 
					edpho.setVisibility(View.GONE); 
				}
				
			}
		});
	}

}
