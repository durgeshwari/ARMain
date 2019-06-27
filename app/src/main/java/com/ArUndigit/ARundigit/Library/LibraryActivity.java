package com.ArUndigit.ARundigit.Library;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ArUndigit.ARundigit.R;

import java.io.File;
import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity {
    private RecyclerView recyclerViewRecordings;
    private ArrayList<ImageViewList> imageArraylist;
    private ImageAdapter imageAdapter;

    Bitmap bmp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        imageArraylist = new ArrayList<ImageViewList>();
        ImageView img = (ImageView) findViewById(R.id.image);


        recyclerViewRecordings = (RecyclerView) findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(LibraryActivity.this, 2);
        recyclerViewRecordings.setLayoutManager(mGridLayoutManager);
        recyclerViewRecordings.setAdapter(imageAdapter);

        fetchImages();
    }


    private void fetchImages() {
        File root = Environment.getExternalStorageDirectory();
        String path = root.getAbsolutePath()+"/Pictures/Sceneform";
        // File root = android.os.Environment.getExternalStorageDirectory();
        // String path = Environment.getExternalStorageDirectory().toString()+"/Pictures/Sceneform/";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: " + files.length);
        if (files != null) {

            for (int i = 0; i < files.length; i++) {

                Log.d("Files", "FileName:" + files[i].getName());
                String fileName = files[i].getName();
                String imageUri = root.getAbsolutePath() + "/Pictures/Sceneform/" + fileName;

                Log.d("files",imageUri);
                ImageViewList imageViewList = new ImageViewList(imageUri, fileName);
                imageArraylist.add(imageViewList);
            }

            recyclerViewRecordings.setVisibility(View.VISIBLE);
            setAdaptertoImageView();
        }
    }

    private void setAdaptertoImageView() {
        imageAdapter = new ImageAdapter(LibraryActivity.this,imageArraylist);
        recyclerViewRecordings.setAdapter(imageAdapter);
    }
}
