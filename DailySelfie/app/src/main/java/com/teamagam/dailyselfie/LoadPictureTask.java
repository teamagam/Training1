package com.teamagam.dailyselfie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

class LoadPictureTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> mImageViewReference;
    final static String TAG = "DailySelfie";

    LoadPictureTask(ImageView imageView) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        mImageViewReference = new WeakReference<>(imageView);
    }


    @Override
    protected Bitmap doInBackground(String... params) {
        String path = params[0];


/*        Log.i(MainActivity.TAG, "Entered doInBackground");
        Bitmap bitmap;
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File(path)));
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            if (null != bitmap) {
                Log.i(MainActivity.TAG, "Bitmap loaded in doInBackground");
            } else {
                Log.i(MainActivity.TAG, "null loaded in doInBackground");
            }
        } catch (IOException e) {
            Log.i(MainActivity.TAG, "IOException in doInBackground");
            bitmap = null;
            //TODO
        }*/


        return decodeSampledBitmapFromFile(path, 64, 64);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap){
        if (mImageViewReference != null && bitmap != null) {
            final ImageView imageView = mImageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private static Bitmap decodeSampledBitmapFromFile(String path,
                                                      int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}