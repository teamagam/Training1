package com.teamagam.dailyselfie;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PictureAdapter mPictureAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        for (int i = 0; i < 20; i++) {
            File file = new File(externalFilesDir, "pic" + String.valueOf(i) + ".jpg");
            if (i % 2 == 0) {
                copyFile("images/beer.jpg", file);
            } else {
                copyFile("images/Alcoholic-Drinks.jpg", file);
            }
        }

        if (externalFilesDir.isDirectory()) {
            File[] listOfFiles = externalFilesDir.listFiles();
            Toast.makeText(this, String.valueOf(listOfFiles.length) + " files were created", Toast.LENGTH_SHORT).show();
        }


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

    private void copyFile(String filename, File fileDest) {
        AssetManager assetManager = this.getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            out = new FileOutputStream(fileDest);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

}
