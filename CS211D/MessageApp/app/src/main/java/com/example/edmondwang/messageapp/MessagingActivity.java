/* Author: Edmond Wang
 * Quiz: 2
 * Date: 12/12/16
 * Send SMS messages. When you receive messages, they will be
 * displayed on the screen as well.
 */
package com.example.edmondwang.messageapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MessagingActivity extends AppCompatActivity
{
    EditText addressET, messageET;
    Button sendButton;
    ListView messageListView;
    ArrayList<String> messageArray;
    ArrayAdapter<String> aa;
    int arrayIndex;
    private static MessagingActivity myActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        arrayIndex = 0;
        addressET = (EditText)findViewById(R.id.addressET);
        messageET = (EditText)findViewById(R.id.messageET);
        sendButton = (Button)findViewById(R.id.sendBtn);
        messageListView = (ListView)findViewById(R.id.listView);
        //If can't send sms, ask for permission to do so
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    0);
        }
        messageArray = new ArrayList<>();
        aa = new ArrayAdapter<>(this, android.R.layout
                .simple_list_item_1, messageArray);
        messageListView.setAdapter(aa);
    }

    //************************onStart()***************************
    @Override
    public void onStart()
    {
        super.onStart();
        myActivity = this;
    }

    //********************onRequestPermissionsResult()************
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults)
    {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED)
            {
                //can send texts
                sendButton.setEnabled(true);
            } else {
                //can't send texts
                sendButton.setEnabled(false);
            }
        }
    }

    //*****************sendButtonPressed()************************
    public void sendButtonPressed(View v)
    {
        //Only send the message if the address and message
        // aren't empty
        if (addressET.getText().length() > 0)
        {
            if(messageET.getText().length() > 0)
            {
                try
                {
                    String sendAddress = addressET.getText().
                            toString();
                    String message = messageET.getText().
                            toString();
                    sendSMS(sendAddress,message);
                } catch (Exception e)
                {
                    Log.e("MessagingActivity", e.toString());
                }

            } else
            {
                Toast.makeText(getBaseContext(),"Enter a " +
                        "message!",Toast.LENGTH_SHORT).show();
            }
        } else
        {
            Toast.makeText(getBaseContext(),"Enter a phone " +
                    "number!", Toast.LENGTH_SHORT).show();
        }
    }

    //**********************sendSMS()*****************************
    private void sendSMS(String address, String message) throws
    Exception
    {
        SmsManager sm = SmsManager.getDefault();
        sm.sendTextMessage(address, null,message,null,null);
        messageET.setText("");
        String msg = "You:\n" + message;
        updateMessages(msg);
    }

    //*******************instance()*******************************
    public static MessagingActivity instance() {
        return myActivity;
    }

    //********************updateMessages()************************
    public void updateMessages(String message)
    {
        aa.insert(message, arrayIndex);
        aa.notifyDataSetChanged();
        arrayIndex++;
    }
}
