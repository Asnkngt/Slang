package com.slang.slang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
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
    private MediaController mediaController;
    private ArrayList<String> terms = new ArrayList<String>();

    private int currVocabTermIndex = -1;
    private ArrayList<Integer> vocabTermIndices;

    private Button selectionButton1;
    private Button selectionButton2;
    private Button selectionButton3;
    private Button selectionButton4;

    // Progress and performance indicators
    private int answered = 0;
    private int answeredCorrectly = 0;
    static int MAX_NUM_QUESTIONS = 10;
    private TextView progressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        Intent intent = getIntent();
        data = intent.getStringExtra(DataKey);

        // Set title bar to the current quiz category name
        TextView titleText = findViewById(R.id.category_name);
        titleText.setText(data.substring(1, data.length() - 1));

        // Get handles for each UI element
        selectionButton1 = findViewById(R.id.selection1);
        selectionButton2 = findViewById(R.id.selection2);
        selectionButton3 = findViewById(R.id.selection3);
        selectionButton4 = findViewById(R.id.selection4);
        vv = findViewById(R.id.videoView);
        progressText = findViewById(R.id.progress);

        // Load all vocab terms in the quiz category
        terms = APIClient.GetTermsInCategory(data.replaceAll("\"",""));

        // Render the video
        vv.post(new Runnable() {
            @Override
            public void run() {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
                Rect rect = new Rect(
                        0,
                        vv.getHeight() / 40,
                        displayMetrics.widthPixels,
                        (int)(vv.getHeight() / 2)
                );
                vv.setClipBounds(rect);
            }
        });
        mediaController = new MediaController(this);
        mediaController.setAnchorView(vv);

        // Toggle pause/play video functionality
        vv.setOnClickListener(new View.OnClickListener() {
            boolean paused = false;
            @Override
            public void onClick(View v) {
                if (paused) {
                    vv.start();
                } else {
                    vv.pause();
                }
                paused = !paused;
            }
        });

        // Replay video on completion functionality
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                vv.start();
            }
        });

        selectionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAnswer(0);
            }
        });
        selectionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAnswer(1);
            }
        });
        selectionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAnswer(2);
            }
        });
        selectionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAnswer(3);
            }
        });

        loadNewQuestion();
    }

    private void loadNewQuestion() {
        Random rand = new Random();
        vocabTermIndices = new ArrayList<Integer>();

        // Populate the multiple-choice answers with random words in the quiz category
        while (vocabTermIndices.size() < 4) {
            int newVocabTermIndex = rand.nextInt((terms.size() / 2)) * 2;
            // Choose a new vocab term different from the previous one
            if (newVocabTermIndex != currVocabTermIndex && !vocabTermIndices.contains(newVocabTermIndex)) {
                vocabTermIndices.add(newVocabTermIndex);
            }
        }
        selectionButton1.setText(terms.get(vocabTermIndices.get(0)));
        selectionButton2.setText(terms.get(vocabTermIndices.get(1)));
        selectionButton3.setText(terms.get(vocabTermIndices.get(2)));
        selectionButton4.setText(terms.get(vocabTermIndices.get(3)));

        // Set the current vocab term to be one of the multiple-choice answers
        currVocabTermIndex = vocabTermIndices.get(rand.nextInt(4));

        // Render the video of the newly selected vocab term
        Uri uri = Uri.parse(terms.get(currVocabTermIndex + 1));
        vv.setVideoURI(uri);
        vv.start();

        // Update progress
        progressText.setText(answeredCorrectly + " Correct \n" + answered + "/" + MAX_NUM_QUESTIONS);
        progressText.setGravity(Gravity.RIGHT);
    }

    // Display correct or wrong depending on answer selected
    public void chooseAnswer(int buttonIndex) {
        String popupText;
        if (currVocabTermIndex == vocabTermIndices.get(buttonIndex)) {
            popupText = "Correct!";
            answeredCorrectly += 1;
        } else {
            popupText = "Wrong! The correct answer was \"" + terms.get(currVocabTermIndex) + "\"";
        }
        answered += 1;
        Toast.makeText(this, popupText, Toast.LENGTH_SHORT).show();

        if (answered >= MAX_NUM_QUESTIONS) {
            // Once the quiz is complete, set the view to the results page
            setContentView(R.layout.activity_quiz_complete);

            TextView results = findViewById(R.id.results);
            Button returnToQuizzesButton = findViewById(R.id.returnToQuizzesButton);

            results.setText("Your score is: " + answered + "/" + MAX_NUM_QUESTIONS );
            results.setGravity(Gravity.CENTER);

            returnToQuizzesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        else {
            loadNewQuestion();
        }
    }

    // Once the quiz is complete, redirect the user to the QuizCompletionPage
//    private class QuizHandler implements View.OnClickListener {
////        String category;
////        public QuizHandler(String category){
////            this.category = category;
////        }
//
//        @Override
//        public void onClick(View v) {
//            startActivity(new Intent(QuizActivity2.this, QuizCompleteActivity.class));
////            startActivity(new Intent(QuizActivity2.this, QuizCompleteActivity.class)
////                    .putExtra(QuizCompleteActivity.DataKey, category));
//        }
//    }
}