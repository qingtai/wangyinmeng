package com.example.qingtaicanlenda;






import java.text.DateFormat.Field;
import java.util.Calendar;
import java.util.Date;

import android.R.raw;
import android.app.Activity;  
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;  
import android.graphics.drawable.ColorDrawable;  
import android.util.Log;
import android.view.LayoutInflater;  
import android.view.MotionEvent;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.view.View.OnTouchListener;  
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;  
import android.widget.Button;  
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;  
import android.widget.TextView;
import android.widget.DatePicker.OnDateChangedListener;
  
public class SelectDayPopupWindow extends PopupWindow  {  
  
  
//    private Button btn_take_photo, btn_pick_photo, btn_cancel;  
    private View mMenuView;
	//private TextView finish;  
    private  OnDateSetListener onDateSetListener = null;
	//private Context Context = null;
//    final Calendar cal = Calendar.getInstance();
//    private DatePickerDialog mDialog;
	//private static Context context;
    
   public SelectDayPopupWindow(Activity context,OnClickListener itemsOnClick,final TextView ts) {  
             super(context);  
             LayoutInflater inflater = (LayoutInflater) context  
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
              mMenuView = inflater.inflate(R.layout.alert_dialog, null); 
              final DatePicker datePicker=(DatePicker)mMenuView.findViewById(R.id.datepicker);
              final TextView finish=(TextView) mMenuView.findViewById(R.id.abset);
              final TextView cancel=(TextView) mMenuView.findViewById(R.id.abcset);
              Calendar calendar=Calendar.getInstance();
              int year=calendar.get(Calendar.YEAR);
              int monthOfYear=calendar.get(Calendar.MONTH);
              int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
              //Log.d(tag, "hello");
              final String TAGA = null;
			  Log.d(TAGA,"��"+year);
//			  DatePickerDialog datePickerDialog = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, onDateSetListener, year, monthOfYear, dayOfMonth);  
//		        datePickerDialog.setCancelable(true);  
//		        DatePicker dp = datePickerDialog.getDatePicker();  
//		         //���õ���Ϊ��Сֵ  
//		         dp.setMinDate(calendar.getTimeInMillis());  
//		         //�������ֵ�ǣ���  
//		         calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth + 6);  
//		        dp.setMaxDate(calendar.getTimeInMillis());  
//		       try {  
//		            //��ȡָ�����ֶ�  
//		           java.lang.reflect.Field field =  dp.getClass().getDeclaredField("mYearSpinner");  
//		            //���װ  
//		              field.setAccessible(true);  
//		             //��ȡ��ǰʵ����ֵ  
//		           NumberPicker np = ((NumberPicker) field.get(dp));  
//		             np.setVisibility(View.GONE);  
//		        } catch (NoSuchFieldException e) {  
//		            e.printStackTrace();  
//		        } catch (IllegalAccessException e) {  
//		              e.printStackTrace();  
//		        }  
		       // datePickerDialog.show();  
			  DatePickerDialog datePickerDialog = new DatePickerDialog(context, AlertDialog.THEME_HOLO_LIGHT, onDateSetListener, year, monthOfYear, dayOfMonth);  
		        datePickerDialog.setCancelable(true);
              final DatePicker dp = datePickerDialog.getDatePicker(); 
              //���õ���Ϊ��Сֵ  
		         dp.setMinDate(calendar.getTimeInMillis());  
		         //�������ֵ�ǣ���  
		         calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth + 6);  
		        dp.setMaxDate(calendar.getTimeInMillis()); 
		        try {  
		            //��ȡָ�����ֶ�  
		           java.lang.reflect.Field field =  dp.getClass().getDeclaredField("mYearSpinner");  
		            //���װ  
		              field.setAccessible(true);  
		             //��ȡ��ǰʵ����ֵ  
		           NumberPicker np = ((NumberPicker) field.get(datePicker));  
		             np.setVisibility(View.GONE);  
		             java.lang.reflect.Field field1 =  dp.getClass().getDeclaredField("mMonthSpinner");  
			            //���װ  
			              field1.setAccessible(true);  
			             //��ȡ��ǰʵ����ֵ  
			           NumberPicker np1 = ((NumberPicker) field1.get(datePicker));  
			             np1.setVisibility(View.GONE); 
		             
		        } catch (NoSuchFieldException e1) {  
		            e1.printStackTrace();  
		        } catch (IllegalAccessException e) {  
		              Throwable e1 = null;
					e1.printStackTrace();  
		        }  
			  datePicker.init(1999, 3, 5,  new OnDateChangedListener(){

				 
				
//			         //���õ���Ϊ��Сֵ  
//			         dp.setMinDate(calendar.getTimeInMillis());  
//			         //�������ֵ�ǣ���  
//			         calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth + 6);  
//			        dp.setMaxDate(calendar.getTimeInMillis());  
			       
					@Override
					public void onDateChanged(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						
						finish.setOnClickListener(new OnClickListener() {
							
					

							@Override
							public void onClick(View v) {
								
								// TODO Auto-generated method stub
								ts.setText(datePicker.getDayOfMonth()+"��");
							    String tag = null;
							    Log.d(TAGA,"��");
							}
						});
						cancel.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dismiss();	
							}
						});
        
//        btn_take_photo = (Button) mMenuView.findViewById(R.id.btn_take_photo);  
//        btn_pick_photo = (Button) mMenuView.findViewById(R.id.btn_pick_photo);  
//        btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel);  
//         setView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
					}
			  });
//        //ȡ����ť  
//        btn_cancel.setOnClickListener(new OnClickListener() {  
//  
//            public void onClick(View v) {  
//                //���ٵ�����  
//                dismiss();  
//            }  
//        });  
//        //���ð�ť����  
//        btn_pick_photo.setOnClickListener(itemsOnClick);  
//        btn_take_photo.setOnClickListener(itemsOnClick);  
        //����SelectPicPopupWindow��View  
        this.setContentView(mMenuView);  
        //����SelectPicPopupWindow��������Ŀ�  
        this.setWidth(LayoutParams.FILL_PARENT);  
        //����SelectPicPopupWindow��������ĸ�  
        this.setHeight(150); 
     // this.setHeight(LayoutParams.WRAP_CONTENT);  
        //����SelectPicPopupWindow��������ɵ��  
        this.setFocusable(true);  
        //����SelectPicPopupWindow�������嶯��Ч��  
      //this.setAnimationStyle(R.style.AnimBottom);  
        //ʵ����һ��ColorDrawable��ɫΪ��͸��  
        ColorDrawable dw = new ColorDrawable(0x00000000);  
        //����SelectPicPopupWindow��������ı���  
        this.setBackgroundDrawable(dw);  
        //mMenuView���OnTouchListener�����жϻ�ȡ����λ�������ѡ������������ٵ�����  
        mMenuView.setOnTouchListener(new OnTouchListener() {  
              
            public boolean onTouch(View v, MotionEvent event) {  
                  
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();  
                int y=(int) event.getY();  
                if(event.getAction()==MotionEvent.ACTION_UP){  
                    if(y<height){  
                       dismiss(); 
                    	
                    }  
                }                 
                return true;  
            }  
        });  
  
    }

//public SelectPicPopupWindow(MainActivity context,
//		OnClickListener itemsOnClick, TextView tv) {
//
//       
    
// private void showDatePickerDialog() {  
//	     Calendar calendar = Calendar.getInstance();  
//	     calendar.setTime(new Date());  
//	     int year = calendar.get(Calendar.YEAR);  
//	     final int month = calendar.get(Calendar.MONTH);  
//	     int day = calendar.get(Calendar.DAY_OF_MONTH);  
//	     LayoutInflater inflater = (LayoutInflater) getContext()  
//	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
//	           mMenuView = inflater.inflate(R.layout.alert_dialog, null); 
//	    final DatePicker datePicker=(DatePicker)mMenuView.findViewById(R.id.datepicker);
//		DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, onDateSetListener, year, month, day);  
//	        datePickerDialog.setCancelable(true);  
//	        DatePicker dp = datePickerDialog.getDatePicker();  
//	         //���õ���Ϊ��Сֵ  
//	         dp.setMinDate(calendar.getTimeInMillis());  
//	         //�������ֵ�ǣ���  
//	         calendar.set(Calendar.DAY_OF_MONTH, day + 6);  
//	        dp.setMaxDate(calendar.getTimeInMillis());  
//	       try {  
//	            //��ȡָ�����ֶ�  
//	           java.lang.reflect.Field field =  dp.getClass().getDeclaredField("mYearSpinner");  
//	            //���װ  
//	              field.setAccessible(true);  
//	             //��ȡ��ǰʵ����ֵ  
//	           NumberPicker np = ((NumberPicker) field.get(dp));  
//	             np.setVisibility(View.GONE);  
//	        } catch (NoSuchFieldException e) {  
//	            e.printStackTrace();  
//	        } catch (IllegalAccessException e) {  
//	              e.printStackTrace();  
//	        }  
//	        datePickerDialog.show();  
//	   
//	      }
//
//private Context getContext() {
//	// TODO Auto-generated method stub
//	return context;
}


//   private DatePicker findDatePicker(ViewGroup group) { 
//	     if (group != null) { 
//	         for (int i = 0, j = group.getChildCount(); i < j; i++) { 
//	           View child = group.getChildAt(i); 
//	              if (child instanceof DatePicker) { 
//	               return (DatePicker) child; 
//	           } else if (child instanceof ViewGroup) { 
//	                DatePicker result = findDatePicker((ViewGroup) child); 
//	               if (result != null) 
//	                      return result; 
//	            } 
//	         } 
//	     } 
//	      return null; 
//	   
//	  }  
  
  

//  



