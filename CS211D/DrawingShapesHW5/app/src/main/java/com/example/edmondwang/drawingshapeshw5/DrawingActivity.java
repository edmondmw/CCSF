package com.example.edmondwang.drawingshapeshw5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DrawingActivity extends AppCompatActivity
{
    int selection;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        selection = getIntent().getIntExtra("selection", 0);
        if(selection <= 0)
        {
            Toast.makeText(getBaseContext(), "Error: select a " +
                    "shape!", Toast.LENGTH_SHORT).show();
            finish();
        }

        switch(selection)
        {
            //circle
            case 1:
                setContentView(new DrawCircle(this, null));
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;

        }
    }

    //*****************drawHandler()******************************
    public void drawHandler(View v)
    {
        switch(selection)
        {
            case 1:
                //call drawCircle;
                break;
            case 2:
                //call draw tri
                break;
            case 3:
                //call drawRect
                break;
            default:
                break;
        }
    }


}
