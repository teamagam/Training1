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
    private LayoutInflater mInflater;
    private List<PictureInfo> mPictureInfoList;

    PictureAdapter(Context context, List<PictureInfo> pictureInfoList) {
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
        pictureViewHolder.setPictureInfo(mPictureInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPictureInfoList.size();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;
        private LoadThumbnailTask mLoadThumbnailTask;
        private int mImageSize;

        PictureViewHolder(View itemView) {
            super(itemView);
            //TODO solve this stupid issue
            mImageSize = mImageView
                    .getContext()
                    .getApplicationContext()
                    .getResources()
                    .getInteger(R.integer.activity_main_thumbnail_size);
            mImageView = (ImageView) itemView.findViewById(R.id.ivItemListImage);
            mTextView = (TextView) itemView.findViewById(R.id.tvItemListImage);
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
        }
    }
}
