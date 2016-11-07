package com.example.edmondwang.statesgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity
{
    String name;
    Integer score;
    int[] randomIDs;
    TextView scoreTV;
    TextView nameTV;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        randomIDs = i.getIntArrayExtra("randoms");
        score = 0;
        nameTV = (TextView)findViewById(R.id.nameTextView);
        nameTV.append("\n" + name);
        scoreTV = (TextView)findViewById(R.id.scoreTextView);
        scoreTV.append("\n" + String.valueOf(score));
    }


}
