package com.teamagam.dailyselfie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PictureViewHolder> {
    private LayoutInflater inflater;
    private List<Picture> data = Collections.emptyList();

    PictureAdapter(Context context, List<Picture> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_picture, parent, false);
        PictureViewHolder holder = new PictureViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        Picture cur = data.get(position);
        holder.picture.setImageResource(cur.image);
        holder.pictureName.setText(cur.fileName);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        TextView pictureName;

        PictureViewHolder(View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.ivItemListImage);
            pictureName = (TextView) itemView.findViewById(R.id.tvItemListImage);
        }
    }
}
