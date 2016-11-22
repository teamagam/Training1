package com.teamagam.dailyselfie;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ImageView;

class EnlargePictureDialog extends Dialog {

    private String mPath;
    private ImageView mImageView;

    EnlargePictureDialog(Context context, String path) {
        super(context);
        mPath = path;
        setContentView();
    }

    private void setContentView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_dialog_picture_enlarge);
        mImageView = (ImageView) findViewById(R.id.fragment_dialog_large_image);
    }

    @Override
    public void show() {
        super.show();
        new LoadPictureTask(mImageView).execute(mPath);
    }
}
