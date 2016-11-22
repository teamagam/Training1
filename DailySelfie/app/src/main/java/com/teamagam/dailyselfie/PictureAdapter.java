package com.teamagam.dailyselfie;

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
    private int mImageSize;

    PictureAdapter(Context context, List<PictureInfo> pictureInfoList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mPictureInfoList = pictureInfoList;
        mImageSize = mContext
                .getApplicationContext()
                .getResources()
                .getInteger(R.integer.activity_main_thumbnail_size);
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_picture, parent, false);
        return new PictureViewHolder(view, mImageSize);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder pictureViewHolder, int position) {
        pictureViewHolder.setPictureInfo(mPictureInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPictureInfoList.size();
    }

    private void enlargePicture(String path) {
        new EnlargePictureDialog(mContext, path).show();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;
        private LoadThumbnailTask mLoadThumbnailTask;
        private int mImageSize;

        PictureViewHolder(View itemView, int imageSize) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.ivItemListImage);
            mTextView = (TextView) itemView.findViewById(R.id.tvItemListImage);
            mImageSize = imageSize;
        }

        void setPictureInfo(PictureInfo pictureInfo) {
            cancelLoadThumbnailTask();
            setText(pictureInfo.fileName);
            setPicture(pictureInfo.path);
        }

        private void cancelLoadThumbnailTask() {
            if (null != mLoadThumbnailTask) {
                mLoadThumbnailTask.cancel(true);
            }
        }

        private void setText(String fileName) {
            mTextView.setText(fileName);
        }

        private void setPicture(String path) {
            mImageView.setVisibility(View.INVISIBLE);
            mLoadThumbnailTask = new LoadThumbnailTask(mImageView, mImageSize);
            mLoadThumbnailTask.execute(path);
            setEnlargePictureCLickListener(path);
        }

        private void setEnlargePictureCLickListener(final String path) {
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enlargePicture(path);
                }
            });
        }

    }
}
