//Author: Edmond Wang
//Date: 9/12/16
//Homework Assignment: 1
//Objective: Display name, class, assignment, and semester on the
//screen. Each line is to have a different font, size, and color.
//ShowClassInfo.java
package com.example.edmondwang.homework1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class ShowClassInfo extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_class_info);

    }
}
