package com.example.edmondwang.statesgame;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class GameActivity extends AppCompatActivity
{
    private static final String TAG = GameActivity.class
            .getName();

    String[][] stateCapitals;
    String name,answer;
    Integer score, row, column;
    TextView scoreTV,nameTV,questionTV;
    EditText answerEditText;
    RadioGroup answerRadio;
    boolean secondaryQuestion;
    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initializeVariables();
    }
    //********************initializeVariables()*******************
    void initializeVariables()
    {
        Intent intent = getIntent();
        //setup views
        name = intent.getStringExtra("name");
        score = 0;
        nameTV = (TextView)findViewById(R.id.nameTextView);
        nameTV.setText(getResources().getString(R.string.name,
                name));
        scoreTV = (TextView)findViewById(R.id.scoreTextView);
        scoreTV.setText(getResources().getString(R.string
                .score,String.valueOf(score)));
        questionTV = (TextView)findViewById(R.id
                .questionTextView);
        answerEditText = (EditText) findViewById(R.id
                .answerEditText);
        answerEditText.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent
                    ke)
            {
                //When we hit enter, go to next activity
                if ((ke.getAction() == KeyEvent.ACTION_DOWN)
                        &&(keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    findViewById(R.id.submitButton)
                            .performClick();
                    return(true);
                }
                return(false);
            }
        });
        answerRadio = (RadioGroup)findViewById(R.id.radioGroup);

        //setup row and column
        rand = new Random();
        column = rand.nextInt(2);
        row = 0;
        secondaryQuestion = false;

        //retrieve the array of states and capitals
        Object[] objectArray = (Object[]) intent.getExtras().
                getSerializable("randoms");
        if(objectArray!=null){
            stateCapitals = new String[objectArray.length][];
            for(int i=0;i<objectArray.length;i++){
                stateCapitals[i]=(String[]) objectArray[i];
                Log.i(TAG,stateCapitals[i][0] + " " +
                        stateCapitals[i][1]);
            }
        }
        Log.i(TAG, getResources().getString(R.string
                .capital_or_state,
                stateCapitals[row][column]));
        //set first question
        setQuestion();
    }
    //*********************submitHandler()************************
    public void submitHandler(View v)
    {
        if(secondaryQuestion)
        {
            //if correct then increase score
            if(answerEditText.getText().toString()
                    .toLowerCase
                    ().equals(answer.toLowerCase()))
            {
                score += 10;
                scoreTV.setText(getResources().getString(R.string
                        .score,String.valueOf(score)));
                Toast.makeText(getBaseContext(),"Correct!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext()," Wrong! The " +
                        "correct answer is " + answer,  Toast
                        .LENGTH_SHORT).show();
            }
            secondaryQuestion = false;
            if(row < 4)
            {
                row++;
                column = rand.nextInt(2);
                setQuestion();
            } else
            {
                insertNameAndScore();
                Intent i = new Intent(getApplicationContext(),
                        ScoreActivity.class);
                startActivity(i);
                finish();
            }
        } else
        {
            if(!(answerRadio.getCheckedRadioButtonId() < 0))
            {
                RadioButton rb = (RadioButton) findViewById
                        (answerRadio.getCheckedRadioButtonId());
                if (answer == String.valueOf(answerRadio
                        .indexOfChild(rb)))
                {
                    Toast.makeText(getBaseContext(),"Correct!",
                            Toast.LENGTH_SHORT).show();
                    score += 10;
                    scoreTV.setText(getResources().getString( R
                            .string.score,String.valueOf(score)));
                    secondaryQuestion = true;
                    setQuestion();
                } else
                {
                    if(answer == "0")
                    {
                        Toast.makeText(getBaseContext(),"Wrong!" +
                                " The correct answer was " +
                                "state!", Toast.LENGTH_SHORT)
                                .show();
                    } else
                    {
                        Toast.makeText(getBaseContext(),"Wrong!" +
                                " The correct answer was " +
                                "capital!", Toast.LENGTH_SHORT)
                                .show();
                    }
                    if (row < 4)
                    {
                        row++;
                        column = rand.nextInt(2);
                        setQuestion();
                    } else
                    {
                        insertNameAndScore();
                        Intent i = new Intent
                                (getApplicationContext(),
                                        ScoreActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            } else
            {
                Toast.makeText(getBaseContext(), "Select an " +
                        "answer!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //*************************setQuestion()**********************
    void setQuestion()
    {
        String question = "";
        Resources r = getResources();
        if(!secondaryQuestion)
        {
            question = r.getString(R.string.capital_or_state,
                    stateCapitals[row][column]);
            answer = String.valueOf(column);
            answerRadio.setVisibility(View.VISIBLE);
            answerEditText.setVisibility(View.INVISIBLE);
            answerEditText.setText("");
        } else
        {   //second question for when they answer first correctly
            if(column == 0)
            {
                question = r.getString(R.string.capital_question,
                        stateCapitals[row][column]);
                //if currently asking about the state, now we
                // want to know the capital
                answer = stateCapitals[row][1];
            } else
            {
                question = r.getString(R.string.state_question,
                        stateCapitals[row][column]);
                answer = stateCapitals[row][0];
            }
            answerRadio.setVisibility(View.INVISIBLE);
            answerRadio.clearCheck();
            answerEditText.setVisibility(View.VISIBLE);
        }
        questionTV.setText(question);
    }
    //*******************insertNameAndScore()*********************
    void insertNameAndScore()
    {

        SQLiteDatabase db = openOrCreateDatabase("statesDB.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,null);
        db.setVersion(1);
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("score",String.valueOf(score));
        db.insertOrThrow("scores",null,cv);
    }

}
