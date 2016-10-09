/* Author: Edmond Wang
 * Extra Credit Assignment
 * Date: 10/10/16
 * This program is able to convert temperature from Celsius to
 * Fahrenheit and vice versa so long as you input the correct
 * password.
 */

package com.example.edmondwang.tempconversionwithintent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity
{
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        et = (EditText)findViewById(R.id.passwordET);
        et.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode,
                                 KeyEvent ke)
            {
                //When we hit enter, we do the same as hitting
                //the submit button
                if((ke.getAction()==KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    submitHandler((Button) findViewById(R.id
                            .submitPasswordButton));
                    return(true);
                }
                return(false);
            }
        });
    }

    //***************submitHandler()******************************
    public void submitHandler(View v)
    {
        //Send to the conversion activity if a password was
        // entered.
        if(et.getText().length() > 0)
        {
            Intent i = new Intent(getApplicationContext(),
                    TempConversionActivity.class);
            i.putExtra("password", et.getText().toString());
            startActivity(i);
            finish();
        }
        else
        {
            Toast.makeText(getBaseContext(),"Enter a " +
                    "password!", Toast.LENGTH_SHORT).show();
        }
    }
}
