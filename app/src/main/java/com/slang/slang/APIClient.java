package com.slang.slang;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class APIClient {
    static String TAG = "HIIII";

    //static String url = "http://flask-env.ev6u43m7kb.us-east-2.elasticbeanstalk.com/";
    static String url = "http://google.com/";

    private static class Retrieve implements Runnable {
        public String url = null;
        public String res = null;

        @Override
        public void run() {
            try {
                URL site = new URL(url);
                BufferedReader in = new BufferedReader(new InputStreamReader(site.openStream()));
                String input;
                StringBuffer stringBuffer = new StringBuffer();
                while ((input = in.readLine()) != null)
                {
                    stringBuffer.append(input);
                }
                in.close();
                res = stringBuffer.toString();
                return;
            } catch (MalformedURLException e) {
                Log.d(TAG, "GetCategories: " + e.getStackTrace());
            } catch (IOException e){
                Log.d(TAG, "GetCategories: " + e.getStackTrace());
            }
            res = "";
            return;
        }
    }

    static ExecutorService executor = Executors.newFixedThreadPool(1);

    static void GetCategories(){
        Retrieve t = new Retrieve();
        t.url = url;
        executor.execute(t);
        while (t.res == null){}
        Log.d(TAG, t.res);
    }
}
