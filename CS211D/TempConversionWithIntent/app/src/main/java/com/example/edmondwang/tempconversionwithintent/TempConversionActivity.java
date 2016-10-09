package com.example.edmondwang.tempconversionwithintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class TempConversionActivity extends AppCompatActivity
{
    private EditText et;
    private TextView output;
    //0 for C to F
    //1 for F to C
    private int choice;
    private String num;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Check if password is right
        String submittedPass = getIntent().getStringExtra
                ("password");
        //If wrong password, then send back to first page
        if(!submittedPass.equals(getResources().getString(R
                .string.realPassword)))
        {
            Toast.makeText(getBaseContext(),"Wrong Password",
                    Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(),
                    PasswordActivity.class);
            startActivity(i);
            finish();
        }
        //Else proceed as normal
        else
        {
            setContentView(R.layout.activity_temp_conversion);
            //initialize choice to 0 by default
            choice = 0;
            TextView tv = (TextView)findViewById(
                    R.id.description);
            tv.setText(getResources().getString(
                    R.string.cToFPrompt));
            et = (EditText)findViewById(R.id.temp);
            output = (TextView)findViewById(R.id.outputText);
            et.setOnKeyListener(new View.OnKeyListener()
            {
                public boolean onKey(View v, int keyCode,
                                     KeyEvent ke)
                {
                    //When we hit enter, we do the same as hitting
                    //the convert button
                    if((ke.getAction()==KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER))
                    {
                        num = et.getText().toString();
                        convertTemp((Button)findViewById( R.id
                                .convertButton));
                        return(true);
                    }
                    return(false);
                }
            });
        }


    }

    //***************buttonHandler()******************************
    //Determines what to do when a radio button is selected
    public void buttonHandler(View v)
    {
        boolean checked = ((RadioButton) v).isChecked();
        TextView tv = (TextView)findViewById(R.id.description);
        switch(v.getId())
        {
            case R.id.cToF:
                if(checked)
                {
                    choice = 0;
                    tv.setText(getResources().getString(R.string
                            .cToFPrompt));
                }
                break;
            case R.id.fToC:
                if(checked)
                {
                    choice = 1;
                    tv.setText(getResources().getString(R.string
                            .fToCPrompt));
                }
                break;
            default:
                break;
        }
    }

    //*********************convertTemp()**************************
    //Converts the temperature from C to F or vice versa
    public void convertTemp(View v)
    {
        //Do this to hide the keyboard
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context
                        .INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow
                (getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        //Get the input value as a string
        num = et.getText().toString();
        //If the string is empty then we leave
        if(num.length() == 0)
        {
            Toast.makeText(getBaseContext(),"Enter a " +
                    "temperature!", Toast.LENGTH_SHORT).show();
            return;
        }
        Double inVal = Double.valueOf(num);
        Double outVal;
        String outString;
        //Used to get rid of trailing zeros
        DecimalFormat format = new DecimalFormat("0.#");
        switch(choice)
        {
            case 0:
                outVal = inVal*9/5 + 32;
                outString = format.format(inVal) + getResources
                        ().getString(R.string.inCelsius) +
                        getResources().getString(R.string.is) +
                        format.format(outVal) + getResources()
                        .getString(R.string.inFahrenheit);
                output.setText(outString);
                break;
            case 1:
                outVal = (inVal - 32)*5/9;
                outString = format.format(inVal) + getResources
                        ().getString(R.string.inFahrenheit) +
                        getResources().getString(R.string.is) +
                        format.format(outVal) + getResources()
                        .getString(R.string.inCelsius);
                output.setText(outString);
                break;
            default:
                //No longer necessary since I now set by default
                Toast.makeText(getBaseContext(),"Select a " +
                        "conversion!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
