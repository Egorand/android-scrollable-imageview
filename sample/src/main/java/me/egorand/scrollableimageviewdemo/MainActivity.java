package me.egorand.scrollableimageviewdemo;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;

import me.egorand.scrollableimageview.ScrollableImageView;

public class MainActivity extends Activity {

    private static final String DEBUG_TAG = "Training";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new BitmapLoaderTask().execute("Santiago_Bernabeu_Stadium.jpg");
    }

    private void setImageBitmap(Bitmap bmp) {
        ImageView imageView = new ScrollableImageView(this);
        imageView.setLayoutParams(new LayoutParams(bmp.getWidth(), bmp.getHeight()));
        imageView.setImageBitmap(bmp);
        ViewGroup container = (ViewGroup) findViewById(R.id.container);
        container.addView(imageView);
    }

    private class BitmapLoaderTask extends AsyncTask<String, Void, Bitmap> {

        private ProgressBar progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = (ProgressBar) findViewById(android.R.id.progress);
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
            progress.setVisibility(View.INVISIBLE);
            setImageBitmap(result);
        }
    }
}
