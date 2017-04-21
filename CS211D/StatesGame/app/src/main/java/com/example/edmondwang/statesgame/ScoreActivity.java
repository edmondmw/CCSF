package com.example.edmondwang.statesgame;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ScoreActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        SQLiteDatabase db = openOrCreateDatabase("statesDB.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        Cursor c = db.rawQuery("Select name, score from scores " +
                "order by score DESC limit 10;", null);
        String scoreTable = "";
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            scoreTable += c.getString(1)+ " " + c.getString(0) +
                    "\n";
        }
        TextView scoreTextView = (TextView) findViewById(R.id
                .scoreTableTV);
        scoreTextView.setText(scoreTable);
    }
    //******************backHandler()*****************************
    public void backHandler(View v)
    {
        finish();
    }
}
