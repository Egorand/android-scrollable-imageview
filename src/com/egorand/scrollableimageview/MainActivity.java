package com.egorand.scrollableimageview;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private static final String DEBUG_TAG = "Training";

	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.activity_main);

		new BitmapLoaderTask().execute("Santiago_Bernabeu_Stadium.jpg");
	}

	private void setImageBitmap(Bitmap bmp) {
		imageView = new ScalableImageView(this);
		imageView.setLayoutParams(new LayoutParams(bmp.getWidth(), bmp.getHeight()));
		imageView.setImageBitmap(bmp);
		ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
		root.addView(imageView);
	}

	private class BitmapLoaderTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			setProgressBarIndeterminateVisibility(true);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			AssetManager assets = getAssets();
			Bitmap bmp = null;
			try {
				bmp = BitmapFactory.decodeStream(assets.open(params[0]));
			} catch (IOException e) {
				Log.e(DEBUG_TAG, e.getMessage(), e);
			}
			return bmp;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			setProgressBarIndeterminateVisibility(false);
			setImageBitmap(result);
		}
	}
}
