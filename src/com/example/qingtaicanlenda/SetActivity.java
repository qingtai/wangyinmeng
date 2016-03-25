package com.example.qingtaicanlenda;



import com.example.qingtaicanlenda.SelectDayPopupWindow;
import com.example.qingtaicanlenda.SelectPicPopupWindow;
import com.example.qingtaicanlenda.R;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class SetActivity extends FragmentActivity {
         private ImageButton titleBack;
         private TextView titleFinish;
         private String[] data={"持续时间","经期间隔","上一次经期时间"};
		 //private String[] data1={"提前两天","提前一天","第一天早","第一天中","第一天晚"};
	
         
		//@Override
         
//        protected void onCreate(Bundle savedInstanceState) {
//        	// TODO Auto-generated method stub
//        	super.onCreate(savedInstanceState);
//        	requestWindowFeature(Window.FEATURE_NO_TITLE);
//        	setContentView(R.layout.title);
//        	
//            titleBack = (ImageButton) this.findViewById(R.id.title_back);
//            titleFinish=(TextView) this.findViewById(R.id.title_finish);
//            titleBack.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(SetActivity.this,MainActivity.class);
//				startActivity(intent);
//			}
//		});
//   
//        
//        }

		//自定义的弹出框类  
	    SelectPicPopupWindow menuWindow ; 
	    private String initStartDateTime = "2013年9月3日 14:44"; // 初始化开始时间
		private String initEndDateTime = "2014年8月23日 17:44"; // 初始化结束时间
		private SharedPreferences pref;
		SharedPreferences.Editor editor;
			@Override  
	    public void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.title);  
	        final TextView te = (TextView) this.findViewById(R.id.cycle); 
	        final TextView ts=(TextView) findViewById(R.id.last_time);
	        final TextView tv=(TextView) findViewById(R.id.last_time_yima);
	        
	      titleBack = (ImageButton) this.findViewById(R.id.title_back);
          titleFinish=(TextView) this.findViewById(R.id.title_finish);
          pref=getSharedPreferences("data", MODE_PRIVATE);
          boolean isRember=pref.getBoolean("save", false);
          if(isRember){
       	   SharedPreferences pref= getSharedPreferences("data",MODE_PRIVATE);
       	   String value=pref.getString("持续时间", "");
       	   String value1=pref.getString("周期间隔", "");
       	   String value2=pref.getString("上次姨妈日期", "");
       	     ts.setText(value);
       	     te.setText(value1);
       	     tv.setText(value2);
   		
          }
          
          titleBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SetActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
	        //把文字控件添加监听，点击弹出自定义窗口  
	      tv.setOnClickListener(new OnClickListener() {            

				public void onClick(View v) {  
	                //实例化SelectPicPopupWindow  
					SelectPicPopupWindow menuWindow= new SelectPicPopupWindow(SetActivity.this, itemsOnClick,tv);  
	                //显示窗口  
	                menuWindow.showAtLocation(SetActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0, 10); //设置layout在PopupWindow中显示的位置
	               
	 
	            }

			     });

			ts.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							SelectDayPopupWindow menuWindow= new SelectDayPopupWindow(SetActivity.this, itemsOnClick,ts);  
			                //显示窗口  
			                menuWindow.showAtLocation(SetActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0, 10); //设置layout在PopupWindow中显示的位置
			               
						}
					});	
			te.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					SelectDayPopupWindow menuWindow= new SelectDayPopupWindow(SetActivity.this, itemsOnClick,te);  
	                //显示窗口  
	                menuWindow.showAtLocation(SetActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0, 10); //设置layout在PopupWindow中显示的位置
				}
			});
			
			titleFinish.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {

	                editor= pref.edit();
					String value= ts.getText().toString();
					String value1=te.getText().toString();
					String value2=tv.getText().toString();
					editor.putBoolean("save", true);
					editor.putString("持续时间", value);
					editor.putString("周期间隔", value1);
					editor.putString("上次姨妈日期", value2);
					
					editor.commit();
					Intent intent = new Intent();
					intent.putExtra("持续时间", value);
					intent.putExtra("周期间隔",value1);
					intent.putExtra("上次姨妈日期", value2);
					setResult(RESULT_OK,intent);
					finish();
				}
				
			});
			
					}
//	              DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
//							MainActivity.this, initEndDateTime);
//					dateTimePicKDialog.dateTimePicKDialog(tv);
					
				
//         @Override
//        public void onBackPressed() {
//     
//        	    editor= pref.edit();
//				String value= ts.getText().toString();
//				String value1=te.getText().toString();
//				String value2=tv.getText().toString();
//				editor.putBoolean("save", true);
//				editor.putString("持续时间", value);
//				editor.putString("周期间隔", value1);
//				editor.putString("上次姨妈日期", value2);
//        	    Intent intent = new Intent(SetActivity.this,MainActivity.class);
//				intent.putExtra("持续时间", value);
//				intent.putExtra("周期间隔",value1);
//				intent.putExtra("上次姨妈日期", value2);
//				setResult(RESULT_OK, intent);
//				finish();
//        }
//			  
//	        
//	   
	      
	    //为弹出窗口实现监听类  
	    private OnClickListener  itemsOnClick = new OnClickListener(){  
	  
	        public void onClick(View v) {  
	            menuWindow.dismiss();  
	            switch (v.getId()) {  
//	            case R.id.btn_take_photo:  
//	                break;  
//	            case R.id.btn_pick_photo:                 
//	                break;  
//	            default:  
//	                break;  
	            }  
	              
	                  
	        }  
	          
	    };  


		
}
