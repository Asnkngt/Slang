package com.slang.slang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity2 extends AppCompatActivity {

    static final String DataKey = "DATA";
    private String data = null;

    private VideoView vv;

    private MediaController mediacontroller;

    private ArrayList<String> terms = new ArrayList<String>();

    private int curr = -1;
    ArrayList<Integer> selected;

    private Button selection1;
    private Button selection2;
    private Button selection3;
    private Button selection4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        Intent intent = getIntent();
        data = intent.getStringExtra(DataKey);

        TextView titleText = findViewById(R.id.category_name);
        titleText.setText(data);
        selection1 = findViewById(R.id.selection1);
        selection2 = findViewById(R.id.selection2);
        selection3 = findViewById(R.id.selection3);
        selection4 = findViewById(R.id.selection4);
        Log.d("-_-", data.substring(1,data.length()-2));
        terms = APIClient.GetTermsInCategory(data.replaceAll("\"",""));
        vv = findViewById(R.id.videoView);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);

        vv.post(new Runnable() {
            @Override
            public void run() {
                Rect rect = new Rect(0,vv.getHeight() / 40, vv.getWidth(),(int)(vv.getHeight() /2));
                vv.setClipBounds(rect);
            }
        });
        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(vv);
        vv.setOnClickListener(new View.OnClickListener() {
            boolean paused = false;
            @Override
            public void onClick(View v) {
                if(paused){
                    vv.start();
                }else {
                    vv.pause();
                }
                paused = !paused;
            }
        });
        vv.setOnCompletionListener ( new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                vv.start();
            }
        });

        selection1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clicked(0);
            }
        });
        selection2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clicked(1);
            }
        });
        selection3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clicked(2);
            }
        });
        selection4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Clicked(3);
            }
        });

        setUp();
    }

    private void setUp(){
        selected = new ArrayList<Integer>();
        Random rand = new Random();
        while(selected.size() < 4){
            int index = rand.nextInt((terms.size()/2)) * 2;
            if(index!=curr && !selected.contains(index)){
                selected.add(index);
            }
        }
        curr = selected.get(rand.nextInt(4));
        selection1.setText(terms.get(selected.get(0)));
        selection2.setText(terms.get(selected.get(1)));
        selection3.setText(terms.get(selected.get(2)));
        selection4.setText(terms.get(selected.get(3)));

        Uri uri = Uri.parse(terms.get(curr+1));
        vv.setVideoURI(uri);
        vv.start();
    }

    public void Clicked(int button) {
        if(curr == selected.get(button)) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
        setUp();
    }
}
