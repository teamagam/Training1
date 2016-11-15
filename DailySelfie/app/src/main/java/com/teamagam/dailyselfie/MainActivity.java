package com.teamagam.dailyselfie;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
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
        List<PictureInfo> pictureInfoList = Collections.emptyList();
        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (null != externalFilesDir) {
            File[] files = externalFilesDir.listFiles();
            if (null != files) {
                for (int i = 0; i < files.length; i++) {
                    PictureInfo pictureInfo = new PictureInfo();
                    pictureInfo.mPath = files[i].getPath();
                    pictureInfo.mFileName = files[i].getName();
                    pictureInfoList.add(pictureInfo);
                }
            }
        }
        return pictureInfoList;
    }

}
