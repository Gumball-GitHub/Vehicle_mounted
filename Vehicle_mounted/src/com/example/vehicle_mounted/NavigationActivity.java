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
        drawable1.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        textview.setCompoundDrawables(drawable1, null, null, null);//只放左边
    }
    
  //重写onKeyDown方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	//判断当点击的是返回键
        if (keyCode == event.KEYCODE_BACK) {
    exit();//退出方法
        }
        return true;
    }
    private long time = 0;

    //退出方法
    private void exit() {
    //如果在两秒大于2秒
    if (System.currentTimeMillis() - time > 2000) {
    //获得当前的时间
    time = System.currentTimeMillis();
    show_Toast("再点击一次退出应用程序");
    } else {
    //点击在两秒以内
    	this.finish();//执行移除所以Activity方法
    	MainActivity.main.finish();
    }
    }
    public void show_Toast(String text) {
    	Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
    	}
  
  
}  
