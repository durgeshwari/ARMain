package com.ArUndigit.ARundigit.Library;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ArUndigit.ARundigit.DetailActivity;
import com.ArUndigit.ARundigit.R;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private ArrayList<ImageViewList> imgArrayList;
    private Context context;

    public ImageAdapter(Context context, ArrayList<ImageViewList> imgArrayList) {
        this.imgArrayList = imgArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.image_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        setUpData(viewHolder,i);
    }


    private void setUpData(ViewHolder holder, int position) {

        ImageViewList imageViewList=imgArrayList.get(position);
        File imgFile = new File(imageViewList.getFilname());
        holder.textView.setText(imageViewList.getFilname());
        String path = imageViewList.getUri();
        Bitmap myBitmap = BitmapFactory.decodeFile(path);
        holder.imageView.setImageBitmap(myBitmap);
        Log.d("file2",path);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onBindViewHolder: called.");


                        Log.d(TAG, "onClick: " + imgArrayList.get(position));

                       // Toast.makeText(context, imgArrayList.get(position), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("image_url",imgArrayList.get(position).getUri());
                        context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(holder.getAdapterPosition());

            }
        });
            }
    public void removeItem(int position){
        imgArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, imgArrayList.size());
    }



    @Override
    public int getItemCount() {
        return imgArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img);
            textView=itemView.findViewById(R.id.textv);
            delete=itemView.findViewById(R.id.deletebtn);

        }
    }
}
