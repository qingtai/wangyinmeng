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
         private String[] data={"����ʱ��","���ڼ��","��һ�ξ���ʱ��"};
		 //private String[] data1={"��ǰ����","��ǰһ��","��һ����","��һ����","��һ����"};
	
         
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

		//�Զ���ĵ�������  
	    SelectPicPopupWindow menuWindow ; 
	    private String initStartDateTime = "2013��9��3�� 14:44"; // ��ʼ����ʼʱ��
		private String initEndDateTime = "2014��8��23�� 17:44"; // ��ʼ������ʱ��
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
       	   String value=pref.getString("����ʱ��", "");
       	   String value1=pref.getString("���ڼ��", "");
       	   String value2=pref.getString("�ϴ���������", "");
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
	        //�����ֿؼ���Ӽ�������������Զ��崰��  
	      tv.setOnClickListener(new OnClickListener() {            

				public void onClick(View v) {  
	                //ʵ����SelectPicPopupWindow  
					SelectPicPopupWindow menuWindow= new SelectPicPopupWindow(SetActivity.this, itemsOnClick,tv);  
	                //��ʾ����  
	                menuWindow.showAtLocation(SetActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0, 10); //����layout��PopupWindow����ʾ��λ��
	               
	 
	            }

			     });

			ts.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							SelectDayPopupWindow menuWindow= new SelectDayPopupWindow(SetActivity.this, itemsOnClick,ts);  
			                //��ʾ����  
			                menuWindow.showAtLocation(SetActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0, 10); //����layout��PopupWindow����ʾ��λ��
			               
						}
					});	
			te.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					SelectDayPopupWindow menuWindow= new SelectDayPopupWindow(SetActivity.this, itemsOnClick,te);  
	                //��ʾ����  
	                menuWindow.showAtLocation(SetActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0, 10); //����layout��PopupWindow����ʾ��λ��
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
					editor.putString("����ʱ��", value);
					editor.putString("���ڼ��", value1);
					editor.putString("�ϴ���������", value2);
					
					editor.commit();
					Intent intent = new Intent();
					intent.putExtra("����ʱ��", value);
					intent.putExtra("���ڼ��",value1);
					intent.putExtra("�ϴ���������", value2);
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
//				editor.putString("����ʱ��", value);
//				editor.putString("���ڼ��", value1);
//				editor.putString("�ϴ���������", value2);
//        	    Intent intent = new Intent(SetActivity.this,MainActivity.class);
//				intent.putExtra("����ʱ��", value);
//				intent.putExtra("���ڼ��",value1);
//				intent.putExtra("�ϴ���������", value2);
//				setResult(RESULT_OK, intent);
//				finish();
//        }
//			  
//	        
//	   
	      
	    //Ϊ��������ʵ�ּ�����  
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
