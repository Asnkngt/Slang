package com.slang.slang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class DictionaryActivity2 extends AppCompatActivity {

    static final String DataKey = "DATA";
    private String data = null;

    private VideoView vv;

    private MediaController mediacontroller;
    private Uri uri = null;

    private ArrayList<String> terms = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary2);

        Intent intent = getIntent();
        data = intent.getStringExtra(DataKey);

        TextView titleText = findViewById(R.id.vocab_name);

        terms = APIClient.GetTerm(data);

        vv = findViewById(R.id.videoView);
        mediacontroller = new MediaController(this);
        mediacontroller.setAnchorView(vv);
        if(terms.size()>1) {
            titleText.setText(terms.get(0));
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
            //uri = Uri.parse(terms.get(1));
            uri = Uri.parse("https://slang-backend-mp4-videos.s3.amazonaws.com/twenty/Liz_10.mp4");
            Log.d("HI",uri.toString());
            vv.setVideoURI(uri);
            vv.start();
            vv.setOnCompletionListener ( new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    vv.start();
                }
            });
        }
    }
}
