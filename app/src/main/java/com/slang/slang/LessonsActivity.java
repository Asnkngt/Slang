package com.slang.slang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class LessonsActivity extends AppCompatActivity {
    public static final String LESSON_NAME = "com.slang.slang.lesson_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        // Adding buttons to the vew based on the list of lesson names
        String[] lessonNames = getResources().getStringArray(R.array.lesson_names);
        LinearLayout ll = findViewById(R.id.lessons);

        for(int i = 0;i < lessonNames.length; i++)
        {
            Button b = new Button(this);
            b.setText(lessonNames[i]);
            ll.addView(b);
        }

    }

    public void selectLesson(View view){
        Intent intent = new Intent(this, LessonActivity.class);
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        intent.putExtra(LESSON_NAME, buttonText);
        startActivity(intent);
    }

}
