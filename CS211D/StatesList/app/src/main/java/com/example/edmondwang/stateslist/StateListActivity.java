/* Author: Edmond Wang
 * Quiz: 1
 * Date: 11/21/16
 * Display a list of states loaded from the US_states file.
 * When a particular state is selected, display its
 * corresponding capital.
 */
package com.example.edmondwang.stateslist;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class StateListActivity extends AppCompatActivity
{
    private static final String TAG = StateListActivity.class
            .getName();
    SQLiteDatabase db;
    ListView lv;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_list);

        db = openOrCreateDatabase("statesDB.db",
                SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);

        if(!checkTableExists())
        {
            Log.i(TAG,"table doesn't exist");
            String createTableCmd = "create table states( _id " +
                    "integer primary key autoincrement, " +
                    "state text not null, capital text); ";
            db.execSQL(createTableCmd);
            loadDB();
        }
        tv = (TextView)findViewById(R.id.textView);
        lv = (ListView)findViewById(R.id.listView);
        Cursor c = db.rawQuery("select * from states;",null);
        final SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
                android.R.layout
                        .simple_list_item_single_choice, c, new
                String[]{"state"}, new int[]{android.R.id
                .text1}, 0);
        lv.setAdapter(sca);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setOnItemClickListener(new  AdapterView
                .OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position,
                                    long id)
            {
                Cursor cursor =  sca.getCursor();
                cursor.moveToPosition(position);
                tv.setText(cursor.getString(2));
            }
        });
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
