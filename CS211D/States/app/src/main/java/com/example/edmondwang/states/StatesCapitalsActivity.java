/* Author: Edmond Wang
 * Homework Assignment: 6
 * Date: 10/31/16
 * Reads US_States file from internal storage and then returns
 * corresponding state/capital depending on the user's input.
 */
package com.example.edmondwang.states;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StatesCapitalsActivity extends AppCompatActivity
{
    Map <String, String> stateCapitalMap;
    EditText et;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states_capitals);
        et = (EditText)findViewById(R.id.inputEditText);
        tv = (TextView) findViewById(R.id.outputTextView);
        stateCapitalMap = new HashMap<>();
        loadMap();
    }

    //********************loadMap()*******************************
    void loadMap()
    {
        try
        {
            FileInputStream fis;
            if(checkForSD())
            {
                checkStoragePermissions();
                File sdCard = Environment
                        .getExternalStorageDirectory();
                File f = new File(sdCard, "Download/US_states");
                fis = new FileInputStream(f);
            }
            else
            {
                fis = openFileInput("US_states");
            }
            //With inputstreamreader, keep for reference
//            InputStreamReader inputStreamReader = new
//                    InputStreamReader(fis);
//            BufferedReader bufferedReader = new BufferedReader
//                    (inputStreamReader);
//            String line;
//            //Get rid of first two lines in text file
//            bufferedReader.readLine();
//            bufferedReader.readLine();
//            while ((line = bufferedReader.readLine()) != null)
//            {
//                String[] split = line.split("\\s\\s+");
//                stateCapitalMap.put(split[0],split[1]);
//            }
            Scanner scanner = new Scanner(fis);
            String line;
            scanner.nextLine();
            scanner.nextLine();
            while(scanner.hasNextLine())
            {
                line = scanner.nextLine();
                String[] split = line.split("\\s\\s+");
                stateCapitalMap.put(split[0],split[1]);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "File not found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //*******************submitHandler()**************************
    public void submitHandler(View v)
    {
        String input = et.getText().toString();
        String output = stateCapitalMap.get(input);
        String fullOutputString;
        if(output == null)
        {
            //if null then maybe entered a capital
            for (Map.Entry<String, String> entry : stateCapitalMap
                    .entrySet()) {
                if(entry.getValue().equals(input))
                {
                    output = entry.getKey();
                }
            }
            if(output == null) //neither state nor capital
            {
                fullOutputString = getResources().getString(R
                        .string.error);
            }
            else
            {
                fullOutputString = input + " " + getResources()
                        .getString(R.string.outText)+" "+output;
            }
        }
        else //input is a state
        {
            fullOutputString = output + " " + getResources()
                    .getString(R.string.outText) + " " + input;
        }
        tv.setText(fullOutputString);
    }

    //**************************checkForSD()**********************
    boolean checkForSD()
    {
        boolean storageAvailable = false;
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state))
        {
            storageAvailable = true;
        }

        return storageAvailable;
    }

    //*******************checkStoragePermissions()****************
    public  boolean checkStoragePermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission
                    .READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(this, new
                        String[] {Manifest.permission
                        .READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            return true;
        }
    }

    //***********onRequestPermissionResult()**********************
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            loadMap();
        }
    }
}
