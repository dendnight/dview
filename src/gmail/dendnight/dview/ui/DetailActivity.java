package gmail.dendnight.dview.ui;

import gmail.dendnight.dview.dict.DictParam;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 单个显示
 * 
 * <pre>
 * Description
 * Author:		dendnight
 * Version:		1.0  
 * Create at:	2013年10月24日 上午11:02:00  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */
@SuppressLint("DrawAllocation")
public class DetailActivity extends Activity {

	/*
	 * 利用多点触控来控制ImageView中图像的放大与缩小 手指控制图片移动
	 */

	private MyImageView imageView;
	private Bitmap bitmap;

	// 两点触屏后之间的长度
	private float beforeLenght;
	private float afterLenght;

	// 单点移动的前后坐标值
	private float afterX, afterY;
	private float beforeX, beforeY;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);// 隐藏状态栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		findView();
		setContentView(imageView);
		config();
	}

	private void findView() {
		imageView = new MyImageView(this);
		// 获得图片
		Intent intent = getIntent();
		String path = intent.getStringExtra(DictParam.PATH);

		bitmap = BitmapFactory.decodeFile(path);
	}

	private void config() {
		// 设置imageView的显示图片
		imageView.setImageBitmap(bitmap);
		// 设置图片填充ImageView
		imageView.setScaleType(ScaleType.FIT_XY);
	}

	// 创建一个自己的ImageView类
	class MyImageView extends ImageView {
		private float scale = 0.1f;

		public MyImageView(Context context) {
			super(context);
		}

		// 用来设置ImageView的位置
		private void setLocation(int x, int y) {
			this.setFrame(this.getLeft() + x, this.getTop() + y, this.getRight() + x, this.getBottom() + y);
		}

		/*
		 * 用来放大缩小ImageView 因为图片是填充ImageView的，所以也就有放大缩小图片的效果 flag为0是放大图片，为1是小于图片
		 */
		private void setScale(float temp, int flag) {

			if (flag == 0) {
				this.setFrame(this.getLeft() - (int) (temp * this.getWidth()),
						this.getTop() - (int) (temp * this.getHeight()),
						this.getRight() + (int) (temp * this.getWidth()),
						this.getBottom() + (int) (temp * this.getHeight()));
			} else {
				this.setFrame(this.getLeft() + (int) (temp * this.getWidth()),
						this.getTop() + (int) (temp * this.getHeight()),
						this.getRight() - (int) (temp * this.getWidth()),
						this.getBottom() - (int) (temp * this.getHeight()));
			}
		}

		// 绘制边框
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			Rect rec = canvas.getClipBounds();
			rec.bottom--;
			rec.right--;
			Paint paint = new Paint();
			paint.setColor(Color.RED);
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawRect(rec, paint);
		}

		/*
		 * 让图片跟随手指触屏的位置移动 beforeX、Y是用来保存前一位置的坐标 afterX、Y是用来保存当前位置的坐标
		 * 它们的差值就是ImageView各坐标的增加或减少值
		 */
		public void moveWithFinger(MotionEvent event) {

			switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN:
				beforeX = event.getX();
				beforeY = event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				afterX = event.getX();
				afterY = event.getY();

				this.setLocation((int) (afterX - beforeX), (int) (afterY - beforeY));

				beforeX = afterX;
				beforeY = afterY;
				break;

			case MotionEvent.ACTION_UP:
				break;
			}
		}

		/*
		 * 通过多点触屏放大或缩小图像 beforeLenght用来保存前一时间两点之间的距离 afterLenght用来保存当前时间两点之间的距离
		 */
		public void scaleWithFinger(MotionEvent event) {
			float moveX = event.getX(1) - event.getX(0);
			float moveY = event.getY(1) - event.getY(0);

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				beforeLenght = (float) Math.sqrt((moveX * moveX) + (moveY * moveY));
				break;
			case MotionEvent.ACTION_MOVE:
				// 得到两个点之间的长度
				afterLenght = (float) Math.sqrt((moveX * moveX) + (moveY * moveY));

				float gapLenght = afterLenght - beforeLenght;

				if (gapLenght == 0) {
					break;
				}

				// 如果当前时间两点距离大于前一时间两点距离，则传0，否则传1
				if (gapLenght > 0) {
					this.setScale(scale, 0);
				} else {
					this.setScale(scale, 1);
				}

				beforeLenght = afterLenght;
				break;
			}
		}

	}

	// 这里来监听屏幕触控时间
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		/*
		 * 判定用户是否触摸到了图片 如果是单点触摸则调用控制图片移动的方法 如果是2点触控则调用控制图片大小的方法
		 */
		if (event.getY() > imageView.getTop() && event.getY() < imageView.getBottom()
				&& event.getX() > imageView.getLeft() && event.getX() < imageView.getRight()) {
			if (event.getPointerCount() == 2) {
				imageView.scaleWithFinger(event);
			} else if (event.getPointerCount() == 1) {
				imageView.moveWithFinger(event);
			}
		}
		return true;
	}

}
