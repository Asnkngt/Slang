package com.slang.slang;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

public class FlashCardObject implements View.OnClickListener, MediaPlayer.OnCompletionListener {
    private String term;
    private Uri uri;
    private VideoView videoViewRef;

    private boolean paused = true;

    public String getTerm() {
        return term;
    }

    public Uri getUri() {
        return uri;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void setVideoView(VideoView videoView) {
        paused = false;

        this.videoViewRef = videoView;
        videoViewRef.setVideoURI(uri);
        videoViewRef.setOnClickListener(this);
        videoViewRef.setOnCompletionListener(this);
        videoViewRef.start();
    }

    public void restart(){
        paused = false;
        videoViewRef.seekTo(0);
        videoViewRef.start();
    }

    @Override
    public void onClick(View v) {
        if(paused){
            Log.d("HIII","resume");
            videoViewRef.start();
        }else {
            Log.d("HIII","pause");
            videoViewRef.pause();
        }
        paused = !paused;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        videoViewRef.start();
    }
}
