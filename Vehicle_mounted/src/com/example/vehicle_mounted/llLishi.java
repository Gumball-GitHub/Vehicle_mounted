package com.example.vehicle_mounted;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;;

public class llLishi extends Fragment implements AdapterView.OnItemClickListener{
	private ListView listView;
	private List<String> list = new ArrayList<String>();
	private MsgAdapter adapter;
	TextView lj;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.lllishi, container, false);
		lj = (TextView) view.findViewById(R.id.llls_lujin);
		lj.setText("文件存储路径："+FileStream.getFileLujin(getActivity(), ""));
		
		
		initList();
		adapter = new MsgAdapter(this.getActivity(), R.layout.lllishi2,
				list);
		listView = (ListView) view.findViewById(R.id.llls_folder);
		listView.setOnItemClickListener(this);
		listView.setAdapter(adapter);
		
		return view;
	}
	
	private void initList() {
		File[] files=FileStream.getFiles(getActivity().getFilesDir()+"");
		for(int i =0;i<files.length;i++){
			String listitem = files[i].getAbsolutePath().substring(files[i].getAbsolutePath().lastIndexOf("/")+1);
			if(!(listitem.equals("username")||listitem.equals("userheight")||listitem.equals("userphone"))){
				list.add(listitem);
			}
			
		}

	}
	
	public class MsgAdapter extends ArrayAdapter<String> {
		private int resourceId;

		public MsgAdapter(Context llLishi, int textViewresourceId,
				List<String> objects) {
			super(llLishi, textViewresourceId, objects);
			resourceId = textViewresourceId;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			String folder = getItem(position);
			View view;
			ViewHolder viewHolder;

			if (convertView == null) {
				view = LayoutInflater.from(getContext()).inflate(resourceId,
						null);
				viewHolder = new ViewHolder();
				viewHolder.llls2_tv = (TextView) view.findViewById(R.id.llls2_tv);
				view.setTag(viewHolder);
			} else {
				view = convertView;
				viewHolder = (ViewHolder) view.getTag();
			}
			viewHolder.llls2_tv = (TextView) view.findViewById(R.id.llls2_tv);
	        Drawable drawable2 = getResources().getDrawable(R.drawable.holy_bible);
	        drawable2.setBounds(0, 0, 40, 40);
	        viewHolder.llls2_tv.setCompoundDrawables(drawable2, null, null, null);
			viewHolder.llls2_tv.setText(folder);
			return view;
		}

		class ViewHolder {
			TextView llls2_tv;
		}
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String list = (String) parent.getItemAtPosition(position).toString();
		try {
			new AlertDialog.Builder(getActivity()).setTitle("您的历史健康数据：").setMessage(FileStream.readInternal(getActivity(), list)).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
