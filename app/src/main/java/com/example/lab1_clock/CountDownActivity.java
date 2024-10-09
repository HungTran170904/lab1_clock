package com.example.lab1_clock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Timer;
import java.util.TimerTask;

public class CountDownActivity extends AppCompatActivity {
    private TextView countDownTv;
    private Button pauseBtn, startBtn, backBtn;
    private Timer timer;
    private int hour, minute, second;
    private boolean isTimerRunning=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_count_down);

        countDownTv= findViewById(R.id.countDownTv);
        pauseBtn= findViewById(R.id.pausebtn);
        startBtn= findViewById(R.id.startbtn);
        backBtn= findViewById(R.id.backBtn);

        Intent intent= getIntent();
        hour= intent.getIntExtra("hour",0);
        minute= intent.getIntExtra("minute",0);
        second= intent.getIntExtra("second",0);

        startTimer();

        pauseBtn.setOnClickListener(v->{
            timer.cancel();
        });

        startBtn.setOnClickListener(v->{
                startTimer();
        });

        backBtn.setOnClickListener(v->{
            finish();
        });
    }

    private void startTimer(){
        timer= new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateTimer();
            }
        },0,1000);
    }

    private void updateTimer() {
        if (hour == 0 && minute == 0 && second == 0) {
            timer.cancel();
            Toast.makeText(this,
                    "Time is up",
                    Toast.LENGTH_LONG).show();
        } else {
            if (second == 0) {
                if (minute == 0) {
                    hour--;
                    minute = 59;
                } else {
                    minute--;
                }
                second = 59;
            } else {
                second--;
            }
        }
        countDownTv.setText(String.format("%02d:%02d:%02d", hour, minute, second));
    }
}