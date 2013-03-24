package com.egorand.scrollableimageview;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.OverScroller;

/**
 * An extension to the standard Android {@link ImageView}, which makes it respond to Scroll and Fling events. Uses a
 * {@link GestureDetectorCompat} and a {@link OverScroller} to provide scrolling functionality.
 * 
 * @author eandreevici
 * 
 */
public class ScrollableImageView extends ImageView {

	private GestureDetectorCompat gestureDetector;
	private OverScroller overScroller;

	private final int screenW;
	private final int screenH;

	private int positionX = 0;
	private int positionY = 0;

	public ScrollableImageView(Context context) {
		super(context);

		DisplayMetrics dm = getResources().getDisplayMetrics();
		screenW = dm.widthPixels;
		screenH = dm.heightPixels;

		gestureDetector = new GestureDetectorCompat(context, gestureListener);
		overScroller = new OverScroller(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gestureDetector.onTouchEvent(event);
		return true;
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (overScroller.computeScrollOffset()) {
			positionX = overScroller.getCurrX();
			positionY = overScroller.getCurrY();
			scrollTo(positionX, positionY);
		} else {
			overScroller.springBack(positionX, positionY, 0,
					(getDrawable().getBounds().width() - screenW), 0, (getDrawable().getBounds()
							.height() - screenH));
		}
	}

	private SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {

		@Override
		public boolean onDown(MotionEvent e) {
			overScroller.forceFinished(true);
			ViewCompat.postInvalidateOnAnimation(ScrollableImageView.this);
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			overScroller.forceFinished(true);
			overScroller.fling(positionX, positionY, (int) -velocityX, (int) -velocityY, 0,
					(getDrawable().getBounds().width() - screenW), 0, (getDrawable().getBounds()
							.height() - screenH));
			ViewCompat.postInvalidateOnAnimation(ScrollableImageView.this);
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			overScroller.forceFinished(true);
			overScroller.startScroll(positionX, positionY, (int) distanceX, (int) distanceY, 0);
			ViewCompat.postInvalidateOnAnimation(ScrollableImageView.this);
			return true;
		}
	};

}
