package com.slang.slang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
        if(term.getText().length()==0){return;}
        startActivity(new Intent(DictionaryActivity.this, DictionaryActivity2.class)
                .putExtra(DictionaryActivity2.DataKey, term.getText().toString().toLowerCase()));
    }
}
