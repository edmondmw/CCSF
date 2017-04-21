/* Author: Edmond Wang
 * Homework Assignment: 7
 * Date: 11/14/16
 * A game in which we answer questions regarding whether a
 * given location is a state or a capital. If correct, we are
 * then told to specify its corresponding capital or state,
 * respectively. Scores and names are then recorded, and the
 * top 10 are displayed at the end.
 */
package com.example.edmondwang.statesgame;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class SplashActivity extends AppCompatActivity
{
    EditText et;
    SQLiteDatabase db;
    private static final String TAG = SplashActivity.class
            .getName();
    String[][] stateCapitals;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        et = (EditText)findViewById(R.id.editText);
        //create db, table, and generate 5 random
        db = openOrCreateDatabase("statesDB.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);

        if(!checkTableExists())
        {
            Log.i(TAG,"table doesn't exist");
            String createTableCmd = "create table states(id " +
                    "integer primary key autoincrement, " +
                    "state text not null, capital text); ";
            db.execSQL(createTableCmd);
            createTableCmd = "create table scores(id integer " +
                    "primary key autoincrement, name text, " +
                    "score integer);";
            db.execSQL(createTableCmd);
            loadDB();
        }
        printDatabase();
        enterSetup();
    }
    //********************enterSetup()****************************
    void enterSetup()
    {
        et.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent
                    ke)
            {
                //When we hit enter, go to next activity
                if ((ke.getAction() == KeyEvent.ACTION_DOWN)
                        &&(keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    goToGameActivity();
                    return(true);
                }
                return(false);
            }
        });
    }

    //****************initializeRandomArray()*********************
    void initializeRandomArray()
    {
        stateCapitals = new String[5][2];
        //Get 5 random ids
        String[] randomID = new String[5];
        Integer[] array = new Integer[50];
        for (int i = 0; i < 50; i++)
        {
            array[i] = i+1;
        }
        Collections.shuffle(Arrays.asList(array));
        for(int i = 0; i < 5; i++)
        {
            randomID[i] = String.valueOf(array[i]);
            Log.i(TAG, String.valueOf(randomID[i]));
        }

        //get the states and capitals using the random ids
        String selectQuery = "select state,capital from states " +
                "where id in (?,?,?,?,?)";
        Cursor c = db.rawQuery(selectQuery, randomID);
        int j = 0;
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext(),
                j++)
        {
            stateCapitals[j][0] = c.getString(0);
            stateCapitals[j][1] = c.getString(1);
            Log.i(TAG, stateCapitals[j][0] + " " +
                    stateCapitals[j][1]);
        }
    }
    //********************goToGameActivity()**********************
    void goToGameActivity()
    {
        initializeRandomArray();
        if (et.getText().length() > 0)
        {
            String name = et.getText().toString();
            Intent i = new Intent(
                    getApplicationContext(),
                    GameActivity.class);
            i.putExtra("name", name);
            Bundle bundle = new Bundle();
            bundle.putSerializable("randoms", stateCapitals);
            i.putExtras(bundle);
            startActivity(i);
        } else
        {
            Toast.makeText(getBaseContext(),
                    "Enter a name!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    //********************loadDB()********************************
    void loadDB()
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

            Scanner scanner = new Scanner(fis);
            String line;
            ContentValues cv = new ContentValues();
            while(scanner.hasNextLine())
            {
                line = scanner.nextLine();
                String sub = line.substring(0,5);
                if(!(sub.equals("-----") || sub.equals("State")))
                {
                    String[] split = line.split("\\s\\s+");
                    //insert into db
                    cv.put("state", split[0]);
                    cv.put("capital", split[1]);
                    if (db.insertOrThrow("states", null, cv) < 0)
                    {
                        Log.i(TAG, "error on insert");
                    }
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "File not found",
                    Toast.LENGTH_SHORT).show();
        }
    }
    //********************checkTableExists()**********************
    boolean checkTableExists()
    {
        Cursor cursor = db.rawQuery("select count (*) from " +
                        "sqlite_master where type = ? and name " +
                "= ?", new String[] {"table", "states"});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }

    //******************printDatabase()***************************
    void printDatabase()
    {
        Cursor c = db.rawQuery("select * from states;",null);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            String result ="|";
            for(int i = 0; i < c.getColumnCount(); i++)
            {
                result += c.getString(i) + "|";
            }
            Log.i(TAG,"ROW " + c.getPosition()+": " + result);
        }
        Log.i(TAG,"***CursorEnd***");
    }
    //*****************scoreHandler()*****************************
    public void scoreHandler(View v)
    {
        Intent i = new Intent(getApplicationContext(),
                ScoreActivity.class);
        startActivity(i);
    }

    //*****************playHandler()******************************
    public void playHandler(View v)
    {
        if(et.getVisibility()== View.INVISIBLE)
        {
            et.setVisibility(View.VISIBLE);
        } else
        {
            goToGameActivity();
        }
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
            loadDB();
        }
    }
}
