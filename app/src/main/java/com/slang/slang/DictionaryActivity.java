package com.slang.slang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DictionaryActivity extends AppCompatActivity implements View.OnClickListener {

    EditText term;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        term = findViewById(R.id.searchedTerm);
        button = findViewById(R.id.button);

        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(DictionaryActivity.this, LessonActivity.class)
                .putExtra(LessonActivity.SwitchKey, LessonActivity.DictionaryActivityCase)
                .putExtra(LessonActivity.DataKey, term.getText().toString()));
    }
}
