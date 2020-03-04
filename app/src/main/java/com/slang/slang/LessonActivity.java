package com.slang.slang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class LessonActivity extends AppCompatActivity {

    static String CategoryKey = "CATEGORY";

    private VideoView vv;

    private String category = null;
    private MediaController mediacontroller;
    private Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        Intent intent = getIntent();
        category = intent.getStringExtra(CategoryKey);

        TextView textView = findViewById(R.id.lesson_name);
        textView.setText(category);
        ArrayList<String> data = APIClient.GetTermsInCategory(category);
        //Log.d("HI", "onCreate: "+APIClient.GetTermsInCategory(category));
        vv = findViewById(R.id.videoView);
        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(vv);
        if(data.size()>=2) {
            //uri = Uri.parse(data.get(1));
            uri = Uri.parse("https://www.demonuts.com/Demonuts/smallvideo.mp4");
            Log.d("HI",uri.toString());
            vv.setVideoURI(uri);
            vv.start();
        }
    }

}
