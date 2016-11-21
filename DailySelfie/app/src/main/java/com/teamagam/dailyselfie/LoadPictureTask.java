package com.teamagam.dailyselfie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

class LoadPictureTask extends AsyncTask<String, Void, Bitmap> {

    protected final WeakReference<ImageView> mImageViewReference;

    public LoadPictureTask() {
        super();
        mImageViewReference = null;
    }

    LoadPictureTask(ImageView imageView) {
        super();
        // Use a WeakReference to ensure the ImageView can be garbage collected
        mImageViewReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String path = params[0];
        return BitmapFactory.decodeFile(path);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (mImageViewReference.get() != null && bitmap != null) {
            final ImageView imageView = mImageViewReference.get();
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);
        }
    }

}