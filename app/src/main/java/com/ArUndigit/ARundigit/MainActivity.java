package com.ArUndigit.ARundigit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.FileOutputStream;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG="ss";


    Button ss_btn,home_btn ;


    private ArFragment arFragment;
        private ModelRenderable chairRenderable,
                                tableRenderable,
                                sofaRenderable,
                                bedRenderable,
                                credenzaRenderable,
                                deskRenderable,
                                sidetableRenderable,
                                couchRenderable,
                                lampRenderable,
                                tvRenderable,
                                bookcaseRenderable;

        ImageView chair_img, table_img, sofa_img,bed_img,credenza_img,desk_img,sidetable_img,couch_img,lamp_img,tv_img,bookcase_img;
        View arrayview[];

        int selected = 1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

           ss_btn = (Button) findViewById(R.id.ss_btn);
            final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.rl);

            ss_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isWriteStoragePermissionGranted();
                    // Take the screenshot

                    Bitmap screenShot = TakeScreenShot(frameLayout);

                    if(screenShot!=null){
                        MediaStore.Images.Media.insertImage(getContentResolver(),screenShot,
                                "IMAGE","Captured Screenshot");

                    }

                    Toast.makeText(getApplicationContext(), "Screen Captured.",Toast.LENGTH_SHORT).show();
                }
            });

            home_btn=(Button)findViewById(R.id.home_btn);
            home_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(MainActivity.this,homeActivity.class);

                    startActivity(intent);
                }
            });



            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

            //view
            chair_img = (ImageView) findViewById(R.id.chair);
            table_img = (ImageView) findViewById(R.id.table);
            sofa_img = (ImageView) findViewById(R.id.sofa);
            bed_img = (ImageView) findViewById(R.id.bed);
            credenza_img = (ImageView) findViewById(R.id.credenza);
            desk_img = (ImageView) findViewById(R.id.desk);
            sidetable_img = (ImageView) findViewById(R.id.sidetable);
            couch_img=(ImageView)findViewById(R.id.couch);
            lamp_img = (ImageView) findViewById(R.id.lamp);
            tv_img = (ImageView) findViewById(R.id.tv);
            bookcase_img = (ImageView) findViewById(R.id.bookcase);


            setArrayView();
            setClickListener();
            setupModel();

            arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
                @Override
                public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {

                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());

                    createModel(anchorNode, selected);

                }
            });
        }

        private boolean isWriteStoragePermissionGranted() {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission is granted2");
                    return true;
                } else {

                    Log.v(TAG, "Permission is revoked2");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                    return false;
                }
            } else {
                //permission is automatically granted on sdk<23 upon installation
                //sdk <23 ise otomatik olarak izin verilir
                Log.v(TAG, "Permission is granted2");
                return true;
            }
        }

        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case 2:
                    Log.d(TAG, "External storage2");
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                        // resume tasks needing this permission


                    } else {
                    }
                    break;

                case 3:
                    Log.d(TAG, "External storage1");
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                        //resume tasks needing this permission

                    } else {
                    }
                    break;
            }}

        // Custom method to take screenshot
        public Bitmap TakeScreenShot(View rootView)
        {

            Bitmap bitmap = Bitmap.createBitmap(rootView.getWidth(),rootView.getHeight(), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            rootView.draw(canvas);
            return bitmap;
        }


        private void setArrayView() {
            arrayview = new View[]{ chair_img, table_img, sofa_img,bed_img,credenza_img,desk_img,sidetable_img,couch_img,lamp_img,tv_img,bookcase_img};

        }

        private void setClickListener() {

            for (int i = 0; i < arrayview.length; i++) {
                arrayview[i].setOnClickListener(MainActivity.this);
            }
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.chair) {
                Log.d("selected", "one");
                selected = 1;
                setBackground(view.getId());
            } else if (view.getId() == R.id.table) {
                Log.d("selected", "two");
                selected = 2;
                setBackground(view.getId());
            } else if (view.getId() == R.id.sofa) {
                Log.d("selected", "three");
                selected = 3;
                setBackground(view.getId());
            }else if (view.getId() == R.id.bed) {
                Log.d("selected", "4");
                selected = 4;
                setBackground(view.getId());
            }
            else if (view.getId() == R.id.credenza) {
                Log.d("selected", "5");
                selected = 5;
                setBackground(view.getId());
            }
            else if (view.getId() == R.id.desk) {
                Log.d("selected", "6");
                selected = 6;
                setBackground(view.getId());
            }
            else if (view.getId() == R.id.sidetable) {
                Log.d("selected", "7");
                selected = 7;
                setBackground(view.getId());
            }
            else if (view.getId() == R.id.couch) {
                Log.d("selected", "8");
                selected = 8;
                setBackground(view.getId());
            }
            else if (view.getId() == R.id.lamp) {
                Log.d("selected", "9");
                selected = 9;
                setBackground(view.getId());
            }
            else if (view.getId() == R.id.tv) {
                Log.d("selected", "10");
                selected = 10;
                setBackground(view.getId());
            }else if (view.getId() == R.id.bookcase) {
                Log.d("selected", "11");
                selected = 11;
                setBackground(view.getId());
            }

        }


        private void setBackground(int id) {
            for (int i = 0; i < arrayview.length; i++) {

                if (arrayview[i].getId() == id)
                    arrayview[i].setBackgroundColor(Color.parseColor("#999999"));
                else
                    arrayview[i].setBackgroundColor(Color.TRANSPARENT);
            }
        }

        private void createModel(AnchorNode anchorNode, int selected) {

            if (selected == 1) {
            Log.d("placed", "one");
            TransformableNode chair = new TransformableNode(arFragment.getTransformationSystem());
            chair.setParent(anchorNode);
            chair.setRenderable(chairRenderable);
            chair.select();

            addname(anchorNode, chair, "Chair");
            }
        if (selected == 2) {
            Log.d("placed", "two");
            TransformableNode table = new TransformableNode(arFragment.getTransformationSystem());
            table.setParent(anchorNode);
            table.setRenderable(tableRenderable);
            table.select();

            addname(anchorNode, table, "table");
             }
        if (selected == 3) {

            Log.d("placed", "three");
            TransformableNode sofa = new TransformableNode(arFragment.getTransformationSystem());
            sofa.setParent(anchorNode);
            sofa.setRenderable(sofaRenderable);
            sofa.select();

            addname(anchorNode, sofa, "sofa");
             }


            if (selected == 4) {
                Log.d("placed", "one");
                TransformableNode bedrooom = new TransformableNode(arFragment.getTransformationSystem());
                bedrooom.setParent(anchorNode);
                bedrooom.setRenderable(bedRenderable);
                bedrooom.select();

                addname(anchorNode,bedrooom, "bed");
            }
            if (selected == 5) {
                Log.d("placed", "one");
                TransformableNode credenza = new TransformableNode(arFragment.getTransformationSystem());
                credenza .setParent(anchorNode);
                credenza .setRenderable(credenzaRenderable);
                credenza.select();

                addname(anchorNode, credenza, " credenza");
            }


            if (selected == 6) {
                Log.d("placed", "one");
                TransformableNode desk = new TransformableNode(arFragment.getTransformationSystem());
                desk.setParent(anchorNode);
               desk.setRenderable(deskRenderable);
               desk.select();

                addname(anchorNode, desk, "desk");
            }

            if (selected == 7) {
                Log.d("placed", "one");
                TransformableNode sidetable = new TransformableNode(arFragment.getTransformationSystem());
               sidetable.setParent(anchorNode);
                sidetable.setRenderable(sidetableRenderable);
              sidetable.select();

                addname(anchorNode,sidetable, "sidetable");
            }

            if (selected == 8) {
                Log.d("placed", "one");
                TransformableNode sofatwo = new TransformableNode(arFragment.getTransformationSystem());
               sofatwo.setParent(anchorNode);
                sofatwo.setRenderable(couchRenderable);
                sofatwo.select();

                addname(anchorNode,sofatwo, "Couch");
            }

            if (selected == 9) {
                Log.d("placed", "one");
                TransformableNode lamp = new TransformableNode(arFragment.getTransformationSystem());
                lamp.setParent(anchorNode);
                 lamp.setRenderable(lampRenderable);
               lamp.select();

                addname(anchorNode,lamp, "lamp");
            }

            if (selected ==10) {
                Log.d("placed", "one");
                TransformableNode tv = new TransformableNode(arFragment.getTransformationSystem());
                tv.setParent(anchorNode);
                tv.setRenderable(tvRenderable);
                tv.select();

                addname(anchorNode, tv, "tv");
            }
            if (selected ==11) {
                Log.d("placed", "one");
                TransformableNode bookcase = new TransformableNode(arFragment.getTransformationSystem());
                bookcase.setParent(anchorNode);
                bookcase.setRenderable(bookcaseRenderable);
                bookcase.select();

                addname(anchorNode, bookcase, "bookcase");
            }
        }


        private void setupModel() {

            ModelRenderable.builder()
                    .setSource(this, Uri.parse("CHAHIN_WOODEN_CHAIR.sfb"))
                    .build()
                    .thenAccept(renderable -> chairRenderable = renderable)
                    .exceptionally(throwable -> {
                        Toast toast =
                                Toast.makeText(this, "Unable to load chair renderable", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return null;
                    });

            ModelRenderable.builder()
                    .setSource(this, Uri.parse("Table_Large_Rectangular_01.sfb"))
                    .build()
                    .thenAccept(renderable -> tableRenderable = renderable)
                    .exceptionally(throwable -> {
                        Toast toast =
                                Toast.makeText(this, "Unable to load table renderable", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return null;
                    });


            ModelRenderable.builder()
                    .setSource(this, Uri.parse("model.sfb"))
                    .build()
                    .thenAccept(renderable -> sofaRenderable = renderable)
                    .exceptionally(throwable -> {
                        Toast toast =
                                Toast.makeText(this, "Unable to load sofa renderable", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return null;
                    });
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("Bedroom.sfb"))
                    .build()
                    .thenAccept(renderable -> bedRenderable = renderable)
                    .exceptionally(throwable -> {
                        Toast toast =
                                Toast.makeText(this, "Unable to load bed renderable", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return null;
                    });
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("Credenza.sfb"))
                    .build()
                    .thenAccept(renderable -> credenzaRenderable = renderable)
                    .exceptionally(throwable -> {
                        Toast toast =
                                Toast.makeText(this, "Unable to load credenza renderable", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return null;
                    });

            ModelRenderable.builder()
                    .setSource(this, Uri.parse("Desk_01.sfb"))
                    .build()
                    .thenAccept(renderable -> deskRenderable = renderable)
                    .exceptionally(throwable -> {
                        Toast toast =
                                Toast.makeText(this, "Unable to load desk renderable", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return null;
                    });

            ModelRenderable.builder()
                    .setSource(this, Uri.parse("sidetable.sfb"))
                    .build()
                    .thenAccept(renderable -> sidetableRenderable = renderable)
                    .exceptionally(throwable -> {
                        Toast toast =
                                Toast.makeText(this, "Unable to load sidetable renderable", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return null;
                    });

            ModelRenderable.builder()
                    .setSource(this, Uri.parse("Sofa_01.sfb"))
                    .build()
                    .thenAccept(renderable -> chairRenderable = renderable)
                    .exceptionally(throwable -> {
                        Toast toast =
                                Toast.makeText(this, "Unable to load couch renderable", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return null;
                    });

            ModelRenderable.builder()
                    .setSource(this, Uri.parse("Standing_lamp_01.sfb"))
                    .build()
                    .thenAccept(renderable -> lampRenderable = renderable)
                    .exceptionally(throwable -> {
                        Toast toast =
                                Toast.makeText(this, "Unable to load lamp renderable", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return null;
                    });

            ModelRenderable.builder()
                    .setSource(this, Uri.parse("TV_01(1).sfb"))
                    .build()
                    .thenAccept(renderable -> tvRenderable = renderable)
                    .exceptionally(throwable -> {
                        Toast toast =
                                Toast.makeText(this, "Unable to tv  renderable", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return null;
                    });
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("Armoire.sfb"))
                    .build()
                    .thenAccept(renderable -> bookcaseRenderable = renderable)
                    .exceptionally(throwable -> {
                        Toast toast =
                                Toast.makeText(this, "Unable to load bookcase renderable", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return null;
                    });



        }



        private void addname(AnchorNode anchorNode, TransformableNode model, String name) {

            ViewRenderable.builder()
                    .setView(this, R.layout.name_furniture)
                    .build()
                    .thenAccept(viewRenderable -> {

                        TransformableNode nameView = new TransformableNode(arFragment.getTransformationSystem());
                        nameView.setLocalPosition(new Vector3(0f, model.getLocalPosition().y + 1.0f, 0));
                        nameView.setParent(anchorNode);
                        nameView.setRenderable(viewRenderable);
                        nameView.select();

                        //setText

                        TextView txt_name = (TextView) viewRenderable.getView();
                        txt_name.setText(name);

                        txt_name.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                anchorNode.setParent(null);

                            }

                        });
                    });
        }
    }
