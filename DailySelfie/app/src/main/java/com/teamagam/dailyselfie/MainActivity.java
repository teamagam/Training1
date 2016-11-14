package com.teamagam.dailyselfie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PictureAdapter pictureAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pictureAdapter = new PictureAdapter(this, getData()); //TODO
        recyclerView = (RecyclerView) findViewById(R.id.pictures_recycler);
        recyclerView.setAdapter(pictureAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<Picture> getData() {
        List<Picture> data = new ArrayList<>();
        int[] images = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
        String[] imagesNames = {"Text1", "Text2", "Text3"};

        for (int i = 0; i < images.length && i < imagesNames.length; i++) {
            Picture pic = new Picture();
            pic.image = images[i];
            pic.fileName = imagesNames[i];
            data.add(pic);
        }

        return data;
    }
}
