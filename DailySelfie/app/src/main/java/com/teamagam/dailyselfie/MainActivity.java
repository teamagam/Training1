package com.teamagam.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int DAILY_NOTIFICATION_ID = 0;

    private static final int REQUEST_TAKE_PHOTO = 1;

    private PictureAdapter mPictureAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
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
            File pictureFile = null;
            try {
                pictureFile = createImageFile();
            } catch (IOException e) {
                Toast.makeText(this, R.string.activity_main_ioexception, Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (pictureFile != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.teamagam.fileprovider",
                            pictureFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                } else {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pictureFile));
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
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(storageDir, imageFileName);
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

    private Notification buildDailyNotification() {
        Notification.Builder notificationBuilder = new Notification.Builder(
                getApplicationContext())
                .setTicker(getString(R.string.notification_daily_text_ticker))
                .setSmallIcon(R.drawable.ic_add_a_photo_black_24dp)
                .setAutoCancel(true)
                .setContentTitle(getString(R.string.notification_daily_title_content))
                .setContentText(getString(R.string.notification_daily_text_content))
                .setContentIntent(createDailySelfiePendingIntent());

        return notificationBuilder.build();
    }

    private PendingIntent createDailySelfiePendingIntent() {
        Intent dailySelfieIntent = new Intent(getApplicationContext(),
                MainActivity.class);
        return PendingIntent.getActivity(getApplicationContext(), DAILY_NOTIFICATION_ID,
                dailySelfieIntent, 0);
    }

    private void notifyNotification(Notification notification) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(DAILY_NOTIFICATION_ID, notification);
    }

}
