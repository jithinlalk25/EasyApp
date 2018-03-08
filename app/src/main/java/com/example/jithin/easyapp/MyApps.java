package com.example.jithin.easyapp;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import android.widget.ListView;

public class MyApps extends AppCompatActivity {

    ListView lv;
    Context context;

    ArrayList prgmName;
    public int prgmImages[]=new int[100];
    public String  prgmNameList[]=new String[100];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myapps);

        getSupportActionBar().setTitle("My Apps");


        int[] imageId = {R.mipmap.ic_default, R.mipmap.ic_alpha_a, R.mipmap.ic_alpha_b, R.mipmap.ic_alpha_c, R.mipmap.ic_alpha_d,
                 R.mipmap.ic_alpha_e, R.mipmap.ic_alpha_f, R.mipmap.ic_alpha_g, R.mipmap.ic_alpha_h,
                 R.mipmap.ic_alpha_i, R.mipmap.ic_alpha_j, R.mipmap.ic_alpha_k, R.mipmap.ic_alpha_l,
             R.mipmap.ic_alpha_m, R.mipmap.ic_alpha_n, R.mipmap.ic_alpha_o, R.mipmap.ic_alpha_p,
                 R.mipmap.ic_alpha_q, R.mipmap.ic_alpha_r, R.mipmap.ic_alpha_s,
              R.mipmap.ic_alpha_t, R.mipmap.ic_alpha_u, R.mipmap.ic_alpha_v, R.mipmap.ic_alpha_w,
                 R.mipmap.ic_alpha_x, R.mipmap.ic_alpha_y,R.mipmap.ic_alpha_z,
   R.mipmap.ic_num_0, R.mipmap.ic_num_1, R.mipmap.ic_num_2, R.mipmap.ic_num_3,
               R.mipmap.ic_num_4, R.mipmap.ic_num_5, R.mipmap.ic_num_6, R.mipmap.ic_num_7,
       R.mipmap.ic_num_8, R.mipmap.ic_num_9};

        String[] icon_name = {"App","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};


        context=this;

        String path= Environment.getExternalStorageDirectory() + File.separator+"Download";

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
int j=0;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isDirectory() && listOfFiles[i].getName().startsWith("EasyApp") && !listOfFiles[i].getName().endsWith(".zip")  && !listOfFiles[i].getName().endsWith(")")) {
                prgmNameList[j]=listOfFiles[i].getName().substring(8);
                int index = Arrays.asList(icon_name).indexOf(listOfFiles[i].getName().substring(8));
                prgmImages[j]=imageId[index];
                j++;
            }
        }

        String[] name = Arrays.copyOfRange(prgmNameList,0,j);
        int[] image=Arrays.copyOfRange(prgmImages,0,j);



        lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(new CustomAdapter(this, name,image));

    }


}