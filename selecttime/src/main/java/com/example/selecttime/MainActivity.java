package com.example.selecttime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends Activity {
    WheelMain wheelMain;
    EditText editText;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        editText = (EditText) findViewById(R.id.et_time);
        Calendar calendar = Calendar.getInstance();
        editText.setText(calendar.get(Calendar.YEAR) + "-" +
                (calendar.get(Calendar.MONTH) + 1) + "-" +
                (calendar.get(Calendar.DAY_OF_MONTH) + "-") +
                (calendar.get(Calendar.HOUR_OF_DAY) + "-") +
                calendar.get(Calendar.MINUTE) + "");
        Button btnSelect = (Button) findViewById(R.id.btn_select);
        btnSelect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                setTheme(android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                final View timepickerview = inflater.inflate(R.layout.timepicker, null);
                ScreenInfo screenInfo = new ScreenInfo(MainActivity.this);
                wheelMain = new WheelMain(timepickerview);
                wheelMain.setHasYear(true);
                wheelMain.setHasMonth(true);
                wheelMain.setHasDay(true);
                wheelMain.setHasHour(true);
                wheelMain.setHasMinute(true);
                wheelMain.setHasSecond(true);
                wheelMain.screenheight = screenInfo.getHeight();
                String time = editText.getText().toString();
                Calendar calendar = Calendar.getInstance();
                if (JudgeDate.isDate(time, "yyyy-MM-dd hh:mm")) {
                    try {
                        calendar.setTime(dateFormat.parse(time));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get((Calendar.MINUTE));
                wheelMain.initDateTimePicker(year, month, day, hour, minute);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("选择时间")
                        .setView(timepickerview)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editText.setText("");
                                editText.append(wheelMain.getCookedTime());
                                editText.append("\n");
                                editText.append(wheelMain.getOriginalTime());
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }
}