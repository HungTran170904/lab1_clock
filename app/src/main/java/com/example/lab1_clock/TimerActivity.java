package com.example.lab1_clock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timer);

        Button setButton = findViewById(R.id.setBtn);
        Button backBtn= findViewById(R.id.backBtn);

        NumberPicker hourPicker= findViewById(R.id.hourPicker);
        hourPicker.setMaxValue(23);
        hourPicker.setMinValue(0);

        NumberPicker minutePicker= findViewById(R.id.minutePicker);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);

        NumberPicker secondPicker= findViewById(R.id.secondPicker);
        secondPicker.setMinValue(0);
        secondPicker.setMaxValue(59);

        setButton.setOnClickListener(v->{
            int hour = hourPicker.getValue();
            int minute = minutePicker.getValue();
            int second = secondPicker.getValue();

            if (hour == 0 && minute == 0 && second == 0){
                Toast.makeText(this,
                        "Hour, minute and second input must be 0 at the same time",
                        Toast.LENGTH_LONG).show();
            }
            else{
                Intent intent= new Intent(this, CountDownActivity.class);
                intent.putExtra("hour", hour);
                intent.putExtra("minute", minute);
                intent.putExtra("second", second);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(v->{
            finish();
        });
    }
}