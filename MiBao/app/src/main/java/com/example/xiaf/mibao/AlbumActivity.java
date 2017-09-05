package com.example.xiaf.mibao;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;


import java.io.File;
import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {

    private ImageSwitcher imageswitch;
    private int position = 0;//当前显示图片位置


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        imageswitch = (ImageSwitcher) findViewById(R.id.imageswitch);

        imageswitch.setFactory(new ViewSwitcher.ViewFactory() {//设定工厂，每进来一个图片都用一个ImageView接收
            @Override
            public View makeView() {
                return new ImageView(AlbumActivity.this);
            }
        });


        imageswitch.setImageURI(getImage(position));
        imageswitch.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));//动画淡入
        imageswitch.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));//动画淡出
    }

    public void btn1(View v){
        if(position==0)
            position = getImagesPath("/storage/emulated/0/mymy/").size();
        imageswitch.setImageURI(getImage(--position));
    }
    public void btn2(View v){
        if(position==getImagesPath("/storage/emulated/0/mymy/").size()-1)//下一张到最后一张时把位置设到第一张
            position = -1;
        imageswitch.setImageURI(getImage(++position));
    }

    static ArrayList<String> getImagesPath(String path){
        ArrayList<String> filelist = new ArrayList<String>();
        File root = new File(path);
        File[] files = root.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                getImagesPath(file.getAbsolutePath());
                filelist.add(file.getAbsolutePath());
            } else {
                filelist.add(file.getAbsolutePath());
                Log.i("File Path :",file.getAbsolutePath());
            }
        }
       return filelist;
    }

    private Uri getImage(int position){

        ArrayList<String> imageList = new ArrayList<String>();
        imageList = getImagesPath("/storage/emulated/0/mibao/");
        Uri uri = Uri.parse(imageList.get(position).toString());
        return uri;
    }
}
