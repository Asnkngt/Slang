package com.slang.slang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

public class FlashCardActivity extends AppCompatActivity {
    public static final String LESSON_NAME = "com.slang.slang.lesson_name";

    private class LessonHandler implements View.OnClickListener {
        String category;
        public LessonHandler(String category){
            this.category = category;
        }

        @Override
        public void onClick(View v) {
            startActivity(new Intent(FlashCardActivity.this, FlashCardActivity2.class)
                    .putExtra(FlashCardActivity2.DataKey, category));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        // Adding buttons to the vew based on the list of lesson names
        List<String> lessonNames = APIClient.GetCategories();//getResources().getStringArray(R.array.lesson_names);
        LinearLayout ll = findViewById(R.id.lessons);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 10, 0, 10);

        for(int i = 0;i < lessonNames.size(); i++)
        {
            Button b = new Button(this);
            b.setLayoutParams(params);
            b.setText(lessonNames.get(i).substring(1, lessonNames.get(i).length()-1));
            b.setTextSize(19);
            b.setBackgroundColor(getResources().getColor(R.color.button));
            b.setOnClickListener(new LessonHandler(lessonNames.get(i)));
            ll.addView(b);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("Testing", "onPause");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("Testing", "onDestroy");
    }

    public void selectLesson(View view){
        Intent intent = new Intent(this, FlashCardActivity2.class);
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        intent.putExtra(LESSON_NAME, buttonText);
        startActivity(intent);
    }

}
