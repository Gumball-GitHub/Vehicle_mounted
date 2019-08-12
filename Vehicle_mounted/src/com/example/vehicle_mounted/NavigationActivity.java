package com.example.vehicle_mounted;
import android.app.Activity;  
import android.graphics.drawable.Drawable;
import android.os.Bundle;  
import android.view.KeyEvent;
import android.view.View;  
import android.view.Window;  
import android.widget.LinearLayout;  
import android.widget.TextView;  
import android.widget.Toast;
  
public class NavigationActivity extends Activity{  
  
    private LinearLayout llShouye, llTubiao, llLishi, llGeren;   
    private TextView tvShouye, tvTubiao, tvLishi, tvGeren;  
    private FragmentSwitchTool tool;  
  
    @SuppressWarnings("unchecked")
	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.activity_navigation);  
  
        initView();  
        tool = new FragmentSwitchTool(getFragmentManager(), R.id.flContainer);  
        tool.setClickableViews(llShouye, llTubiao, llLishi, llGeren);  
        tool.addSelectedViews(new View[]{tvShouye}).addSelectedViews(new View[]{tvTubiao})  
            .addSelectedViews(new View[]{tvLishi}).addSelectedViews(new View[]{tvGeren});  
        tool.setFragments(llShouye.class ,llTubiao.class ,llLishi.class ,llGeren.class); 
          
        tool.changeTag(llShouye);  
    }  
  
    private void initView() {  
        llShouye = (LinearLayout) findViewById(R.id.llShouye);  
        llTubiao = (LinearLayout) findViewById(R.id.llTubiao);  
        llLishi = (LinearLayout) findViewById(R.id.llLishi);  
        llGeren = (LinearLayout) findViewById(R.id.llGeren);  
          
        tvShouye = (TextView) findViewById(R.id.tvShouye);  
        tvTubiao = (TextView) findViewById(R.id.tvTubiao);  
        tvLishi = (TextView) findViewById(R.id.tvLishi);  
        tvGeren = (TextView) findViewById(R.id.tvGeren);  
        
        initPhoto(tvShouye,R.id.tvShouye,R.drawable.clinic);
        initPhoto(tvTubiao,R.id.tvTubiao,R.drawable.heart_monitor);
        initPhoto(tvLishi,R.id.tvLishi,R.drawable.wedding_day);
        initPhoto(tvGeren,R.id.tvGeren,R.drawable.name);
    }  
    
    private void initPhoto(TextView textview ,int tid ,int did) {
        textview = (TextView) findViewById(tid);
        Drawable drawable1 = getResources().getDrawable(did);
        drawable1.setBounds(0, 0, 40, 40);//��һ0�Ǿ���߾��룬�ڶ�0�Ǿ��ϱ߾��룬40�ֱ��ǳ���
        textview.setCompoundDrawables(drawable1, null, null, null);//ֻ�����
    }
    
  //��дonKeyDown����
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	//�жϵ�������Ƿ��ؼ�
        if (keyCode == event.KEYCODE_BACK) {
    exit();//�˳�����
        }
        return true;
    }
    private long time = 0;

    //�˳�����
    private void exit() {
    //������������2��
    if (System.currentTimeMillis() - time > 2000) {
    //��õ�ǰ��ʱ��
    time = System.currentTimeMillis();
    show_Toast("�ٵ��һ���˳�Ӧ�ó���");
    } else {
    //�������������
    	this.finish();//ִ���Ƴ�����Activity����
    	MainActivity.main.finish();
    }
    }
    public void show_Toast(String text) {
    	Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
    	}
  
  
}  
