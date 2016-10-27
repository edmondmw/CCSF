/* Author: Edmond Wang
 * Homework Assignment: 5
 * Date: 10/24/16
 * Select which shape you want to draw then get sent to the
 * next screen which allows you to draw as many of the
 * specified shape as you want.
 */
package com.example.edmondwang.drawingshapeshw5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
                selection = 0;
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
        else
        {
            Toast.makeText(getApplication().getBaseContext(),
                    "Choose a shape!", Toast.LENGTH_SHORT).show();
        }
    }
}
