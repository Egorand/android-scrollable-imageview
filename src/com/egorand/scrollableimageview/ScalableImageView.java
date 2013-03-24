package com.egorand.scrollableimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.widget.ImageView;

public class ScalableImageView extends ImageView {

	private ScaleGestureDetector scaleGestureDetector;
	
	private float scaleFactor = 1.0f;
	
	public ScalableImageView(Context context) {
		super(context);
		
		scaleGestureDetector = new ScaleGestureDetector(context, scaleGestureListener);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		scaleGestureDetector.onTouchEvent(event);
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		canvas.scale(scaleFactor, scaleFactor);
		super.onDraw(canvas);
		canvas.restore();
	}
	
	private SimpleOnScaleGestureListener scaleGestureListener = new SimpleOnScaleGestureListener() {

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			scaleFactor *= detector.getScaleFactor();
			scaleFactor = Math.max(.1f, Math.min(scaleFactor, 5f));
			ViewCompat.postInvalidateOnAnimation(ScalableImageView.this);
			return true;
		};
	};
}
