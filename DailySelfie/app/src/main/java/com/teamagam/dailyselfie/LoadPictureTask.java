package com.teamagam.dailyselfie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

class LoadPictureTask extends AsyncTask<String, Void, Bitmap> {
    private final Context mContext;
    private final WeakReference<ImageView> mImageViewReference;

    LoadPictureTask(Context context, ImageView imageView) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        mContext = context;
        mImageViewReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String path = params[0];
        int thumbnailSize = mContext.getResources().getInteger(R.integer.thumbnail_size);
        return decodeSampledBitmapFromFile(path, thumbnailSize, thumbnailSize);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (mImageViewReference != null && bitmap != null) {
            final ImageView imageView = mImageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
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