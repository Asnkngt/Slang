package com.slang.slang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private class QuizHandler implements View.OnClickListener {
        String category;
        public QuizHandler(String category){
            this.category = category;
        }

        @Override
        public void onClick(View v) {
            /*
            startActivity(new Intent(QuizActivity.this, FlashCardActivity2.class)
                    .putExtra(FlashCardActivity2.SwitchKey, FlashCardActivity2.QuizActivityCase)
                    .putExtra(FlashCardActivity2.DataKey, category));*/
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ArrayList<String> lessonNames = APIClient.GetCategories();//getResources().getStringArray(R.array.lesson_names);
        LinearLayout ll = findViewById(R.id.quizzes);

        for(int i = 0;i < lessonNames.size(); i++)
        {
            Button b = new Button(this);
            b.setText(lessonNames.get(i));
            b.setOnClickListener(new QuizActivity.QuizHandler(lessonNames.get(i)));
            ll.addView(b);
        }
    }
}
