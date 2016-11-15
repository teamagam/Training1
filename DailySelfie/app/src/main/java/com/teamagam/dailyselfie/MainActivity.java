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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PictureAdapter mPictureAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPictureAdapter = new PictureAdapter(this, getData());
        mRecyclerView = (RecyclerView) findViewById(R.id.pictures_recycler);
        mRecyclerView.setAdapter(mPictureAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<PictureInfo> getData() {
        Bitmap bm = BitmapFactory.decodeResource( getResources(), R.mipmap.ic_launcher);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = new File(storageDir, "ic_launcher.PNG");
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            Log.e("DailySelfieTag", "FAILED");
        }
        String sampleImagePath = file.getAbsolutePath();
        String sampleImageFileName = file.getName();

        List<PictureInfo> data = new ArrayList<>();
        PictureInfo pic = new PictureInfo();
        pic.mPath = sampleImagePath;
        pic.mFileName = sampleImageFileName;
        data.add(pic);

        return data;
    }

}
