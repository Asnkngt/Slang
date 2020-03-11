package com.slang.slang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity {

    private class Module implements View.OnClickListener {
        public String name;
        public Class<?> aClass;
        public Module(String name, Class<?> activity) {
            this.name = name;
            this.aClass = activity;
        }

        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, aClass));
        }
    }
    private Module[] modules = {
            new Module("Dictionary", DictionaryActivity.class),
            new Module("Flash Cards", FlashCardActivity.class),
            new Module("Quizzes", QuizActivity.class),
    };

    private void initTable(int buttonPerRow){
        if(buttonPerRow<1){
            buttonPerRow = 1;
        }
        TableLayout moduleLayout = (TableLayout) findViewById(R.id.ModuleTable);
        moduleLayout.removeAllViews();

        TableRow.LayoutParams param = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f
        );

        int curr = 0;
        while (curr < modules.length) {
            TableRow tableRow = new TableRow(this);
            tableRow.setWeightSum(buttonPerRow);
            moduleLayout.addView(tableRow);
            for(int i = 0; i < buttonPerRow; ++i, ++curr){
                Button button = new Button(this);
                if(curr < modules.length) {
                    button.setText(modules[curr].name);
                    button.setLayoutParams(param);
                    button.setWidth(0);
                    button.setOnClickListener(modules[curr]);
                }
                tableRow.addView(button, i);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTable(2);

        //APIClient.GetCategories();
        //APIClient.GetTermsInCategory("numbers");
    }
}
