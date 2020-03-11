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
import java.util.concurrent.TimeUnit;

public class APIClient {
    static String TAG = "HIIII";

    static String url = "http://slang-dev.us-east-1.elasticbeanstalk.com/";
    //static String url = "http://flask-env.ev6u43m7kb.us-east-2.elasticbeanstalk.com/";
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

    static ExecutorService executor = Executors.newFixedThreadPool(1);//null;

    private static String getStr(String append){
        Log.d(TAG, "getStr: Start");
        Retrieve t = new Retrieve();
        t.url = url + append;
        //executor = Executors.newFixedThreadPool(1);
        executor.execute(t);
        //executor.shutdown();
        //executor = null;
        while (t.res == null){
            Log.d(TAG, "getStr: Waiting");
        }
        Log.d(TAG, "getStr: Finished");
        return t.res;
    }

    static ArrayList<String> GetCategories(){
        String htmlRes = getStr("db/categories");

        ArrayList<String> categories = new ArrayList<String>();
        while(htmlRes.length() > 1){
            htmlRes = htmlRes.substring(htmlRes.indexOf("\"") + 1);
            String category = htmlRes.substring(0, htmlRes.indexOf("\""));
            categories.add(category);
            htmlRes = htmlRes.substring(htmlRes.indexOf("\"") + 1);
        }
        //System.out.println(categories);
        return categories;
    }

    static ArrayList<String> GetTermsInCategory(String category){
        String htmlRes = getStr("db/categories/"+category);

        htmlRes = htmlRes.substring(1,htmlRes.length()-1);
        Log.d(TAG, htmlRes);
        ArrayList<String> terms = new ArrayList<String>();
        while(htmlRes.length() > 2){
            htmlRes = htmlRes.substring(htmlRes.indexOf("\"") + 1);
            String term = htmlRes.substring(0, htmlRes.indexOf("\""));
            htmlRes = htmlRes.substring(htmlRes.indexOf("\"") + 3);
            String video = htmlRes.substring(0, htmlRes.indexOf("\""));
            htmlRes = htmlRes.substring(htmlRes.indexOf("\"") + 1);
            if(!terms.contains(term)) {
                terms.add(term);
                terms.add(video);
            }
            htmlRes = htmlRes.substring(htmlRes.indexOf("]") + 1);
        }
        //System.out.println(terms);
        return terms;
    }
    static ArrayList<String> GetTerm(String english){
        String htmlRes = getStr("db/terms/" + english);
        if(english.length()==0){ return new ArrayList<String>();}
        htmlRes = htmlRes.substring(1,htmlRes.length()-1);
        Log.d(TAG, htmlRes);
        ArrayList<String> terms = new ArrayList<String>();
        while(htmlRes.length() > 2){
            htmlRes = htmlRes.substring(htmlRes.indexOf("\"") + 1);
            String term = htmlRes.substring(0, htmlRes.indexOf("\""));
            htmlRes = htmlRes.substring(htmlRes.indexOf("\"") + 3);
            String video = htmlRes.substring(0, htmlRes.indexOf("\""));
            htmlRes = htmlRes.substring(htmlRes.indexOf("\"") + 1);
            if(!terms.contains(term)) {
                terms.add(term);
                terms.add(video);
            }
            htmlRes = htmlRes.substring(htmlRes.indexOf("]") + 1);
        }
        //System.out.println(terms);
        return terms;
    }
}
