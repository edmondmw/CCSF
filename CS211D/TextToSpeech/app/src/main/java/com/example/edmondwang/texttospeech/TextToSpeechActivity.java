/* Author: Edmond Wang
 * Extra Credit Assignment: 3
 * Date: 12/12/16
 * Able to convert text to speech. Can also adjust the language
  * and the pitch, but can't change voice. I was uncertain
  * about what that meant, so it was not implemented.
 */
package com.example.edmondwang.texttospeech;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

public class TextToSpeechActivity extends AppCompatActivity
{
    EditText nameET,  pitchET;
    Button startButton, stopButton;
    TextToSpeech tts;
    ArrayList<String> languages;
    ArrayAdapter<String> arrayAdapter;
    Spinner languageSpinner, voiceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        startButton = (Button)findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        nameET = (EditText) findViewById(R.id.nameET);
        pitchET = (EditText) findViewById(R.id.pitchET);
        languageSpinner = (Spinner) findViewById(R.id
                .languageSpinner);

        //Populate language array list
        languages = new ArrayList<>();
        languages.add("English(US)");
        languages.add("English(UK)");
        languages.add("French");
        languages.add("German");
        languages.add("Italian");
        languages.add("Spanish");

        tts = new TextToSpeech(this,
                new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int i)
            {
                if(i != TextToSpeech.ERROR)
                {
                    tts.setLanguage(Locale.US);
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(pitchET.getText().toString() != "")
                {
                    tts.setPitch(Float.valueOf(pitchET.getText()
                            .toString()));
                }

                String toSpeak = nameET.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,
                        Toast.LENGTH_SHORT).show();
                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH,
                        null);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tts.stop();
            }
        });

        languageSpinnerSetup();
    }
    //****************languageSpinnerSetup()**********************
    private void languageSpinnerSetup()
    {
        arrayAdapter = new ArrayAdapter<String>(this, android.R
                .layout.simple_spinner_item, languages);
        arrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        languageSpinner.setAdapter(arrayAdapter);

        languageSpinner.setOnItemSelectedListener(new
            AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int i, long l)
            {
                languageSpinner.getSelectedItem().toString();
                setLanguage(languageSpinner.getSelectedItem()
                        .toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?>
                                                  adapterView)
            {

            }
        });
    }
    //****************setLanguage()*******************************
    private void setLanguage(String selection)
    {
        int result;
        switch(selection)
        {
            case "English(US)":
                result = tts.setLanguage(Locale.US);
                break;
            case "English(UK)":
                result = tts.setLanguage(Locale.UK);
                break;
            case "French":
                result = tts.setLanguage(Locale.FRANCE);
                break;
            case "German":
                result = tts.setLanguage(Locale.GERMANY);
                break;
            case "Italian":
                result = tts.setLanguage(Locale.ITALY);
                break;
            case "Spanish":
                result = tts.setLanguage(new Locale("es","ES"));
                break;
            default:
                result = tts.setLanguage(Locale.US);
                break;
        }
        if(result == TextToSpeech.LANG_MISSING_DATA||result ==
                TextToSpeech.LANG_NOT_SUPPORTED)
        {
            Toast.makeText(getApplicationContext(),"Language " +
                    "not supported",Toast.LENGTH_SHORT).show();
            startButton.setEnabled(false);
        }
        else
        {
            startButton.setEnabled(true);
        }
    }
}
