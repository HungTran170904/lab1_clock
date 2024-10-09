package com.example.lab1_clock;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private SimpleDateFormat formatter= new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView currentTimeText= findViewById(R.id.currentTimeText);
        currentTimeText.setText(getCurrentTimeStr());
        Timer timer= new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(()->currentTimeText.setText(getCurrentTimeStr()));
            }
        },0,1000);

        Button scheduleBtn= findViewById(R.id.scheduleBtn);
        scheduleBtn.setOnClickListener(v->{
            Intent intent= new Intent(this, ScheduleActivity.class);
            startActivity(intent);
        });

        Button timerBtn= findViewById(R.id.timerBtn);
        timerBtn.setOnClickListener(v->{
            Intent intent= new Intent(this, TimerActivity.class);
            startActivity(intent);
        });
    }

    private String getCurrentTimeStr(){
        Date now= new Date();
        String formattedDate="Current Time \n"+formatter.format(now);
        return formattedDate;
    }
}