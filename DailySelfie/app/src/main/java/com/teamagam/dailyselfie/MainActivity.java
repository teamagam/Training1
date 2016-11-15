package com.teamagam.dailyselfie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PictureAdapter mPictureAdapter;
    private RecyclerView mRecyclerView;
    private List<PictureInfo> mPictureInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPictureInfoList = new ArrayList<>();
        mPictureAdapter = new PictureAdapter(this, mPictureInfoList);
        mRecyclerView = (RecyclerView) findViewById(R.id.pictures_recycler);
        mRecyclerView.setAdapter(mPictureAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
