package com.example.jithin.easyapp;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import static android.content.Context.MODE_PRIVATE;

public class Preview extends DialogFragment implements View.OnClickListener{

    private volatile boolean running = true;
    String title_text,body_text,image,image_scale_type,text_typeface,view,gravity,audio;
    int title_text_color,title_background_color,body_background_color,body_text_color,text_size,icon;
    boolean bold,italic,editable,repeat;
    Toolbar toolbar;
    TextView text,title;
    ImageView background_image,edit;
    RelativeLayout body;
    private View myFragmentView;
    FloatingActionButton fab;
    ProgressDialog mProgressDialog;
    private ProgressBar spinner;
    Bitmap myBitmap;
    MediaPlayer mp;
    String[] icon_name = {"App","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};
    String URL="https://firebasestorage.googleapis.com/v0/b/easyapp-de7e6.appspot.com/o/app-release.apk?alt=media&token=898bdc44-e259-4c90-b9aa-69bdb2ec4fab";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.preview, container,
                false);
        //getDialog().setTitle("Preview");
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        fab=(FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        // Do something else

        spinner = (ProgressBar)rootView.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);


        mp = new MediaPlayer();


        SharedPreferences prefs = this.getActivity().getSharedPreferences("preview", MODE_PRIVATE);

        title_text=prefs.getString("title_text",null);
        body_text=prefs.getString("body_text",null);
        image=prefs.getString("image","");
        title_text_color=prefs.getInt("title_text_color",0);
        title_background_color=prefs.getInt("title_background_color",0);
        body_background_color=prefs.getInt("body_background_color",0);
        body_text_color=prefs.getInt("body_text_color",0);
        image_scale_type=prefs.getString("image_scale_type","None");
        text_typeface=prefs.getString("text_typeface","None");
        view=prefs.getString("view","Both");
        gravity=prefs.getString("gravity","Left");
        text_size=prefs.getInt("text_size",0);
        bold=prefs.getBoolean("bold",false);
        italic=prefs.getBoolean("italic",false);
        editable=prefs.getBoolean("editable",false);
        icon=prefs.getInt("icon",0);
        audio=prefs.getString("audio","");
        repeat=prefs.getBoolean("repeat",false);

        toolbar=(Toolbar)rootView.findViewById(R.id.toolbar);
        text=(TextView)rootView.findViewById(R.id.text);
        title=(TextView)rootView.findViewById(R.id.title);
        background_image=(ImageView)rootView.findViewById(R.id.image);
        body=(RelativeLayout)rootView.findViewById(R.id.body);
        edit=(ImageView)rootView.findViewById(R.id.edit);


        if(audio.equals("1")){

            try {
                mp.setDataSource(Environment.getExternalStorageDirectory() + File.separator+"EasyAppImages/audio.3gp");
                mp.prepare();
                mp.start();

                mp.setLooping(repeat);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        if(editable==true){
            edit.setVisibility(View.VISIBLE);
        }


        if(!image.equals("")) {
            File imgFile = new File(Environment.getExternalStorageDirectory() + File.separator + "EasyAppImages/image.jpg");
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            background_image.setImageBitmap(myBitmap);
        }


        if(gravity.equals("Right")){
            text.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
        }else if(gravity.equals("Center")){
            text.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        }




         if(image_scale_type.equals("None")){}
        else if(image_scale_type.equals("Matrix")){
             background_image.setScaleType(ImageView.ScaleType.MATRIX);
         }
         else if(image_scale_type.equals("FitXY")){
             background_image.setScaleType(ImageView.ScaleType.FIT_XY);
         }
         else if(image_scale_type.equals("FitStart")){
             background_image.setScaleType(ImageView.ScaleType.FIT_START);
         }
         else if(image_scale_type.equals("FitCenter")){
             background_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
         }
         else if(image_scale_type.equals("FitEnd")){
             background_image.setScaleType(ImageView.ScaleType.FIT_END);
         }
         else if(image_scale_type.equals("Center")){
             background_image.setScaleType(ImageView.ScaleType.CENTER);
         }
         else if(image_scale_type.equals("CenterCrop")){
             background_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
         }


        toolbar.setBackgroundColor(title_background_color);
        body.setBackgroundColor(body_background_color);
        title.setTextColor(title_text_color);
        text.setTextColor(body_text_color);
        text.setText(body_text);
        title.setText(title_text);
        text.setTextSize(text_size+5);


        if(text_typeface.equals("None")) {
            if (bold == true && italic == true) {
                text.setTypeface(null, Typeface.BOLD_ITALIC);
            } else if (bold != true && italic == true) {
                text.setTypeface(null, Typeface.ITALIC);
            } else if (bold == true && italic != true) {
                text.setTypeface(null, Typeface.BOLD);
            } else {
            }
        }
          else if(text_typeface.equals("Sans")) {
            if (bold == true && italic == true) {
                text.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
            } else if (bold != true && italic == true) {
                text.setTypeface(Typeface.SANS_SERIF, Typeface.ITALIC);
            } else if (bold == true && italic != true) {
                text.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
            } else {
                text.setTypeface(Typeface.SANS_SERIF);
            }
        }else if(text_typeface.equals("Serif")) {
            if (bold == true && italic == true) {
                text.setTypeface(Typeface.SERIF, Typeface.BOLD_ITALIC);
            } else if (bold != true && italic == true) {
                text.setTypeface(Typeface.SERIF, Typeface.ITALIC);
            } else if (bold == true && italic != true) {
                text.setTypeface(Typeface.SERIF, Typeface.BOLD);
            } else {
                text.setTypeface(Typeface.SERIF);
            }
        }
             else if(text_typeface.equals("Monospace")) {
            if (bold == true && italic == true) {
                text.setTypeface(Typeface.MONOSPACE, Typeface.BOLD_ITALIC);
            } else if (bold != true && italic == true) {
                text.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC);
            } else if (bold == true && italic != true) {
                text.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
            } else {
                text.setTypeface(Typeface.MONOSPACE);
            }
        }
        else{
            if (bold == true && italic == true) {
                text.setTypeface(null, Typeface.BOLD_ITALIC);
            } else if (bold != true && italic == true) {
                text.setTypeface(null, Typeface.ITALIC);
            } else if (bold == true && italic != true) {
                text.setTypeface(null, Typeface.BOLD);
            } else {
            }
        }
        return rootView;
    }


    @Override
    public void onDismiss(final DialogInterface dialog) {
        //Fragment dialog had been dismissed
        mp.stop();
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:

                mp.stop();

                if(isNetworkAvailable()) {


                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:

                                    spinner.setVisibility(View.VISIBLE);

                                    new MyAsyncTask().execute();

                                /*String path=Environment.getExternalStorageDirectory() + File.separator+"Download"+File.separator+"EasyApp_"+icon_name[icon];
                                File EasyApp = new File(path);
                                if(!EasyApp.exists()) {
                                    EasyApp.mkdirs();
                                }else{
                                    if(EasyApp.isDirectory()) {

                                       /* try{
                                        File file = new File(path+"/App.apk");
                                            if(file.exists()) {
                                                File to = new File(path + "z.apk");
                                                file.renameTo(to);
                                                to.delete();
                                            }
                                            }catch (Exception e){
                                            Toast.makeText(getActivity()," hjj ", Toast.LENGTH_LONG).show();

                                        }*/

                                      /*  File[] children = EasyApp.listFiles();
                                        for (File child : children) {
                                            child.delete();
                                        }
                                    }
                                    EasyApp.delete();
                                    EasyApp.mkdirs();
                                }


                                if(!image.equals("")) {
                                    try {
                                        File file = new File(path, "image.jpg");
                                        FileOutputStream out = new FileOutputStream(file);
                                        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                                    } catch (Exception e) {
                                    }
                                }
                                    try{
                                        File file1 = new File(path, "attribute.txt");

                                        file1.createNewFile();
                                        FileOutputStream fOut = new FileOutputStream(file1);
                                        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                                        myOutWriter.append(title_text+"\n");
                                        myOutWriter.append(String.valueOf(title_text_color)+"\n");
                                        myOutWriter.append(String.valueOf(title_background_color)+"\n");
                                        myOutWriter.append(String.valueOf(body_background_color)+"\n");
                                        myOutWriter.append(image+"\n");
                                        myOutWriter.append(image_scale_type+"\n");
                                        myOutWriter.append(String.valueOf(body_text_color)+"\n");
                                        myOutWriter.append(String.valueOf(text_size)+"\n");
                                        myOutWriter.append(text_typeface+"\n");
                                        myOutWriter.append(String.valueOf(bold)+"\n");
                                        myOutWriter.append(String.valueOf(italic)+"\n");
                                        myOutWriter.append(gravity+"\n");
                                        myOutWriter.append(view+"\n");
                                        myOutWriter.append(String.valueOf(editable)+"\n");

                                            myOutWriter.close();

                                            fOut.flush();
                                            fOut.close();


                                    }catch (Exception e){}

                                try{
                                    File file2 = new File(path, "text.txt");

                                    file2.createNewFile();
                                    FileOutputStream fOut = new FileOutputStream(file2);
                                    OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                                    myOutWriter.append(body_text);

                                    myOutWriter.close();

                                    fOut.flush();
                                    fOut.close();


                                }catch (Exception e){}

                                //Toast.makeText(getActivity(),"  ", Toast.LENGTH_LONG).show();

                                new DownloadFile().execute(URL);   */

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
                    builder.setMessage("Download your App").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                }else {
                    Toast.makeText(getActivity(),"No Internet Connection!", Toast.LENGTH_LONG).show();

                }

                break;

        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }


    // DownloadFile AsyncTask
    private class DownloadFile extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            running=true;

            spinner.setVisibility(View.GONE);

            // Create progress dialog
            mProgressDialog = new ProgressDialog(Preview.this.getActivity());
            // Set your progress dialog Title
            //mProgressDialog.setTitle("Progress Bar Tutorial");
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            // Set your progress dialog Message
            mProgressDialog.setMessage("Downloading, Please Wait!");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    running = false;
                    mProgressDialog.dismiss();
                    Toast.makeText(getActivity()," cancel ", Toast.LENGTH_LONG).show();

                }
            });
            // Show progress dialog
            mProgressDialog.show();
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getActivity()," cancel ", Toast.LENGTH_LONG).show();

        }

        @Override
        protected String doInBackground(String... Url) {

                try {
                    URL url = new URL(Url[0]);
                    URLConnection connection = url.openConnection();
                    connection.connect();

                    // Detect the file lenghth
                    int fileLength = connection.getContentLength();

                    // Locate storage location
                    String filepath = Environment.getExternalStorageDirectory()
                            .getPath();

                    String path = Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator + "EasyApp_" + icon_name[icon];


                    // Download the file
                    InputStream input = new BufferedInputStream(url.openStream());

                    // Save the downloaded file

                    OutputStream output = new FileOutputStream(path + File.separator + "App.apk");

                    byte data[] = new byte[1024];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1 && running) {
                        total += count;
                        // Publish the progress
                        publishProgress((int) (total * 100 / fileLength));
                        output.write(data, 0, count);
                    }

                    // Close connection
                    output.flush();
                    output.close();
                    input.close();
                } catch (Exception e) {
                    // Error Log
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

            return null;
        }


        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            //mProgressDialog.dismiss();
            //Toast.makeText(getActivity()," post ", Toast.LENGTH_SHORT).show();

            if(mProgressDialog.getProgress()==100) {
                mProgressDialog.dismiss();
                //dismiss();

                String path = Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator + "EasyApp_" + icon_name[icon];


                ZipUtility z = new ZipUtility();
                File f1 = new File(path);
                File f2 = new File(Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator + "EasyApp_" + icon_name[icon] + ".zip");
                File f3 = new File(path + "_ext");
                try {
                    z.zipDirectory(f1, f2);
                    //z.unzip(f2,f3);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                Uri myuri;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    myuri = Uri.fromFile(new File
                            (path + File.separator + "App.apk"));
                } else {
                    myuri = FileProvider.getUriForFile(getActivity(),
                            BuildConfig.APPLICATION_ID + ".provider", new File
                                    (path + File.separator + "App.apk"));
                }


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(myuri, "application/vnd.android.package-archive");
                startActivity(intent);
            }else{
                mProgressDialog.dismiss();
            }


        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // Update the progress dialog
            mProgressDialog.setProgress(progress[0]);
            // Dismiss the progress dialog
      /*      if(progress[0]==100){
                Toast.makeText(getActivity()," gvkgvkhgvk ", Toast.LENGTH_SHORT).show();

                mProgressDialog.dismiss();
                //dismiss();

                String path=Environment.getExternalStorageDirectory() + File.separator+"Download"+File.separator+"EasyApp_"+icon_name[icon];


                ZipUtility z=new ZipUtility();
                File f1 = new File(path);
                File f2 = new File(Environment.getExternalStorageDirectory() + File.separator+"Download"+File.separator+"EasyApp_"+icon_name[icon]+".zip");
                File f3 = new File(path+"_ext");
                try {
                    z.zipDirectory(f1, f2);
                    //z.unzip(f2,f3);
                }catch (Exception e){
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                Uri myuri;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
                    myuri = Uri.fromFile(new File
                            (path  +File.separator+ "App.apk"));
                } else {
                    myuri = FileProvider.getUriForFile(getActivity(),
                            BuildConfig.APPLICATION_ID + ".provider",new File
                                    (path  +File.separator+ "App.apk"));
                }



                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(myuri, "application/vnd.android.package-archive");
                startActivity(intent);


            }  */
        }

    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params) {

            String path1=Environment.getExternalStorageDirectory() + File.separator+"Download"+File.separator+"EasyApp_"+icon_name[icon]+".zip";
            File p=new File(path1);
            if(p.exists()){
                p.delete();
            }


            String path=Environment.getExternalStorageDirectory() + File.separator+"Download"+File.separator+"EasyApp_"+icon_name[icon];
            File EasyApp = new File(path);
            File easy = new File(path);

            if(!EasyApp.exists()){

                try {
                EasyApp.mkdirs();
            }catch (Exception e){

//                Toast.makeText(getActivity(),e.toString(), Toast.LENGTH_LONG).show();


            }}else{
                final File to = new File(path  + System.currentTimeMillis());
                EasyApp.renameTo(to);
                to.delete();

                easy.mkdirs();

            }


  /*          if(!EasyApp.exists()) {
                EasyApp.mkdirs();
            }else{
                if(EasyApp.isDirectory()) {

                                       /* try{
                                        File file = new File(path+"/App.apk");
                                            if(file.exists()) {
                                                File to = new File(path + "z.apk");
                                                file.renameTo(to);
                                                to.delete();
                                            }
                                            }catch (Exception e){
                                            Toast.makeText(getActivity()," hjj ", Toast.LENGTH_LONG).show();

                                        }*//*

                    File[] children = EasyApp.listFiles();
                    for (File child : children) {
                        child.delete();
                    }
                }
                EasyApp.delete();
                EasyApp.mkdirs();
            }
     */

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {



            String path=Environment.getExternalStorageDirectory() + File.separator+"Download"+File.separator+"EasyApp_"+icon_name[icon];


            if(!audio.equals("")){
                try{
                    File file1= new File(Environment.getExternalStorageDirectory() + File.separator+"EasyAppImages/audio.3gp");
                    File file2= new File(path,"audio.3gp");
                    FileUtils.copyFile(file1,file2);
                }catch(Exception e){

                }
            }
            if(!image.equals("")) {
                try {
                    File file = new File(path, "image.jpg");
                    FileOutputStream out = new FileOutputStream(file);
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                } catch (Exception e) {
                }
            }
            try{
                File file1 = new File(path, "attribute.txt");

                file1.createNewFile();
                FileOutputStream fOut = new FileOutputStream(file1);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(title_text+"\n");
                myOutWriter.append(String.valueOf(title_text_color)+"\n");
                myOutWriter.append(String.valueOf(title_background_color)+"\n");
                myOutWriter.append(String.valueOf(body_background_color)+"\n");
                myOutWriter.append(image+"\n");
                myOutWriter.append(image_scale_type+"\n");
                myOutWriter.append(String.valueOf(body_text_color)+"\n");
                myOutWriter.append(String.valueOf(text_size)+"\n");
                myOutWriter.append(text_typeface+"\n");
                myOutWriter.append(String.valueOf(bold)+"\n");
                myOutWriter.append(String.valueOf(italic)+"\n");
                myOutWriter.append(gravity+"\n");
                myOutWriter.append(view+"\n");
                myOutWriter.append(String.valueOf(editable)+"\n");
                myOutWriter.append(audio+"\n");
                myOutWriter.append(String.valueOf(repeat)+"\n");

                myOutWriter.close();

                fOut.flush();
                fOut.close();


            }catch (Exception e){}

            try{
                File file2 = new File(path, "text.txt");

                file2.createNewFile();
                FileOutputStream fOut = new FileOutputStream(file2);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.append(body_text);

                myOutWriter.close();

                fOut.flush();
                fOut.close();


            }catch (Exception e){}

            //Toast.makeText(getActivity(),"  ", Toast.LENGTH_LONG).show();

            new DownloadFile().execute(URL);

        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onPause() {
        super.onPause();
//...
            this.dismiss();
    }
}