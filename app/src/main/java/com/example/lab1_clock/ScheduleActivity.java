package com.example.lab1_clock;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class ScheduleActivity extends AppCompatActivity {
    private Calendar calendar= Calendar.getInstance();
    private int REQUEST_CODE= 9;

    private EditText messageEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        TextView dateTv= findViewById(R.id.dateTv);
        TextView timeTv= findViewById(R.id.timeTv);
        Button dateBtn= findViewById(R.id.dateBtn);
        Button timeBtn= findViewById(R.id.timeBtn);
        Button createBtn= findViewById(R.id.createBtn);
        messageEt= findViewById(R.id.messageEt);

        dateBtn.setOnClickListener(v->{
            // Create a DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view,year,month, dayOfMonth)->{
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String dateStr= dayOfMonth+"/"+(month+1)+"/"+year;
                        dateTv.setText(dateStr);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        timeBtn.setOnClickListener(v->{
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, hours, minutes) -> {
                        calendar.set(Calendar.HOUR, hours);
                        calendar.set(Calendar.MINUTE, minutes);
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);
                        timeTv.setText(hours+":"+minutes);
                    },
                    calendar.get(Calendar.HOUR),
                    calendar.get(Calendar.MINUTE),
                    false);
            timePickerDialog.show();
        });

        createBtn.setOnClickListener(v->{
            Calendar now= Calendar.getInstance();
            if(now.after(calendar)){
                Toast.makeText(this,
                        "Scheduled time must be after the current time",
                        Toast.LENGTH_LONG).show();
            }
            else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{
                                    android.Manifest.permission.POST_NOTIFICATIONS
                                },
                                REQUEST_CODE);
                    }
                    else{
                        scheduleAlarm(calendar.getTimeInMillis(), messageEt.getText().toString());
                        finish();
                    }
                }
                else{
                    scheduleAlarm(calendar.getTimeInMillis(), messageEt.getText().toString());
                    finish();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                scheduleAlarm(calendar.getTimeInMillis(), messageEt.getText().toString());
                finish();
            }
            else{
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void scheduleAlarm(long scheduledTime, String message){
        AlarmManager alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            try{
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, scheduledTime, pendingIntent);
                Toast.makeText(this, "Schedule the notification successfully", Toast.LENGTH_LONG).show();
            }
            catch(SecurityException ex){
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}