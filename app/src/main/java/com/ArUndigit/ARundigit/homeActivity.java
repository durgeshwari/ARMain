package com.ArUndigit.ARundigit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ArUndigit.ARundigit.Library.LibraryActivity;
import com.skyfishjy.library.RippleBackground;

public class homeActivity extends AppCompatActivity {
    ImageView create,library;
    Animation frombottom;
    RippleBackground rippleBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashtohome);

        rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        create =(ImageView) findViewById(R.id.create_btn);
        library =(ImageView) findViewById(R.id.lib_btn);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(homeActivity.this,MainActivity.class);
                startActivity(intent);

            rippleBackground.stopRippleAnimation();

        }
        });
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(homeActivity.this, LibraryActivity.class);
                startActivity(intent);

            }
        });


    }
}
