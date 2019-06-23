package com.example.camera;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class second extends AppCompatActivity {
    GridView list;
    ArrayList<piccontent> piclist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        list=findViewById(R.id.list);
       piclist =new ArrayList<piccontent>();
       String [] produce={
               MediaStore.Images.Media.DISPLAY_NAME,
               MediaStore.Images.Media.DATA
       };

        Uri allimage =MediaStore.Images.Media.INTERNAL_CONTENT_URI;
        Cursor cursor=managedQuery(allimage,produce,null,null,null);
        if (cursor.moveToFirst()){
String imagename;
            int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
            String collompath;
            do {
                imagename=cursor.getString(columnIndex);
                collompath=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                piclist.add(new piccontent(imagename,collompath));
            }while (cursor.moveToNext());

        }
        myadapter myadapter=new myadapter();
        list.setAdapter(myadapter);
    }
    class myadapter extends BaseAdapter{


        @Override
        public int getCount() {
            return piclist.size();
        }

        @Override
        public Object getItem(int position) {
            return piclist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view1=inflater.inflate(R.layout.content,null);
            ImageView image=view1.findViewById(R.id.picimage);
            TextView text=view1.findViewById(R.id.picname);
            text.setText(piclist.get(position).picname);
            File imagee=new File(piclist.get(position).picimage);
            if (imagee.exists()){
                Bitmap mybit= BitmapFactory.decodeFile(piclist.get(position).picimage);
           Bitmap bitmap=Bitmap.createScaledBitmap(mybit,10,10,true);
           image.setImageBitmap(bitmap);

        }

            return view1;
        }
    }
}
