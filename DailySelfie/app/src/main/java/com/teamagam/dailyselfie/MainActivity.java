package com.teamagam.dailyselfie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PictureAdapter mPictureAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mPictureAdapter = new PictureAdapter(this, loadPictureInfoList());
        mRecyclerView = (RecyclerView) findViewById(R.id.pictures_recycler);
        mRecyclerView.setAdapter(mPictureAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<PictureInfo> loadPictureInfoList() {
        List<PictureInfo> pictureInfoList = new ArrayList<>();
        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (null != externalFilesDir) {
            File[] files = externalFilesDir.listFiles();
            if (null != files) {
                for (File file : files) {
                    PictureInfo pictureInfo = new PictureInfo();
                    pictureInfo.mPath = file.getAbsolutePath();
                    pictureInfo.mFileName = file.getName();
                    pictureInfoList.add(pictureInfo);
                }
            }
        }
        return pictureInfoList;
    }

}
