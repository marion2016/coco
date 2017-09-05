package com.example.xiaf.mibao;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.support.v4.content.FileProvider.getUriForFile;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button take;
    private Button watch;
    public static final int TAKE_PHOTO =1;
    private Uri imageUri;
    private File outputImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        take = (Button)findViewById(R.id.TakePhotoButton);
        watch = (Button)findViewById(R.id.WatchPhotoButton);
        take.setOnClickListener(this);
        watch.setOnClickListener(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.TakePhotoButton:
                gotoTakePhoto();
                break;
            case R.id.WatchPhotoButton:
                Intent watch = new Intent(MainActivity.this,AlbumActivity.class);
                startActivity(watch);
                break;
            case R.id.WatchVideo:
                break;
            default:
                break;
        }
    }

    private String getPhotopath() {

        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);

        // 照片全路径
        String fileName = "";

        // 文件夹路径
        String pathUrl = Constant.StoragePath+R.string.app_name;
        String imageName = time+R.string.postfix;

        File file = new File(pathUrl);
        file.mkdirs();// 创建文件夹
        fileName = pathUrl + imageName;
        return fileName;
    }

    public void gotoTakePhoto(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        File out = new File(getPhotopath());
        imageUri = Uri.fromFile(out);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,1);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
            //Bitmap bitmap = getBitmapFromUrl(getPhotopath(), 313.5, 462.0);
            //saveScalePhoto(bitmap);

            Intent intent = new Intent(MainActivity.this,GalleryActivity.class);
            intent.putExtra("uri",imageUri.toString());
            Log.i("IMAGE_URI",imageUri.toString());
            startActivity(intent);
        }
    }
//    private Bitmap getBitmapFromUrl(String url, double width, double height) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
//        Bitmap bitmap = BitmapFactory.decodeFile(url);
//        // 防止OOM发生
//        options.inJustDecodeBounds = false;
//        int mWidth = bitmap.getWidth();
//        int mHeight = bitmap.getHeight();
//        Matrix matrix = new Matrix();
//        float scaleWidth = 1;
//        float scaleHeight = 1;
//
//        // 按照固定宽高进行缩放
//        // 这里希望知道照片是横屏拍摄还是竖屏拍摄
//        // 因为两种方式宽高不同，缩放效果就会不同
//        // 这里用了比较笨的方式
//        if(mWidth <= mHeight) {
//            scaleWidth = (float) (width/mWidth);
//            scaleHeight = (float) (height/mHeight);
//        } else {
//            scaleWidth = (float) (height/mWidth);
//            scaleHeight = (float) (width/mHeight);
//        }
////        matrix.postRotate(90); /* 翻转90度 */
//        // 按照固定大小对图片进行缩放
//        matrix.postScale(scaleWidth, scaleHeight);
//        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight, matrix, true);
//        // 用完了记得回收
//        bitmap.recycle();
//        return newBitmap;
//    }
//    private void saveScalePhoto(Bitmap bitmap) {
//        // 照片全路径
//        String fileName = "";
//        // 文件夹路径
//        String pathUrl = Environment.getExternalStorageDirectory().getPath()+"/mymy/";
//        String imageName = "imageScale.jpg";
//        FileOutputStream fos = null;
//        File file = new File(pathUrl);
//        file.mkdirs();// 创建文件夹
//        fileName = pathUrl + imageName;
//        try {
//            fos = new FileOutputStream(fileName);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fos.flush();
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


//    @Override
//    protected void onActivityResult(int requestCode,int resultCode,Intent data){
//        FileOutputStream fos = null;
//        switch (requestCode){
//            case TAKE_PHOTO:
//                if(resultCode == RESULT_OK){
//                    try{
//
//                        String sdCardStatus = Environment.getExternalStorageState();
//                        if(!sdCardStatus.equals(Environment.MEDIA_MOUNTED)){
//                            Log.i("TestFile","SD Card is not available right now");
//                            return;
//                        }
//
//                        String name = System.currentTimeMillis() + ".png";
//
//
//                        Bundle bundle = data.getExtras();
//                        Bitmap bitmap = (Bitmap)bundle.get("data");
//
//
//                        String path = Environment.getExternalStorageDirectory().getPath()+"/Android/data/com.example.xiaf.mibao/files/";
//
//
//                        String filename = path+name;
//                        File file = new File(filename);
//                        if(!file.exists()){
//                            file.createNewFile();
//
//                        }
//
//                        Log.i("File Path",file.getPath().toString());
//                        Uri imageUri = Uri.fromFile(file);
//                        try{
//                            fos = new FileOutputStream(filename);
//                            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
//
//                        }catch(FileNotFoundException e){
//                            e.printStackTrace();
//                        }
//
//
//
//                        Intent intent = new Intent(MainActivity.this,GalleryActivity.class);
//                        intent.putExtra("uri",imageUri.toString());
//                        Log.i("IMAGE_URI",imageUri.toString());
//                        startActivity(intent);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }finally {
//                        try {
//                            if(fos != null){
//                                fos.flush();
//                                fos.close();
//                            }
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                break;
//        }
//    }
}
