package com.example.camera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image=findViewById(R.id.imagee);
    }
final int REQUEST_IMAGE_CAPTURE=1;
    public void capture(View view) {
        Intent in =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (in.resolveActivity(getPackageManager())!=null){
            startActivityForResult(in,REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK){
            Bundle exter=data.getExtras();
            Bitmap bitmap = (Bitmap) exter.get("data");
            image.setImageBitmap(bitmap);
            try {
                createfile();
                savetogallery();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    String currentpath;

    private File createfile()throws IOException{

String time=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
String filename="JPEG"+time+"_";
        File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File tempFile = File.createTempFile(filename, ".jpg", storage);
        currentpath="file:"+tempFile.getAbsolutePath();
        return tempFile;
    }

   private void savetogallery(){

        Intent in =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f =new File(currentpath);
       Uri uri=Uri.fromFile(f);
       in.setData(uri);
       this.sendBroadcast(in);
   }

    public void gallery(View view) {
        Intent in =new Intent(this,second.class);
        startActivity(in);
    }
}


