package com.slang.slang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class FlashCardActivity2 extends AppCompatActivity {

    static final String DataKey = "DATA";
    private String data = null;

    RecyclerView flashcards;
    FlashCardAdapter adapter;
    ArrayList<FlashCardObject> flashCardObjects;
    public class SnapHelperOneByOne extends LinearSnapHelper{

        @Override
        public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY){

            if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
                return RecyclerView.NO_POSITION;
            }

            final View currentView = findSnapView(layoutManager);

            if( currentView == null ){
                return RecyclerView.NO_POSITION;
            }

            final int currentPosition = layoutManager.getPosition(currentView);

            if (currentPosition == RecyclerView.NO_POSITION) {
                return RecyclerView.NO_POSITION;
            }

            flashCardObjects.get(currentPosition).restart();

            return currentPosition;
        }
    }

    private ArrayList<FlashCardObject> prepareData(String category){
        ArrayList<FlashCardObject> flashCardObjects = new ArrayList<FlashCardObject>();
        ArrayList<String> data = APIClient.GetTermsInCategory(category.replaceAll("\"",""));
        for(int i=0;i<data.size();i+=2){
            flashCardObjects.add(new FlashCardObject());
            flashCardObjects.get(i/2).setTerm(data.get(i));
            flashCardObjects.get(i/2).setUri(Uri.parse(data.get(i+1)));
        }
        /*
        flashCardObjects.add(new FlashCardObject());
        flashCardObjects.add(new FlashCardObject());
        flashCardObjects.add(new FlashCardObject());

        flashCardObjects.get(0).setTerm("TWENTY");
        flashCardObjects.get(1).setTerm("FAKE1");
        flashCardObjects.get(2).setTerm("FAKE2");

        flashCardObjects.get(0).setUri(Uri.parse("https://slang-backend-mp4-videos.s3.amazonaws.com/twenty/Liz_10.mp4"));
        flashCardObjects.get(1).setUri(Uri.parse("https://slang-backend-mp4-videos.s3.amazonaws.com/twenty/Liz_10.mp4"));
        flashCardObjects.get(2).setUri(Uri.parse("https://slang-backend-mp4-videos.s3.amazonaws.com/twenty/Liz_10.mp4"));
        */
        return flashCardObjects;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard2);

        Intent intent = getIntent();
        data = intent.getStringExtra(DataKey);

        flashcards = findViewById(R.id.flashcards);

        SnapHelperOneByOne helper = new SnapHelperOneByOne();
        helper.attachToRecyclerView(flashcards);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        flashcards.setLayoutManager(layoutManager);

        flashCardObjects = prepareData(data);
        adapter = new FlashCardAdapter(getApplicationContext(), flashCardObjects);

        flashcards.setAdapter(adapter);
    }
}
