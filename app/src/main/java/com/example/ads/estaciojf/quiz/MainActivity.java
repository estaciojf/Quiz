package com.example.ads.estaciojf.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;

    List<RadioButton> listRadioButtons;

    ArrayList<Question> listQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "http://www.json-generator.com/api/json/get/cpvFduCnsi?indent=2";

        listQuestions = new ArrayList<>();
        listQuestions.add(new Question());

        bindViews();
        listRadioButtons = new ArrayList<RadioButton>();

        listRadioButtons.add(radioButton1);
        listRadioButtons.add(radioButton2);
        listRadioButtons.add(radioButton3);


        new JsonTask().execute(url);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioButton1){
                    selectRadioButton(radioButton1);
                    Log.i("Radio", "Radio 1");
                }

                if (i == R.id.radioButton2){
                    selectRadioButton(radioButton2);
                    Log.i("Radio", "Radio 2");

                }
                if (i == R.id.radioButton3){
                    selectRadioButton(radioButton3);
                    Log.i("Radio", "Radio 3");

                }

            }
        });

    }

    private void bindViews () {
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
    }

    private void selectRadioButton (RadioButton radioButton) {
        // ContextCompat.getColor(context, R.color.color_name)
        radioButton.setTextColor(getResources().getColor(R.color.accent));
        

        for (RadioButton radio : listRadioButtons) {
            Log.i("Radio", radio.toString() + " " + radio.isChecked());
            if (!radio.isChecked())
                radio.setTextColor(getResources().getColor(R.color.primaryText));
        }
    }

}

