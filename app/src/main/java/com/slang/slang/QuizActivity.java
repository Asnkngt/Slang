package com.slang.slang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private class QuizHandler implements View.OnClickListener {
        String category;
        public QuizHandler(String category){
            this.category = category;
        }

        @Override
        public void onClick(View v) {
            startActivity(new Intent(QuizActivity.this, QuizActivity2.class)
                    .putExtra(QuizActivity2.DataKey, category));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        List<String> lessonNames = APIClient.GetCategories();//getResources().getStringArray(R.array.lesson_names);
        LinearLayout ll = findViewById(R.id.quizzes);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 10, 0, 10);

        for (int i = 0; i < lessonNames.size(); i++) {
            Button b = new Button(this);
            b.setLayoutParams(params);
            b.setText(lessonNames.get(i).substring(1, lessonNames.get(i).length()-1));
            b.setTextSize(19);
            b.setBackgroundColor(getResources().getColor(R.color.button));
            b.setOnClickListener(new QuizActivity.QuizHandler(lessonNames.get(i)));
            ll.addView(b);
        }
    }
}
