package com.example.qingtaicanlenda;


import java.security.GeneralSecurityException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * 鑷畾涔夋棩鍘嗗崱
 * 
 * @author wuwenjie
 * 
 */
public class CalendarCard extends View {

	private static final int TOTAL_COL = 7; // 7列
	private static final int TOTAL_ROW = 6; // 6行

	private Paint mCirclePaint; // 绘制圆形的画笔
	private Paint mTextPaint; // 绘制文本的画笔
	private int mViewWidth; // 视图宽度
	private int mViewHeight; // 师徒的高度
	private int mCellSpace; // 单元格间距
	private Row rows[] = new Row[TOTAL_ROW]; // 行数组 每个元素代表一行
	private static CustomData mShowDate; // 自定义的日期year,month,day
	private OnCellClickListener mCellClickListener; // 单元格点击回调时间
	private int touchSlop; //
	private boolean callBackCellSpace;

	private Cell mClickCell;
	private float mDownX;
	private float mDownY;

	/**
	 *单元格点击的回调接口
	 * 
	 * @author wuwenjie
	 * 
	 */
	public interface OnCellClickListener {
		void clickDate(CustomData date); // 回调点击的日期

		void changeDate(CustomData date); //回调 ViewPager改变的日期
	}

	public CalendarCard(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public CalendarCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CalendarCard(Context context) {
		super(context);
		init(context);
	}

	public CalendarCard(Context context, OnCellClickListener listener) {
		super(context);
		this.mCellClickListener = listener;
		init(context);
	}

	private void init(Context context) {
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mCirclePaint.setStyle(Paint.Style.FILL);
		mCirclePaint.setColor(Color.parseColor("#F24949")); //红色圆形
		touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		initDate();
	}

	private void initDate() {
		mShowDate = new CustomData();
		fillDate();//
	}

	private void fillDate() {
		int monthDay = DateUtil.getCurrentMonthDay(); // 今天
		int lastMonthDays = DateUtil.getMonthDays(mShowDate.year,
				mShowDate.month - 1); // 上个月的天数
		int currentMonthDays = DateUtil.getMonthDays(mShowDate.year,
				mShowDate.month); // 当前月的天数
		int firstDayWeek = DateUtil.getWeekDayFromDate(mShowDate.year,
				mShowDate.month);   // 一周的第一天
		boolean isCurrentMonth = false;
		if (DateUtil.isCurrentMonth(mShowDate)) {
			isCurrentMonth = true;
		}
		int day = 0;
		for (int j = 0; j < TOTAL_ROW; j++) {
			rows[j] = new Row(j);
			for (int i = 0; i < TOTAL_COL; i++) {
				int position = i + j * TOTAL_COL; // 单元格的位置
				// 当前月的
				if (position >= firstDayWeek
						&& position < firstDayWeek + currentMonthDays) {
					day++;
					rows[j].cells[i] = new Cell(CustomData.modifiDayForObject(
							mShowDate, day), State.CURRENT_MONTH_DAY, i, j);
					// 今天
					if (isCurrentMonth && day == monthDay ) {
						CustomData date = CustomData.modifiDayForObject(mShowDate, day);
						rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
					}

					if (isCurrentMonth && day > monthDay) { // 这个月的今天以后的天
						rows[j].cells[i] = new Cell(
								CustomData.modifiDayForObject(mShowDate, day),
								State.UNREACH_DAY, i, j);
					}

					// 上个月
				} else if (position < firstDayWeek) {
					rows[j].cells[i] = new Cell(new CustomData(mShowDate.year,
							mShowDate.month - 1, lastMonthDays
									- (firstDayWeek - position - 1)),
							State.PAST_MONTH_DAY, i, j);
					// 下个月
				} else if (position >= firstDayWeek + currentMonthDays) {
					rows[j].cells[i] = new Cell((new CustomData(mShowDate.year,
							mShowDate.month + 1, position - firstDayWeek
									- currentMonthDays + 1)),
							State.NEXT_MONTH_DAY, i, j);
				}
			}
		}
		mCellClickListener.changeDate(mShowDate);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < TOTAL_ROW; i++) {
			if (rows[i] != null) {
				rows[i].drawCells(canvas);
			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mViewWidth = w;
		mViewHeight = h;
		mCellSpace = Math.min(mViewHeight / TOTAL_ROW, mViewWidth / TOTAL_COL);
		if (!callBackCellSpace) {
			callBackCellSpace = true;
		}
		mTextPaint.setTextSize(mCellSpace / 3);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mDownX = event.getX();
			mDownY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			float disX = event.getX() - mDownX;
			float disY = event.getY() - mDownY;
			if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop) {
				int col = (int) (mDownX / mCellSpace);
				int row = (int) (mDownY / mCellSpace);
				measureClickCell(col, row);
			}
			break;
		default:
			break;
		}

		return true;
	}

	/**
	 * 计算点击的单元格
	 * @param col
	 * @param row
	 */
	private void measureClickCell(int col, int row) {
		if (col >= TOTAL_COL || row >= TOTAL_ROW)
			return;
		if (mClickCell != null) {
			rows[mClickCell.j].cells[mClickCell.i] = mClickCell;
		}
		if (rows[row] != null) {
			mClickCell = new Cell(rows[row].cells[col].date,
					rows[row].cells[col].state, rows[row].cells[col].i,
					rows[row].cells[col].j);

			CustomData date = rows[row].cells[col].date;
			date.week = col;
			mCellClickListener.clickDate(date);

			// 更新
			update();
		}
	}

	/**
	 * 组元素
	 * 
	 * @author wuwenjie
	 * 
	 */
	class Row {
		public int j;

		Row(int j) {
			this.j = j;
		}

		public Cell[] cells = new Cell[TOTAL_COL];

		// 绘制单元格
		public void drawCells(Canvas canvas) {
			for (int i = 0; i < cells.length; i++) {
				if (cells[i] != null) {
					cells[i].drawSelf(canvas);
				}
			}
		}

	}

	/**
	 * 单元格
	 * 
	 * @author wuwenjie
	 * 
	 */
	class Cell {
		public CustomData date;
		public State state;
		public int i;
		public int j;

		public Cell(CustomData date, State state, int i, int j) {
			super();
			this.date = date;
			this.state = state;
			this.i = i;
			this.j = j;
		}

		public void drawSelf(Canvas canvas) {
			Paint paint_green= new Paint(); 
			paint_green.setStyle(Paint.Style.STROKE);
			paint_green.setColor(Color.GREEN);
			//paint_green.setColor((Color.parseColor("#F24949	")));
			switch (state) {
			case TODAY: // 今天
				mTextPaint.setColor(Color.RED);
				RectF oval = new RectF((float) (mCellSpace * i ),(float) (mCellSpace * j ),(float) (mCellSpace * (i+1) ),(float) (mCellSpace * (j+1) ));  
				canvas.drawRect(oval,paint_green);
//				canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
//						(float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
//						mCirclePaint);
				break;
			case CURRENT_MONTH_DAY: //当前月
				mTextPaint.setColor(Color.BLACK);
			break;
//				mTextPaint.setColor(Color.parseColor("#fffffe"));
//				break;
			case PAST_MONTH_DAY: // 上个月
//				mTextPaint.setColor(Color.parseColor("#fffffe"));
//				break;
				mTextPaint.setColor(Color.GRAY);
				break;
			case NEXT_MONTH_DAY: // 下个月
//				mTextPaint.setColor(Color.parseColor("#fffffe"));
//				break;
				mTextPaint.setColor(Color.GRAY);
				break;
			case UNREACH_DAY: // 本月不可达的日子
				mTextPaint.setColor(Color.BLACK);
				break;
			default:
				break;
//			case CURRENT_MONTH_DAY: //当前月
//				mTextPaint.setColor(Color.BLACK);
//				break;
//			case PAST_MONTH_DAY: // 杩囧幓涓�涓湀
//			case NEXT_MONTH_DAY: // 涓嬩竴涓湀
//				mTextPaint.setColor(Color.parseColor("#fffffe"));
//				break;
//			case UNREACH_DAY: // 杩樻湭鍒扮殑澶�
//				mTextPaint.setColor(Color.GRAY);
//				break;
//			default:
//				break;
			}
			// 绘制文字
			String content = date.day + "";
			canvas.drawText(content,
					(float) ((i + 0.5) * mCellSpace - mTextPaint
							.measureText(content) / 2), (float) ((j + 0.7)
							* mCellSpace - mTextPaint
							.measureText(content, 0, 1) / 2), mTextPaint);
		}
	}

	/**
	 * 
	 * @author wuwenjie 
	 */
	enum State {
		TODAY,CURRENT_MONTH_DAY, PAST_MONTH_DAY, NEXT_MONTH_DAY, UNREACH_DAY;
	}

	// 从左往右滑动 上一个月
	public void leftSlide() {
		if (mShowDate.month == 1) {
			mShowDate.month = 12;
			mShowDate.year -= 1;
		} else {
			mShowDate.month -= 1;
		}
		update();
	}

	// 从右往左边滑 下一个月
	public void rightSlide() {
		if (mShowDate.month == 12) {
			mShowDate.month = 1;
			mShowDate.year += 1;
		} else {
			mShowDate.month += 1;
		}
		update();
	}

	public void update() {
		fillDate();
		invalidate();
	}

}



//import android.content.Context;
//import android.graphics.Canvas;
//
//import android.util.AttributeSet;
//import android.view.View;
//
//public class CalendarCard extends View {
//
//	 private static final int TOTAL_COL = 7; // 7列  
//	 private static final int TOTAL_ROW = 6; // 6行 
//	 private OnCellClickListener mOnCellClickListener;
//	 private static  CustomData mShowDate; 
//	 
//	 public interface OnCellClickListener {  
//		    void clickDate(CustomData date); // 回调点击的日期  
//		    
//	        void changeDate(CustomData date); // 回调滑动ViewPager改变的日期  
//		     }
//
//	 
//
//
//	
//	public CalendarCard(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		// TODO Auto-generated constructor stub
//		init(context);  
//	}
//
//	public CalendarCard(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		// TODO Auto-generated constructor stub
//		init(context);  
//	}
//
//	public CalendarCard(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//		init(context);  
//	}
//
//	public CalendarCard(Context context,OnCellClickListener listener) {
//		super(context);
//		this.mOnCellClickListener = listener;
//		init(context);  
//	}
//
//	private void init(Context context){
//		
//		initDate();
//	}
//	private void initDate(){
//		mShowDate= new CustomData();
//		fillDate();
//	}
//    private void fillDate(){
//    	 int monthDay = DateUtil.getCurrentMonthDay(); // 今天  
//    	 int lastMonthDays = DateUtil.getMonthDays(mShowDate.year,  
//                     mShowDate.month - 1); // 上个月的天数  
//         int currentMonthDays = DateUtil.getMonthDays(mShowDate.year,  
//    	           mShowDate.month); // 当前月的天数  
//    	 int firstDayWeek = DateUtil.getWeekDayFromDate(mShowDate.year,  
//    	        mShowDate.month);  
//         boolean isCurrentMonth = false;  
//         if (DateUtil.isCurrentMonth(mShowDate)) {  
//    	            isCurrentMonth = true;  
//    	      }  
//        int day = 0;  
//    	 for (int j = 0; j < TOTAL_ROW; j++) {  
//                rows[j] = new Row(j);  
//    	       for (int i = 0; i < TOTAL_COL; i++) {  
//    	                 int position = i + j * TOTAL_COL; // 单元格位置  
//    	               // 这个月的  
//    	                if (position >= firstDayWeek  
//    	                        && position < firstDayWeek + currentMonthDays) {  
//                     day++;  
//    	                   rows[j].cells[i] = new Cell(CustomDate.modifiDayForObject(  
//    	                          mShowDate, day), State.CURRENT_MONTH_DAY, i, j);  
//    	                  // 今天  
//                     if (isCurrentMonth && day == monthDay ) {  
//                          CustomDate date = CustomDate.modifiDayForObject(mShowDate, day);  
//    	                       rows[j].cells[i] = new Cell(date, State.TODAY, i, j);  
//                     }  
//    	 
//    	                   if (isCurrentMonth && day > monthDay) { // 如果比这个月的今天要大，表示还没到  
//    	                        rows[j].cells[i] = new Cell(  
//    	                             CustomDate.modifiDayForObject(mShowDate, day),  
//    	                             State.UNREACH_DAY, i, j);  
//    	                   }  
//   
//    	                  // 过去一个月  
//                    } else if (position < firstDayWeek) {  
//    	                   rows[j].cells[i] = new Cell(new CustomDate(mShowDate.year,  
//                              mShowDate.month - 1, lastMonthDays  
//    	                                   - (firstDayWeek - position - 1)),  
//    	                           State.PAST_MONTH_DAY, i, j);  
//    	                    // 下个月  
//    	                } else if (position >= firstDayWeek + currentMonthDays) {  
//    	                    rows[j].cells[i] = new Cell((new CustomDate(mShowDate.year,  
//    	                            mShowDate.month + 1, position - firstDayWeek  
//    	                                   - currentMonthDays + 1)),  
//    	                            State.NEXT_MONTH_DAY, i, j);  
//    	               }  
//    	            }  
//    	        }  
//    	        mCellClickListener.changeDate(mShowDate);  
//
//    }
//    
//    class Row{
//    	public int j;
//
//		Row(int j){
//    		this.j = j;
//    	}
//		
//		cells = new Cell[TOTAL_COL];
//		
//		public void drawCells(Canvas canvas) {
//
//			for (int i = 0; i < cells.length; i++) {
//				
//			}
//			}
//		}
//    class Cell{
//    	
//    	public CustomData date;
//		public State state; 
//        public int i;
//        public int j;
//		public Cell(CustomData date,State state, int i, int j){
//    		super();
//    		this.date = date;
//    		this.state = state;
//    		this.i=i;
//    		this.j=j;
//    	}
//    }
//    
//    
//   enum State{
//	   TODAY,CURRENT_MONTH_DAY, PAST_MONTH_DAY, NEXT_MONTH_DAY, UNREACH_DAY; 
//   }
//}

