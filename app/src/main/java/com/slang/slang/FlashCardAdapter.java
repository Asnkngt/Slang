package com.slang.slang;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class FlashCardAdapter extends RecyclerView.Adapter<FlashCardAdapter.ViewHolder> {
    private ArrayList<FlashCardObject> flashCardObjects;
    private Context context;

    public FlashCardAdapter(Context context, ArrayList<FlashCardObject> collabList) {
        this.flashCardObjects = collabList;
        this.context = context;
    }

    @Override
    public FlashCardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.d("HIII",""+i);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.object_flashcard, viewGroup,false);
        return new FlashCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlashCardAdapter.ViewHolder viewHolder, int i) {
        MediaController mediacontroller = new MediaController(context);
        mediacontroller.setAnchorView(viewHolder.video);

        viewHolder.text.setText(flashCardObjects.get(i).getTerm());
        flashCardObjects.get(i).setVideoView(viewHolder.video);

        viewHolder.video.setVideoURI(flashCardObjects.get(i).getUri());
    }

    @Override
    public int getItemCount() {
        return flashCardObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView text;
        private VideoView video;

        public ViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.vocab_name);
            video = view.findViewById(R.id.videoView);
        }
    }
}
