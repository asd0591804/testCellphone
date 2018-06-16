package com.example.win7.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    public ImageView img;
    public Button CameraBtn;
    public Button upLoadbtn;

    DisplayMetrics mPhone;
    public final static int CAMERA = 66;
    public final static int PHOTO = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhone = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mPhone);

        img = findViewById(R.id.img);
        CameraBtn = findViewById(R.id.btn1);
        upLoadbtn = findViewById(R.id.btn2);

        CameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //利用function"checkSDcard"判斷有無SD卡
//                if(checkSDcard()){
//                    Toast.makeText(MainActivity.this,"SDCard ya",Toast.LENGTH_SHORT).show();
//                    //sd 存 SD卡絕對路徑
//                    String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
//                    //圖片的位置 /SD/001.jpg ,File.separator="/"
//                    String filepath = sd + File.separator+"001.jpg";
//
//                    //判斷圖片存不存在
//                    File file = new File(filepath);
//                    if(file.exists()){
//                        Bitmap bm = BitmapFactory.decodeFile(filepath);
//                        img.setImageBitmap(bm);
//                    }else{
//                        Toast.makeText(MainActivity.this,"fail find image",Toast.LENGTH_LONG).show();
//                    }
//
//                }else{
//                    //如果沒有SD卡則執行
//                    Toast.makeText(MainActivity.this,"no SDCard",Toast.LENGTH_LONG).show();
//                }
//                ContentValues value = new ContentValues();
//                value.put(MediaStore.Audio.Media.MIME_TYPE,"image/jpeg");
//                Uri uri = getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,value);
//
//                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT,uri.getPath());
//                startActivityForResult(intent,CAMERA);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PHOTO);
            }
        });
        upLoadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PHOTO);
            }
        });
    }

    //判斷有無SD卡
//    private static boolean checkSDcard(){
//        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
//            return true;
//        }else{
//            return false;
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Toast.makeText(MainActivity.this,"turn back",Toast.LENGTH_SHORT).show();
        if((requestCode ==CAMERA || requestCode ==PHOTO)&&data!=null){
//            Toast.makeText(MainActivity.this,"have file",Toast.LENGTH_SHORT).show();
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();

            try{
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                if(bitmap.getWidth()>bitmap.getHeight()){
                    ScalePic(bitmap,mPhone.heightPixels);

                }else{
                    ScalePic(bitmap,mPhone.widthPixels);

                }

            }catch (Exception e){

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private void ScalePic(Bitmap bitmap,int phone){
        float mScale = 1;

        if(bitmap.getWidth()>phone){
            mScale = (float)phone/(float)bitmap.getWidth();

            Matrix mMat = new Matrix();
            mMat.setScale(mScale,mScale);

            Bitmap mScaleBitmap = Bitmap.createBitmap(bitmap,0,0,
                    bitmap.getWidth(),bitmap.getHeight(),mMat,false);
            img.setImageBitmap(mScaleBitmap);
        }else{
            img.setImageBitmap(bitmap);
        }
    }

    //    合併路徑用
//    private static Bitmap getBitmapFromSDCard(String file){
//        try{
//            String sd = Environment.getExternalStorageDirectory().toString();
//            Bitmap bitmap = BitmapFactory.decodeFile(sd+"/"+file);
//            return bitmap;
//        }
//        catch(Exception e){
//
//            Log.e("message","THIS IS WRONG");
//            e.printStackTrace();
//            return null;
//        }
//    }
}
