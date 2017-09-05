package com.example.xiaf.mibao;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GalleryActivity extends AppCompatActivity  implements View.OnClickListener {

    private ImageView pic ;
    private Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        pic = (ImageView)findViewById(R.id.photo);
        save = (Button)findViewById(R.id.save);
        save.getBackground().setAlpha(80);

        save.setOnClickListener(this);

        Intent intent = getIntent();
        if(intent != null){
                Uri uri = Uri.parse(intent.getStringExtra("uri"));
                if(uri != null){
                   pic.setImageURI(uri);
                }
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.save:


                break;
            default:
                break;

        }
    }




}
