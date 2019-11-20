package com.example.sounddstest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class DownloadAndPlayAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    int count;
     ProgressDialog progressDialog;
    int lenght;
    int dcount=0,tcount;
    private DownloadTaskListener listener;
    private static double SPACE_KB = 1024;
    private static double SPACE_MB = 1024 * SPACE_KB;
    private static double SPACE_GB = 1024 * SPACE_MB;
    private static double SPACE_TB = 1024 * SPACE_GB;
    String nam;
    public DownloadAndPlayAsyncTask(DownloadTaskListener listener, Context context, ProgressDialog progressDialog){
        this.listener = listener;
        this.context=context;
        this.progressDialog=progressDialog;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        Toast.makeText(context, "not completed", Toast.LENGTH_SHORT).show();
        Log.d("Doooooo","start");
       // dcounr++;
      /*  progressDialog=new ProgressDialog(context);
        progressDialog.setTitle(count);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Downloading...");*/
        progressDialog.setMessage("Downloading...");
        progressDialog.setCancelable(false);
        progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Cancel download task
               // running = false;
                progressDialog.cancel();
                cancel(true);
            }
        });
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
//        new DownloadAndPlayAsyncTask().execute("url");


        try {
             lenght=strings.length;
            if (lenght>4) {
                nam="";
                tcount=3;

                // URL url = new URL("https://www.android-examples.com/wp-content/uploads/2016/04/Thunder-rumble.mp3");
                int i;
                Log.d("LENGTH", strings.length + "");
                for (i = 2; i < strings.length - 3; i++) {
                    nam=strings[i+3];
                    dcount++;
                    URL url = new URL(strings[i]);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();
                    // this will be useful so that you can show a tipical 0-100% progress bar
                    int lenghtOfFile = conexion.getContentLength();

                    // downlod the file
                    //  File path=getFilesDir();
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(strings[1] + strings[i + 3] + ".mp3");
           /* InputStream input = new BufferedInputStream(url.openStream());
            String path = strings[1]+"musics/music.mp3";
            Boolean result = new File(path).createNewFile();
            OutputStream output = new FileOutputStream(path);*/
                    //  OutputStream output = new FileOutputStream(get(),"myfolder");

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {

                        if (isCancelled()) {
                            File file = new File(strings[1] + strings[i+3] + ".mp3");
                            if (file.exists()) {
                                file.delete();
                            }
                            break;
                        }

                        total += count;
                        // publishing the progress....
                        publishProgress((int) (total * 100 / lenghtOfFile));
                        output.write(data, 0, count);
                        progressDialog.setProgressNumberFormat((bytes2String(total)) + "/" + (bytes2String(lenghtOfFile)));
                    }

                    output.flush();
                    output.close();
                    input.close();
                    Log.d("Doooooo", lenghtOfFile+""+count);
                }
            }
            else {
                int i;
                tcount=1;
                nam="";
                nam=strings[3];
               // if (isCancelled())

                dcount++;
                Log.d("LENGTH", strings.length + "");
              //  for (i = 2; i < strings.length - 3; i++) {
                    URL url = new URL(strings[2]);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();
                    // this will be useful so that you can show a tipical 0-100% progress bar
                    int lenghtOfFile = conexion.getContentLength();

                    // downlod the file
                    //  File path=getFilesDir();
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(strings[1] + strings[3] + ".mp3");
           /* InputStream input = new BufferedInputStream(url.openStream());
            String path = strings[1]+"musics/music.mp3";
            Boolean result = new File(path).createNewFile();
            OutputStream output = new FileOutputStream(path);*/
                    //  OutputStream output = new FileOutputStream(get(),"myfolder");

                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        if (isCancelled())
                        {
                            File file=new File(strings[1] + strings[3] + ".mp3");
                            if (file.exists())
                            {
                                file.delete();
                            }
                            break;
                    }
                        total += count;
                        // publishing the progress....
                        publishProgress((int) (total * 100 / lenghtOfFile));
                        output.write(data, 0, count);
                        progressDialog.setProgressNumberFormat((bytes2String(total)) + "/" + (bytes2String(lenghtOfFile)));
                    }

                    output.flush();
                    output.close();
                    input.close();
                    Log.d("Doooooo", "done Download");
                //}
            }
        } catch (Exception e) {
            Log.d("Doooooo",e.getMessage());
        }
        return "Completed";
    }

    @Override
    protected void onPostExecute(String s) {
//        Toast.makeText(context, "completed", Toast.LENGTH_SHORT).show();
     //   ProgressDialog dialog = getProgressDialog();
        if (progressDialog != null) {
           // progressDialog.dismiss();
           // progressDialog.cancel();
           // progressDialog=null;
            progressDialog.setProgress(0);
            progressDialog.setMessage("Downloading...");
            progressDialog.setProgressNumberFormat(null);
            progressDialog.dismiss();


        }
        Log.d("Doooooo",s);
        listener.onDownloadComplete();
        super.onPostExecute(s);
       // progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);
        progressDialog.setMessage(nam+" Sound "+"Downloading..."+dcount+"/"+tcount);
       // Log.d("downlooooood",(values[0]/(1024*1024)) + "" +(values[0]/(1024*1024)));
        Log.d("downlooooood",""+count);


    }
    public static String bytes2String(long sizeInBytes) {

        NumberFormat nf = new DecimalFormat();
        nf.setMaximumFractionDigits(2);

        try {
            if ( sizeInBytes < SPACE_KB ) {
                return nf.format(sizeInBytes) + " Byte(s)";
            } else if ( sizeInBytes < SPACE_MB ) {
                return nf.format(sizeInBytes/SPACE_KB) + " KB";
            } else if ( sizeInBytes < SPACE_GB ) {
                return nf.format(sizeInBytes/SPACE_MB) + " MB";
            } else if ( sizeInBytes < SPACE_TB ) {
                return nf.format(sizeInBytes/SPACE_GB) + " GB";
            } else {
                return nf.format(sizeInBytes/SPACE_TB) + " TB";
            }
        } catch (Exception e) {
            return sizeInBytes + " Byte(s)";
        }

    }
    //private Context ctx;


   /* @Override
    protected void onProgressUpdate(Integer... progress) {
        Intent i = new Intent();
        i.setAction(CUSTOM_INTENT);
        i.setFlags(progress[0]);

    }*/
}
