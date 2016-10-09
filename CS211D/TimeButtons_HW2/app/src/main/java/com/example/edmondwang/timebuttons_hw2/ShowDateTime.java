//Author: Edmond Wang
//Date: 9/19/16
//Homework Assignment: 2
//Objective: Displays the current date when the date button is
// pushed. Displays the current time when the time button is
// pushed.
//****************************************************************
package com.example.edmondwang.timebuttons_hw2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowDateTime extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_date_time);
    }

    //*****************buttonHandler()****************************
    //Determines what to do when a button is pressed
    public void buttonHandler(View v)
    {
        switch(v.getId())
        {
            case R.id.timeButton:
                updateText(0);
                break;

            case R.id.dateButton:
                updateText(1);
                break;

            default:
                break;
        }
    }

    //*******************updateText()*****************************
    //update the textView depending on selection
    //0 is for time
    //1 is for date
    private void updateText(int selection)
    {
        TextView tv;
        DateFormat dateFormat;
        //The label before the actual time/date
        String prefix;
        if(selection == 0)
        {
            tv = (TextView) findViewById(R.id.timeText);
            dateFormat = new SimpleDateFormat(
                    "HH:mm:ss");
            prefix = getResources().getString(R.string.timeLabel);
        }
        else if(selection == 1)
        {
            tv = (TextView)findViewById(R.id.dateText);
            dateFormat = new SimpleDateFormat( "MMM " +
                    "dd, yyyy");
            prefix = getResources().getString(R.string.dateLabel);
        }
        //if it's neither of these selections then just leave
        else
        {
            return;
        }

        tv.setVisibility(View.VISIBLE);
        Date date = new Date();
        String displayText = prefix + " " + dateFormat.format
                (date);
        tv.setText(displayText);
    }
}
