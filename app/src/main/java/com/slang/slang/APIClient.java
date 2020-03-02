package com.slang.slang;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
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

    static String url = "http://flask-env.ev6u43m7kb.us-east-2.elasticbeanstalk.com/";
    //static String url = "http://flask-env.ev6u43m7kb.us-east-2.elasticbeanstalk.com/db/categories";
    //static String url = "http://google.com/";

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
                Log.d(TAG, "GetCategories: " + e.getStackTrace()+ " " + e.getMessage());
            } catch (IOException e){
                Log.d(TAG, "GetCategories: " + e.getStackTrace()+ " " + e.getMessage());
            }
            res = "";
            return;
        }
    }

    static ExecutorService executor = Executors.newFixedThreadPool(1);

    static ArrayList<String> GetCategories(){
        Retrieve t = new Retrieve();
        t.url = url+"db/categories";
        executor.execute(t);
        while (t.res == null){}
        Log.d(TAG, t.res);
        ArrayList<String> categories = new ArrayList<String>();
        while(t.res.length() > 1){
            t.res = t.res.substring(t.res.indexOf("\"") + 1);
            String category = t.res.substring(0, t.res.indexOf("\""));
            categories.add(category);
            t.res = t.res.substring(t.res.indexOf("\"") + 1);
        }
        System.out.println(categories);
        return categories;
    }

    static ArrayList<String> GetTermsInCategory(String category){
        Retrieve t = new Retrieve();
        t.url = url+"/db/terms?category="+category;
        executor.execute(t);
        while (t.res == null){}
        t.res = t.res.substring(1,t.res.length()-1);
        Log.d(TAG, t.res);
        ArrayList<String> terms = new ArrayList<String>();
        while(t.res.length() > 2){
            t.res = t.res.substring(t.res.indexOf("\"") + 1);
            String term = t.res.substring(0, t.res.indexOf("\""));
            t.res = t.res.substring(t.res.indexOf("\"") + 3);
            String video = t.res.substring(0, t.res.indexOf("\""));
            t.res = t.res.substring(t.res.indexOf("\"") + 1);
            if(!terms.contains(term)) {
                terms.add(term);
                terms.add(video);
            }
            t.res = t.res.substring(t.res.indexOf("]") + 1);
        }
        //System.out.println(terms);
        return terms;
    }
}
