package com.teamagam.dailyselfie;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
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
        File[] files = getFilesListFromDir();
        for (File file : files) {
            pictureInfoList.add(pictureInfoFromFile(file));
        }
        return pictureInfoList;
    }

    private File[] getFilesListFromDir() {
        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (null != externalFilesDir) {
            return externalFilesDir.listFiles();
        } else {
            Toast.makeText(this, getString(R.string.activity_main_nullexception), Toast.LENGTH_SHORT).show();
            return new File[0];
        }
    }

    private PictureInfo pictureInfoFromFile(File file) {
        PictureInfo pictureInfo = new PictureInfo();
        pictureInfo.path = file.getAbsolutePath();
        pictureInfo.fileName = file.getName();
        return pictureInfo;
    }

}
