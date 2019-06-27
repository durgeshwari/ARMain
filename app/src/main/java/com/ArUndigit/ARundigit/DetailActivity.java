package com.ArUndigit.ARundigit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    ImageView mPlace;
    private static final String TAG = "GalleryActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        mPlace = findViewById(R.id.imageView);
        getIncomingIntent();
////
////        Intent intent=getIntent();
////        Bundle mBundle = getIntent().getExtras();
////        String path=
////        Bitmap myBitmap = BitmapFactory.decodeFile(path);
////        mPlace.setImageBitmap(myBitmap);
//
//        String path = getIntent().getExtras();
//        Log.d("detail",path);
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
//        mPlace.setImageBitmap(bitmap);


    }
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("image_url") ){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageUrl = getIntent().getStringExtra("image_url");
            setImage(imageUrl);
        }
    }


    private void setImage(String imageUrl){
        Log.d(TAG, "setImage: setting te image and name to widgets.");


        ImageView image = findViewById(R.id.imageView);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }

}

