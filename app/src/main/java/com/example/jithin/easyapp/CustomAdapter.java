package com.example.jithin.easyapp;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdapter(MyApps mainActivity, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        imageId=prgmImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public class Holder
    {
        TextView tv;
        ImageView img,play,share;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.play=(ImageView) rowView.findViewById(R.id.play);
        holder.share=(ImageView) rowView.findViewById(R.id.share);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);

        holder.play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //Toast.makeText(context, "com.example.jithin.easyapp_"+result[position].toLowerCase(), Toast.LENGTH_LONG).show();
                try{
                Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.example.jithin.easyapp_"+result[position].toLowerCase());
                context.startActivity(intent);}
                catch (Exception e){
                    String path=Environment.getExternalStorageDirectory() + File.separator+"Download"+File.separator+"EasyApp_"+result[position];



                    Uri myuri;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
                        myuri = Uri.fromFile(new File
                                (path  +File.separator+ "App.apk"));
                    } else {
                        myuri = FileProvider.getUriForFile(context,
                                BuildConfig.APPLICATION_ID + ".provider",new File
                                        (path  +File.separator+ "App.apk"));
                    }



                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(myuri, "application/vnd.android.package-archive");
                    context.startActivity(intent);
                }
            }
        });

        holder.share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable()){















                    AlertDialog.Builder adb = new AlertDialog.Builder(context);
                    LayoutInflater adbInflater = LayoutInflater.from(context);
                    View eulaLayout = adbInflater.inflate(R.layout.checkbox, null);
                    SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
                    String skipMessage = settings.getString("skipMessage", "NOT checked");

                    final CheckBox dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
                    adb.setView(eulaLayout);
                    adb.setTitle("Share");
                    adb.setCancelable(false);
                    adb.setMessage("\nShare using Gmail\n\n\tOR\n\nUpload to Drive and Share Link\n\nNB : Do not change the Name of the file");


                    adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String checkBoxResult = "NOT checked";

                            if (dontShowAgain.isChecked()) {
                                checkBoxResult = "checked";
                            }

                            SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
                            SharedPreferences.Editor editor = settings.edit();

                            editor.putString("skipMessage", checkBoxResult);
                            editor.commit();




                            String path=Environment.getExternalStorageDirectory() + File.separator+"Download"+File.separator+"EasyApp_"+result[position];

                            String[] blacklist = new String[]{"com.any.package", "net.other.package"};



                            //        Intent intent = new Intent();
                            //        intent.setAction(Intent.ACTION_SEND);
                            //       intent.setType("*/*");


                            File file=new File(Environment.getExternalStorageDirectory() + File.separator+"Download"+File.separator+"EasyApp_App.zip");
                            Uri uri;
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
                                uri = Uri.fromFile(file);
                            } else {
                                uri = FileProvider.getUriForFile(context,
                                        BuildConfig.APPLICATION_ID + ".provider",file);
                            }



                            //        intent.putExtra(Intent.EXTRA_STREAM, uri);
                            //        context.startActivity(Intent.createChooser(intent, "Share App using"));





















                            List<Intent> targetedShareIntents = new ArrayList<Intent>();

                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("*/*");
                            intent.putExtra(Intent.EXTRA_STREAM, uri);

                            List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);

                            for (ResolveInfo resolveInfo : resInfo) {
                                String packageName = resolveInfo.activityInfo.packageName;

                                Intent targetedShareIntent = new Intent(android.content.Intent.ACTION_SEND);
                                targetedShareIntent.setType("*/*");
                                targetedShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                targetedShareIntent.setPackage(packageName);

                                if (packageName.equals("com.google.android.gm") || packageName.equals("com.google.android.apps.docs")) { // Remove Facebook Intent share
                                    targetedShareIntents.add(targetedShareIntent);
                                }
                            }

// Add my own activity in the share Intent chooser;

                            try {

                                Intent chooserIntent = Intent.createChooser(
                                        targetedShareIntents.remove(0), "Share App Using");

                                chooserIntent.putExtra(
                                        Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));

                                context.startActivity(chooserIntent);
                            }catch (IndexOutOfBoundsException e){

                                Toast.makeText(context, "Gmail or Google Drive must be installed for sharing", Toast.LENGTH_LONG).show();


                            }








                            // Do what you want to do on "OK" action

                            return;
                        }
                    });

  /*                  adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String checkBoxResult = "NOT checked";

                            if (dontShowAgain.isChecked()) {
                                checkBoxResult = "checked";
                            }

                            SharedPreferences settings = context.getSharedPreferences("PREFS_NAME", 0);
                            SharedPreferences.Editor editor = settings.edit();

                            editor.putString("skipMessage", checkBoxResult);
                            editor.commit();

                            // Do what you want to do on "CANCEL" action

                            return;
                        }
                    });
*/
                    if (!skipMessage.equals("checked")) {
                        adb.show();
                    }else{
                        String path=Environment.getExternalStorageDirectory() + File.separator+"Download"+File.separator+"EasyApp_"+result[position];

                        String[] blacklist = new String[]{"com.any.package", "net.other.package"};



                        //        Intent intent = new Intent();
                        //        intent.setAction(Intent.ACTION_SEND);
                        //       intent.setType("*/*");


                        File file=new File(Environment.getExternalStorageDirectory() + File.separator+"Download"+File.separator+"EasyApp_App.zip");
                        Uri uri;
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N){
                            uri = Uri.fromFile(file);
                        } else {
                            uri = FileProvider.getUriForFile(context,
                                    BuildConfig.APPLICATION_ID + ".provider",file);
                        }



                        //        intent.putExtra(Intent.EXTRA_STREAM, uri);
                        //        context.startActivity(Intent.createChooser(intent, "Share App using"));





















                        List<Intent> targetedShareIntents = new ArrayList<Intent>();

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("*/*");
                        intent.putExtra(Intent.EXTRA_STREAM, uri);

                        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);

                        for (ResolveInfo resolveInfo : resInfo) {
                            String packageName = resolveInfo.activityInfo.packageName;

                            Intent targetedShareIntent = new Intent(android.content.Intent.ACTION_SEND);
                            targetedShareIntent.setType("*/*");
                            targetedShareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                            targetedShareIntent.setPackage(packageName);

                            if (packageName.equals("com.google.android.gm") || packageName.equals("com.google.android.apps.docs")) { // Remove Facebook Intent share
                                targetedShareIntents.add(targetedShareIntent);
                            }
                        }

// Add my own activity in the share Intent chooser;
                        try {
                        Intent chooserIntent = Intent.createChooser(
                                targetedShareIntents.remove(0), "Share App Using");

                        chooserIntent.putExtra(
                                Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));

                        context.startActivity(chooserIntent);
                        }catch (IndexOutOfBoundsException e){

                            Toast.makeText(context, "Gmail or Google Drive must be installed for sharing", Toast.LENGTH_LONG).show();


                        }
                    }






























                // TODO Auto-generated method stub


















                /*Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse(path);

                sharingIntent.setType("*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                context.startActivity(Intent.createChooser(sharingIntent, "Share App using"));

               /* Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse(path);
                //sharingIntent.setType(" ");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                context.startActivity(Intent.createChooser(sharingIntent, "Share image using"));

/*
                File f=new File(path);
                Uri uri = Uri.parse("file://"+f.getAbsolutePath());
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setPackage("com.whatsapp");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.setType("image/*");
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(Intent.createChooser(share, "Share image File"));
  */          }else {
                    Toast.makeText(context, "No Internet Connection!", Toast.LENGTH_LONG).show();

                }


            }
        });
        return rowView;
    }

}