package com.example.edmondwang.drawingshapeshw5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectionActivity extends AppCompatActivity
{
    int selection;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
    }

    //******************radioHandler()****************************
    public void radioHandler(View v)
    {
        switch(v.getId())
        {
            case R.id.circleButton:
                selection = 1;
                break;
            case R.id.triangleButton:
                selection = 2;
                break;
            case R.id.rectangleButton:
                selection = 3;
                break;
            default:
                break;
        }
    }

    //*******************startClicked()***************************
    public  void startClicked(View v)
    {
        if(selection > 0)
        {
            Intent i = new Intent(getApplicationContext(),
                    DrawingActivity.class);
            i.putExtra("selection", selection);
            startActivity(i);
        }
    }
}
