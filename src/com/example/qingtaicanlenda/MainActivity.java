package com.example.qingtaicanlenda;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import  com.example.qingtaicanlenda.CalendarCard.OnCellClickListener;

public class MainActivity extends FragmentActivity implements OnClickListener, OnCellClickListener{
	private ViewPager mViewPager;
	private int mCurrentIndex = 498;
	private CalendarCard[] mShowViews;
	private CalendarViewAdapter<CalendarCard> adapter;
	private SildeDirection mDirection = SildeDirection.NO_SILDE;
	enum SildeDirection {
		RIGHT, LEFT, NO_SILDE;}
	private TextView textView;
	private ImageButton preImgBtn, nextImgBtn;
	private TextView monthText;
	private TextView closeImgBtn;

	private SharedPreferences pref;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.canlendar);
		mViewPager = (ViewPager) this.findViewById(R.id.vp_calendar);
		preImgBtn = (ImageButton) this.findViewById(R.id.btnPreMonth);
		nextImgBtn = (ImageButton) this.findViewById(R.id.btnNextMonth);
		monthText = (TextView) this.findViewById(R.id.tvCurrentMonth);
		closeImgBtn = (TextView) this.findViewById(R.id.btnClose);
		preImgBtn.setOnClickListener(this);
		nextImgBtn.setOnClickListener(this);
		closeImgBtn.setOnClickListener(this);
		pref=getSharedPreferences("data", MODE_PRIVATE);
		CalendarCard[] views = new CalendarCard[3];
		for (int i = 0; i < 3; i++) {
			views[i] = new CalendarCard(this, this);
	
		}
		adapter = new CalendarViewAdapter<CalendarCard>(views);
	
		setViewPager();

	}
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		initView();
//	}
//	public void initView() {
//	    FragmentCalendar chat = new FragmentCalendar();
//	    getFragmentManager().beginTransaction().replace(R.id.main_content, chat).commit();
//	    RadioGroup myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
//	    myTabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//	 
//	 
//	        @Override
//	        public void onCheckedChanged(RadioGroup group, int checkedId) {
//	            FragmentCalendar s;
//				// TODO Auto-generated method stub
//	            switch (checkedId) {
//	            case R.id.rili:
//	                s = new FragmentCalendar();
//	                getFragmentManager().beginTransaction().replace(R.id.main_content, s)
//	                        .commit();
//	                break;
////	            case R.id.geren:
////	                if (address==null) {
////	                    address =new FragmentAddress();
////	                }
////	                Log.i("MyFragment", "FragmentAddress");
////	                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, address).commit();
////	                break;
////	            case R.id.rbFind:
////	                find = new FragmentFind();
////	                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, find)
////	                        .commit();
////	                break;
//	            case R.id.zhidao:
//	                FragmentZhidao zhidao = new FragmentZhidao();
//	                getFragmentManager().beginTransaction().replace(R.id.main_content, zhidao)
//	                        .commit();
//	                break;
//	            default:
//	                break;
//	            }
//	 
//	        }
//	    });
//
//}
	

	
	private void setViewPager() {
		mViewPager.setAdapter(adapter);
		mViewPager.setCurrentItem(498);
		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				measureDirection(position);
				updateCalendarView(position);				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
//	
	@Override
	public void onClick(View v) {
		textView= (TextView) findViewById(R.id.btnClose);
		switch (v.getId()) {
		case R.id.btnPreMonth:
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
			break;
		case R.id.btnNextMonth:
			mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
			break;
		case R.id.btnClose:
			
			textView.setOnClickListener(new OnClickListener() {
				@Override
			public void onClick(View arg0) {
				//跳到设置界面
					Intent intent = new Intent(MainActivity.this,SetActivity.class);
					startActivityForResult(intent, 1); 
				}
				});
			
		break;
		default:
			break;
		}
	}

	@Override
	public void clickDate(CustomData date) {
		
	}

	@Override
	public void changeDate(CustomData date) {
		monthText.setText(date.year+"年"+ date.month + "月");
	}

	/**
	 * 璁＄畻鏂瑰悜
	 * 
	 * @param arg0
	 */
	private void measureDirection(int arg0) {

		if (arg0 > mCurrentIndex) {
			mDirection = SildeDirection.RIGHT;

		} else if (arg0 < mCurrentIndex) {
			mDirection = SildeDirection.LEFT;
		}
		mCurrentIndex = arg0;
	}

	// 更新日历
	private void updateCalendarView(int arg0) {
		mShowViews = adapter.getAllItems();
		if (mDirection == SildeDirection.RIGHT) {
			mShowViews[arg0 % mShowViews.length].rightSlide();
		} else if (mDirection == SildeDirection.LEFT) {
			mShowViews[arg0 % mShowViews.length].leftSlide();
		}
		mDirection = SildeDirection.NO_SILDE;
	}


@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	Log.d("Main","hello");
	switch(requestCode){
	case 1:
		if (resultCode==RESULT_OK){
			
		    TextView testView=	(TextView) findViewById(R.id.test);
			String returnedData=data.getStringExtra("持续时间");
			String returnedData1=data.getStringExtra("周期间隔");
			String returnedData2=data.getStringExtra("上次姨妈日期");
		
			String value= returnedData;
			String value1=returnedData1;
			String value2=returnedData2;
			editor= pref.edit();
			//editor.putBoolean("save", true);
			editor.putString("持续时间", value);
			editor.putString("周期间隔", value1);
			editor.putString("上次姨妈日期", value2);
			editor.commit();
			testView.setText(returnedData+returnedData1+returnedData2);
		}
		break;
		default:
	}
}


	
}


