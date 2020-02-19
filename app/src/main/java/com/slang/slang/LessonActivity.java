package com.slang.slang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LessonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        Intent intent = getIntent();
        String lessonName = intent.getStringExtra(LessonsActivity.LESSON_NAME);

        TextView textView = findViewById(R.id.lesson_name);
        textView.setText(lessonName);

    }
}
