package com.teamagam.dailyselfie;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_take_picture:
                dispatchTakePictureIntent();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(this, R.string.activity_main_ioexception, Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.teamagam.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                } else {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile);
                }
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Toast.makeText(this, getString(R.string.activity_main_picture_taken), Toast.LENGTH_LONG).show();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
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
