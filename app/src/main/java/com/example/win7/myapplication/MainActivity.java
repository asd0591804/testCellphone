package com.example.win7.myapplication;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public ImageView img;
    public Button SDbtn;
    public Button upLoadbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.img);
        SDbtn = findViewById(R.id.SDbutton);
        upLoadbtn = findViewById(R.id.upLoadbutton);

        SDbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //利用function"checkSDcard"判斷有無SD卡
                if(checkSDcard()){
                    Toast.makeText(MainActivity.this,"SDCard ya",Toast.LENGTH_SHORT).show();
                    //sd 存 SD卡絕對路徑
                    String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
                    //圖片的位置 /SD/001.jpg ,File.separator="/"
                    String filepath = sd + File.separator+"001.jpg";

                    //判斷圖片存不存在
                    File file = new File(filepath);
                    if(file.exists()){
                        Bitmap bm = BitmapFactory.decodeFile(filepath);
                        img.setImageBitmap(bm);
                    }else{
                        Toast.makeText(MainActivity.this,"fail find image",Toast.LENGTH_LONG).show();
                    }

                }else{
                    //如果沒有SD卡則執行
                    Toast.makeText(MainActivity.this,"no SDCard",Toast.LENGTH_LONG).show();
                }

            }
        });
        upLoadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageResource(R.drawable.penguins);
            }
        });
    }

    //判斷有無SD卡
    private static boolean checkSDcard(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
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
