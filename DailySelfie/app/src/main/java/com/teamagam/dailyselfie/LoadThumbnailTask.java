package com.teamagam.dailyselfie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.widget.ImageView;

class LoadThumbnailTask extends LoadPictureTask {
    private final int mImageSize;

    LoadThumbnailTask(ImageView imageView, int imageSize) {
        super(imageView);
        mImageSize = imageSize;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String path = params[0];
        return decodeSampledBitmapFromFile(path, mImageSize, mImageSize);
    }

    private static Bitmap decodeSampledBitmapFromFile(String path, int requiredWidth, int requiredHeight) {
        final Options options = new Options();
        justDecodeBounds(path, options);
        calculateSampleSize(options, requiredWidth, requiredHeight);
        return decodeCompleteFile(path, options);
    }

    private static Bitmap decodeCompleteFile(String path, Options options) {
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private static void justDecodeBounds(String path, Options options) {
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
    }

    private static void calculateSampleSize(Options options, int requiredWidth, int requiredHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int sampleSize = 1;

        if (height > requiredHeight || width > requiredWidth) {

            final int newHeight = height / 2;
            final int newWidth = width / 2;

            // Calculate the largest sampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((newHeight / sampleSize) >= requiredHeight
                    && (newWidth / sampleSize) >= requiredWidth) {
                sampleSize *= 2;
            }
        }

        options.inSampleSize = sampleSize;
    }
}