package com.example.edmondwang.states;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StatesCapitalsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states_capitals);
        int ch;
        StringBuffer fileContent = new StringBuffer("");
        try
        {
            FileInputStream fis = openFileInput(
                    "US_states");
            try {
                while( (ch = fis.read()) != -1)
                    fileContent.append((char)ch);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        String data = new String(fileContent);
        System.out.println(data);
    }
}
