package com.teamagam.dailyselfie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by user on 16/11/2016.
 */
public class LoadPictureTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView mImageView;

    public LoadPictureTask(ImageView imageView) {
        mImageView = imageView;
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        String path = params[0];
        Bitmap bitmap;
        try {
            InputStream inputStream = new FileInputStream(new File(path));
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            bitmap = null;
            //TODO
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap){
        mImageView.setImageBitmap(bitmap);
    }
}
