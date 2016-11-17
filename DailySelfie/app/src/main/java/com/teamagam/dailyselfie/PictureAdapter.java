package com.teamagam.dailyselfie;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {
    private final Context mContext;
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
        PictureInfo pictureInfo = mPictureInfoList.get(position);
        pictureViewHolder.mTextView.setText(pictureInfo.fileName);
        new LoadPictureTask(mContext ,pictureViewHolder.mImageView).execute(pictureInfo.path);
    }

    @Override
    public int getItemCount() {
        return mPictureInfoList.size();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        PictureViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.ivItemListImage);
            mTextView = (TextView) itemView.findViewById(R.id.tvItemListImage);
        }
    }
}
