package com.teamagam.dailyselfie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

class LoadThumbnailTask extends LoadPictureTask {
    private final int mImageSize;

    LoadThumbnailTask(ImageView imageView, int imageSize) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        super(imageView);
        mImageSize = imageSize;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String path = params[0];
        return decodeSampledBitmapFromFile(path, mImageSize, mImageSize);
    }

    private static Bitmap decodeSampledBitmapFromFile(String path, int requiredWidth, int requiredHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateSampleSize(options, requiredWidth, requiredHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private static int calculateSampleSize(Options options, int requiredWidth, int requiredHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int sampleSize = 1;

        if (height > requiredHeight || width > requiredWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest sampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / sampleSize) >= requiredHeight
                    && (halfWidth / sampleSize) >= requiredWidth) {
                sampleSize *= 2;
            }
        }

        return sampleSize;
    }
}