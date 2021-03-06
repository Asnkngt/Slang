package com.slang.slang;

import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
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
    static String TAG = "***** LOG *****";

    static String url = "http://slang-dev.us-east-1.elasticbeanstalk.com/";
    //static String url = "http://flask-env.ev6u43m7kb.us-east-2.elasticbeanstalk.com/";
    //static String url = "http://flask-env.ev6u43m7kb.us-east-2.elasticbeanstalk.com/db/categories";
    //static String url = "http://google.com/";

    private static class Page implements Runnable {
        public String url = null;
        public String res = null;

        @Override
        public void run() {
            try {
                URL site = new URL(url);
                BufferedReader in = new BufferedReader(new InputStreamReader(site.openStream()));
                String input;
                StringBuffer stringBuffer = new StringBuffer();
                while ((input = in.readLine()) != null) {
                    stringBuffer.append(input);
                }
                in.close();
                res = stringBuffer.toString();
                // Remove beginning and end brackets [] from the string (e.g. ["hi"] -> "hi")
                res = res.substring(1, res.length() - 1);
                return;
            } catch (MalformedURLException e) {
                Log.d(TAG, "GetCategories: " + e.getStackTrace() + " " + e.getMessage());
            } catch (IOException e){
                Log.d(TAG, "GetCategories: " + e.getStackTrace() + " " + e.getMessage());
            }
            res = "";
            return;
        }
    }

    static ExecutorService executor = Executors.newFixedThreadPool(1);//null;

    private static String getPageData(String subdirectory) {
        Log.d(TAG, "getPageData: Start");
        Page page = new Page();
        page.url = url + subdirectory;
        //executor = Executors.newFixedThreadPool(1);
        executor.execute(page);
        //executor.shutdown();
        //executor = null;
        while (page.res == null){
            Log.d(TAG, "getPageData: Waiting");
        }
        Log.d(TAG, "getPageData: Finished");
        return page.res;
    }

    static List<String> GetCategories() {
        String htmlRes = getPageData("db/categories");
        List<String> categories = Arrays.asList(htmlRes.split(","));
        return categories;
    }

    static ArrayList<String> GetTermsInCategory(String category) {
        String htmlRes = getPageData("db/categories/" + category);

        Log.d("GET_TERMS_IN_CATEGORY: ", htmlRes);

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
        return terms;
    }

    static ArrayList<String> GetTerm(String term) {
        String htmlRes = getPageData("db/terms/" + term);
        if (term.length() == 0 || htmlRes.length() == 0) {
            return new ArrayList<String>();
        }
        ArrayList<String> terms = new ArrayList<>();

        // Most terms have multiple video files associated with it, so we just want to pick the first file
        List<String> records = Arrays.asList(htmlRes.split("]"));
        for (int i = 0; i < records.size(); i++) {
            // Record is a comma-separated string containing info about a term. This includes the term
            // itself, its video URL, and its category. E.g. record = ["apple", "apple.mp4", "foods"]
            List<String> record = Arrays.asList(records.get(i).split(","));
            String videoURL = record.get(1);

            // Remove quotation marks from videoURL (the first and last characters)
            videoURL = videoURL.substring(1, videoURL.length() - 1);
            if (!terms.contains(term)) {
                terms.add(term);
                terms.add(videoURL);
            }
        }
        return terms;
    }
}