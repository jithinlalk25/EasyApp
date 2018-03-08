package com.example.jithin.easyapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.android.gms.ads.MobileAds;

import org.apache.commons.io.FileUtils;

import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private int mPickedColor = Color.WHITE;
    int z=Color.WHITE;
    EditText t1,t2;
    private int requestCode = 1;
    private ImageView image;
    Intent CamIntent, GalIntent, CropIntent ;
    Uri uri;
    int title_text_color=Color.WHITE,title_background_color=Color.HSVToColor(255,new float[]{240,1,1}),body_background_color=Color.WHITE,body_text_color=Color.BLACK;
    MaterialBetterSpinner materialBetterSpinner_image ,materialBetterSpinner_text,materialBetterSpinner_view,materialBetterSpinner_gravity;
    int image_scale_type=0,text_typeface=0,view=0;
    SeekBar seekBar;
    CheckBox bold,italic,editable,repeat;
    Bitmap bitmap=null;
    FragmentManager fm = getSupportFragmentManager();
    FragmentManager fm1 = getSupportFragmentManager();
    File directory;
    Uri uri_image;
    int aud=0;
    int icon=0;
    private AdView mAdView;
    Preview preview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-5600645232674176~1146450358");

        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setVisibility(View.GONE);


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("Ads", "onAdLoaded");
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
                mAdView.setVisibility(View.GONE);
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Log.i("Ads", "onAdOpened");
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.i("Ads", "onAdLeftApplication");
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                Log.i("Ads", "onAdClosed");
            }
        });

        AdRequest adRequest = new AdRequest.Builder().addTestDevice("1633A5447F81A3813D7478E16121A808")
                .build();
        mAdView.loadAd(adRequest);








        preview = new Preview();







        directory = new File(Environment.getExternalStorageDirectory() + File.separator+"EasyAppImages");

        if(!directory.exists()) {
            directory.mkdirs();
        }else{
            if(directory.isDirectory()) {
                File[] children = directory.listFiles();
                if(children!=null){
                for (File child : children) {
                    child.delete();
                }}
            }
            directory.delete();
            directory.mkdirs();
        }


        bold=(CheckBox)findViewById(R.id.body_text_style_bold);
        italic=(CheckBox)findViewById(R.id.body_text_style_italic);
        editable=(CheckBox)findViewById(R.id.editable);
        repeat=(CheckBox)findViewById(R.id.audio_repeat);



        String[] SPINNER_DATA_image = {"None","Matrix","FitXY","FitStart","FitCenter","FitEnd","Center","CenterCrop"};
        materialBetterSpinner_image = (MaterialBetterSpinner)findViewById(R.id.body_background_image_scale_type);
        ArrayAdapter<String> adapter_image = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, SPINNER_DATA_image);
        materialBetterSpinner_image.setAdapter(adapter_image);
        materialBetterSpinner_image.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                image_scale_type=position;
                switch (position) {
                }}
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });



        String[] SPINNER_DATA_text = {"None","Sans","Serif","Monospace"};
        materialBetterSpinner_text = (MaterialBetterSpinner)findViewById(R.id.body_text_typeface);
        ArrayAdapter<String> adapter_text = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, SPINNER_DATA_text);
        materialBetterSpinner_text.setAdapter(adapter_text);
        materialBetterSpinner_text.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                text_typeface=position;
                Toast.makeText(getApplicationContext(),"" + position, Toast.LENGTH_LONG).show();

                switch (position) {
                }}
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        String[] SPINNER_DATA_view = {"Both","Portrait","Landscape"};
        materialBetterSpinner_view = (MaterialBetterSpinner)findViewById(R.id.view);
        ArrayAdapter<String> adapter_view = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, SPINNER_DATA_view);
        materialBetterSpinner_view.setAdapter(adapter_view);
        materialBetterSpinner_view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                view=position;
                switch (position) {
                }}
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        String[] SPINNER_DATA_gravity = {"Left","Right","Center"};
        materialBetterSpinner_gravity = (MaterialBetterSpinner)findViewById(R.id.body_text_gravity);
        ArrayAdapter<String> adapter_gravity = new ArrayAdapter<String>(MainActivity.this, R.layout.spinner_item, SPINNER_DATA_gravity);
        materialBetterSpinner_gravity.setAdapter(adapter_gravity);
        materialBetterSpinner_gravity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                switch (position) {
                }}
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });




        TextInputLayout text1 = (TextInputLayout) findViewById(R.id.title_text_textinputlayout);
        TextInputLayout text2 = (TextInputLayout) findViewById(R.id.body_text_textinputlayout);
        text1.setCounterEnabled(true);
        text1.setCounterMaxLength(30);
        text2.setCounterEnabled(true);
        text2.setCounterMaxLength(300);
        t1=(EditText) findViewById(R.id.title_text);
        t2=(EditText) findViewById(R.id.body_text);


/*
        t1.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                mAdView.setVisibility(hasFocus ? View.GONE : View.VISIBLE);

            }
        });
        t2.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                mAdView.setVisibility(hasFocus ? View.GONE : View.VISIBLE);

            }
        });
        //InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //im.showSoftInput(t1, InputMethodManager.SHOW_IMPLICIT);
/*
        FrameLayout touchInterceptor = (FrameLayout)findViewById(R.id.touch);
        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (t1.isFocused()) {
                        Rect outRect = new Rect();
                        t1.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                            t1.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });

*/









        seekBar = (SeekBar) findViewById(R.id.seekBar);
        final TextView textView = (TextView) findViewById(R.id.size_percent);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarProgress = 11;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarProgress = progress;
                textView.setText((seekBarProgress+1) + "%");
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.preview) {
/*
            try {
                File file = new File(directory, "image.jpg");
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            }catch (Exception e){}
*/

            SharedPreferences.Editor editor = getSharedPreferences("preview", MODE_PRIVATE).edit();
            editor.putString("title_text", t1.getText().toString());
            editor.putString("body_text", t2.getText().toString());
            editor.putInt("title_text_color", title_text_color);
            editor.putInt("title_background_color",title_background_color);
            editor.putInt("body_background_color",body_background_color);
            editor.putInt("body_text_color",body_text_color);
            editor.putString("image_scale_type",materialBetterSpinner_image.getText().toString());
            editor.putString("text_typeface",materialBetterSpinner_text.getText().toString());
            editor.putString("view",materialBetterSpinner_view.getText().toString());
            editor.putString("gravity",materialBetterSpinner_gravity.getText().toString());
            editor.putInt("text_size",seekBar.getProgress());
            editor.putBoolean("bold",bold.isChecked());
            editor.putBoolean("italic",italic.isChecked());
            editor.putBoolean("editable",editable.isChecked());
            editor.putInt("icon",0);

            if(bitmap!=null){
                try {
                    //File file = new File(directory, "image.jpg");
                    //FileOutputStream out = new FileOutputStream(file);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                    //Toast.makeText(getApplicationContext(),""+file.length(), Toast.LENGTH_LONG).show();

                    editor.putString("image","1");
                }catch (Exception e){}
            }else{
                editor.putString("image","");
            }


            if(aud==1){
                editor.putString("audio","1");
            }else{
                editor.putString("audio","");
            }

            editor.putBoolean("repeat",repeat.isChecked());


            editor.apply();

            //Toast.makeText(getApplicationContext(),materialBetterSpinner_text.getText(), Toast.LENGTH_LONG).show();

            String[] permissionArrays = new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE};

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionArrays, 11111);
            } else {
                Preview preview = new Preview();
                // Show DialogFragment
                preview.show(fm, "Dialog Fragment");
            }








            return true;
        }
        else if(id==R.id.my_apps){

            String[] permissionArrays = new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE};

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionArrays, 22222);
            } else {
                Intent intent = new Intent(this, MyApps.class);
                startActivity(intent);
            }

            return true;

        }
        else if(id==R.id.about){

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText && !(v instanceof MaterialBetterSpinner)) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    public void TitleTextColor(View view) {
        t1.clearFocus();
        t2.clearFocus();
        ImageView im=(ImageView) findViewById(R.id.title_text_color);
        SelectColor(im,"title_text_color");
    }
    public void TitleBackgroundColor(View view) {
        t1.clearFocus();
        t2.clearFocus();
        ImageView im=(ImageView) findViewById(R.id.title_background_color);
        SelectColor(im,"title_background_color");
    }
    public void BodyBackgroundColor(View view) {
        t1.clearFocus();
        t2.clearFocus();
        ImageView im=(ImageView) findViewById(R.id.body_background_color);
        SelectColor(im,"body_background_color");
    }
    public void BodyTextColor(View view) {
        t1.clearFocus();
        t2.clearFocus();
        ImageView im=(ImageView) findViewById(R.id.body_text_color);
        SelectColor(im,"body_text_color");
    }
    public void BodyBackgroundMusic(View view){

        t1.clearFocus();
        t2.clearFocus();

        final ImageView im=(ImageView) findViewById(R.id.body_background_music);

        final CharSequence[] items = { "Choose from Phone","No Music", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Music");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Choose from Phone")) {
                    dialog.dismiss();

                    Intent intent_upload = new Intent();
                    intent_upload.setType("audio/*");
                    intent_upload.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent_upload,2);

                }else if (items[item].equals("No Music")) {

im.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.noaudio));

                    aud=0;

                    dialog.dismiss();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();



    }
    public void BodyBackgroundImage(View view){
        t1.clearFocus();
        t2.clearFocus();

        final CharSequence[] items = { "Choose from Phone","No Image", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Choose from Phone")) {
                    dialog.dismiss();

                    //Toast.makeText(getApplicationContext(),"High-Res Images may Crash the app!", Toast.LENGTH_LONG).show();


                    Intent intent = new Intent();
// Show only images, no videos or anything else
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

                } else if (items[item].equals("No Image")) {

                    ImageView im=(ImageView) findViewById(R.id.body_background_image);
                    bitmap=null;
                    im.setImageBitmap(null);
                    dialog.dismiss();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();













    //    GalIntent = new Intent(Intent.ACTION_PICK,
    //            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

    //    startActivityForResult(Intent.createChooser(GalIntent, "Select Image From Gallery"), 2);
    }

    public void icon(View view){


        IconPicker iconpicker = new IconPicker();
        // Show DialogFragment
        iconpicker.show(fm, "Dialog Fragment1");
    }

    public void iconselect(int pos){
        icon=pos;
        Bitmap[] imageId = {
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_a),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_b),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_c),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_d),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_e),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_f),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_g),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_h),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_i),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_j),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_k),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_l),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_m),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_n),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_o),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_p),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_q),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_r),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_s),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_t),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_u),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_v),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_w),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_x),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_y),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_alpha_z),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_num_0),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_num_1),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_num_2),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_num_3),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_num_4),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_num_5),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_num_6),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_num_7),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_num_8),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_num_9),


        };

        ImageView ic=(ImageView)findViewById(R.id.icon);
        ic.setImageBitmap(imageId[pos]);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri_image = data.getData();


            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri_image);

                bitmap=getResizedBitmapLessThan500KB(bitmap,500);


                // Log.d(TAG, String.valueOf(bitmap));

                File file = new File(directory, "image.jpg");
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                File imgFile = new File(Environment.getExternalStorageDirectory() + File.separator + "EasyAppImages/image.jpg");
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                //background_image.setImageBitmap(myBitmap);


                ImageView im=(ImageView) findViewById(R.id.body_background_image);
                im.setImageBitmap(myBitmap);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"Resolution of image is too high!", Toast.LENGTH_LONG).show();
            }catch (OutOfMemoryError e){
                Toast.makeText(getApplicationContext(),"Resolution of image is too high!", Toast.LENGTH_LONG).show();
            }
        }

        if(requestCode == 2){

            if(resultCode == RESULT_OK){

                final ImageView im=(ImageView) findViewById(R.id.body_background_music);


                try {

                    Uri uri = data.getData();

                    String s= getPathFromURI(this,uri);

                    File file1= new File(s);
                    File file2= new File(Environment.getExternalStorageDirectory() + File.separator+"EasyAppImages/audio.3gp");
                    FileUtils.copyFile(file1,file2);


                    aud=1;
                    im.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.audio));

                    //Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();


                }catch(Exception e) {
                    Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }

            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPathFromURI(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }










    public static Bitmap getResizedBitmapLessThan500KB(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();



        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        Bitmap reduced_bitmap = Bitmap.createScaledBitmap(image, width, height, true);
        //if(reduced_bitmap.getByteCount() > (500 * 1000)) {
        //    return getResizedBitmapLessThan500KB(reduced_bitmap, maxSize);
        //} else {
            return reduced_bitmap;
        //}
    }

    public void ImageCropFunction() {

        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            //CropIntent.putExtra("outputX", 180);
            //CropIntent.putExtra("outputY", 180);
            //CropIntent.putExtra("aspectX", 3);
            //CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);

        } catch (ActivityNotFoundException e) {

        }
    }

    public void SelectColor(ImageView im,String s){
        mContext = getApplicationContext();
        final LinearLayout rl = (LinearLayout) findViewById(R.id.rl);
        final ImageView img=im;
        final String ss=s;

        GridView gv = (GridView) ColorPicker.getColorPicker(MainActivity.this);

        // Initialize a new AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        // Set the alert dialog content to GridView (color picker)
        builder.setView(gv);

        // Initialize a new AlertDialog object
        final AlertDialog dialog = builder.create();

        // Show the color picker window
        dialog.show();

        // Set the color picker dialog size
       // dialog.getWindow().setLayout(
       //         getScreenSize().x - rl.getPaddingLeft() - rl.getPaddingRight()
       //         ,
       //         getScreenSize().y - getStatusBarHeight() - rl.getPaddingTop() - rl.getPaddingBottom()
       //         );

        // Set an item click listener for GridView widget
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the pickedColor from AdapterView
                mPickedColor = (int) parent.getItemAtPosition(position);

                // Set the layout background color as picked color
                //rl.setBackgroundColor(mPickedColor);

                img.setBackgroundColor(mPickedColor);

                if(ss.equals("title_background_color")){
                    title_background_color=mPickedColor;
                }else if(ss.equals("title_text_color")){
                    title_text_color=mPickedColor;
                }else if(ss.equals("body_background_color")){
                    body_background_color=mPickedColor;
                }else if(ss.equals("body_text_color")){
                    body_text_color=mPickedColor;
                }

                     /*   String col= String.valueOf(z);
                        int a=Integer.parseInt(col);
                        rl.setBackgroundColor(a);

                        int color = Color.TRANSPARENT;
                        Drawable background = rl.getBackground();
                        if (background instanceof ColorDrawable)
                            color = ((ColorDrawable) background).getColor();

                        Toast.makeText(getApplicationContext(),"" + color, Toast.LENGTH_LONG).show();
*/
                // close the color picker
                dialog.dismiss();
            }
        });

    }
    private Point getScreenSize(){
        WindowManager wm = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        //Display dimensions in pixels
        display.getSize(size);
        return size;
    }

    // Custom method to get status bar height in pixels
    public int getStatusBarHeight() {
        int height = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }
    public static int HSVColor(float hue, float saturation, float black){

        int color = Color.HSVToColor(255,new float[]{hue,saturation,black});
        return color;
    }

    private void checkRunTimePermission() {
        String[] permissionArrays = new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissionArrays, 11111);
        } else {
            // if already permition granted
            // PUT YOUR ACTION (Like Open cemara etc..)
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean openActivityOnce = true;
        boolean openDialogOnce = true;
        if (requestCode == 11111) {

            if(grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED && grantResults[2]==PackageManager.PERMISSION_GRANTED) {

                // Show DialogFragment
                preview.show(fm, "Dialog Fragment");

            }
        }
        else if (requestCode == 22222) {

            if(grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED && grantResults[2]==PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(this, MyApps.class);
        startActivity(intent);}}
    }

}


