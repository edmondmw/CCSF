package com.example.edmondwang.drawingshapeshw5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class DrawingActivity extends AppCompatActivity
{
    int selection;
    DrawView drawView;
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
        drawView = (DrawView)findViewById(R.id.drawView);
        drawView.setSelection(selection);
    }

    //*****************drawHandler()******************************
    public void drawHandler(View v)
    {
        switch(selection)
        {
            case 1:
                //call drawCircle;
                drawView.generateCircle();
                break;
            case 2:
                drawView.generateTriangle();
                break;
            case 3:
                drawView.generateRectangle();
                break;
            default:
                break;
        }
    }
    //****************clearScreen()*******************************
    public void clearScreen(View v)
    {
        drawView.clearScreen();
    }

}
