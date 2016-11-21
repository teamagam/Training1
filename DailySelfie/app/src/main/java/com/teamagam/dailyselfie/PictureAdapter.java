package com.teamagam.dailyselfie;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<PictureInfo> mPictureInfoList;

    PictureAdapter(Context context, List<PictureInfo> pictureInfoList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mPictureInfoList = pictureInfoList;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_picture, parent, false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder pictureViewHolder, int position) {
        final PictureInfo pictureInfo = mPictureInfoList.get(position);
        pictureViewHolder.mTextView.setText(pictureInfo.fileName);
        int imageSize = pictureViewHolder.mImageView
                .getContext()
                .getApplicationContext()
                .getResources()
                .getInteger(R.integer.activity_main_thumbnail_size);
        if (null != pictureViewHolder.mLoadThumbnailTask) {
            pictureViewHolder.mLoadThumbnailTask.cancel(true);
        }
        pictureViewHolder.mImageView.setVisibility(View.INVISIBLE);
        pictureViewHolder.mLoadThumbnailTask = new LoadThumbnailTask(pictureViewHolder.mImageView, imageSize);
        pictureViewHolder.mLoadThumbnailTask.execute(pictureInfo.path);
        pictureViewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enlargePicture(pictureInfo.path);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPictureInfoList.size();
    }

    private void enlargePicture(String path) {
        final Dialog enlargePictureDialog = new Dialog(mContext);
        enlargePictureDialog.setContentView(R.layout.layout_dialog_picture_enlarge);
        ImageView enlargePictureImageView = (ImageView) enlargePictureDialog.findViewById(R.id.fragment_dialog_large_image);
        new LoadPictureTask(enlargePictureImageView).execute(path);
        enlargePictureDialog.show();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        LoadThumbnailTask mLoadThumbnailTask;

        PictureViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.ivItemListImage);
            mTextView = (TextView) itemView.findViewById(R.id.tvItemListImage);
        }
    }
}
