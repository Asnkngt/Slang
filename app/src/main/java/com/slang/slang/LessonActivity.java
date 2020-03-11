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

    int type;

    static final String SwitchKey = "SWITCH";
    static final int DictionaryActivityCase = 0;
    static final int LessonsActivityCase = 1;
    static final int QuizActivityCase = 2;

    static final String DataKey = "DATA";
    private String data = null;

    private VideoView vv;

    private MediaController mediacontroller;
    private Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        Intent intent = getIntent();
        type = intent.getIntExtra(SwitchKey,-1);
        data = intent.getStringExtra(DataKey);

        TextView titleText = findViewById(R.id.lesson_name);
        titleText.setText(data);

        TextView term = findViewById(R.id.term);


        ArrayList<String> terms = new ArrayList<String>();

        switch (type){
            case -1:
                return;
            case DictionaryActivityCase:
                data = data.toLowerCase();
                titleText.setText("Dictionary");
                terms = APIClient.GetTerm(data);
                if(terms.size() == 0){
                    term.setText("Word not found");
                }else{
                    term.setText(data);
                }
                break;
            case LessonsActivityCase:
                titleText.setText(data);
                terms = APIClient.GetTermsInCategory(data);
                term.setText(terms.get(0));
                break;
            case QuizActivityCase:
                titleText.setText(data);
                terms = APIClient.GetTermsInCategory(data);
                term.setText("Quiz placeholder");
                break;
        }

        //Log.d("HI", "onCreate: "+APIClient.GetTermsInCategory(category));
        vv = findViewById(R.id.videoView);
        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(vv);
        if(terms.size()>=2) {
            uri = Uri.parse(terms.get(1));
            //uri = Uri.parse("https://slang-backend-mp4-videos.s3.amazonaws.com/twenty/Liz_10.mp4");
            Log.d("HI",uri.toString());
            vv.setVideoURI(uri);
            vv.start();
        }
    }
}
