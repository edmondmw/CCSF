package com.example.edmondwang.messageapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by Edmond Wang on 12/8/2016.
 */
public class SMSMonitor extends BroadcastReceiver
{
    public static final String ACTION = "android.provider" +
            ".Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context con, Intent in)
    {
        if(in != null && in.getAction() != null && ACTION
                .compareToIgnoreCase(in.getAction()) == 0)
        {
            Object[] pduArray = (Object[])in.getExtras().get
                    ("pdus");
            SmsMessage[] mes = new SmsMessage[pduArray.length];
            for(int i = 0; i < pduArray.length; i++)
            {
                mes[i] = SmsMessage.createFromPdu((byte[])
                        pduArray[i]);
                Log.d("SMSMonitor",mes[i].getOriginatingAddress
                        ());
                String message = mes[i].getOriginatingAddress()
                        +":\n" + mes[i].getMessageBody();
                MessagingActivity inst= MessagingActivity
                .instance();
                inst.updateMessages(message);
            }
            Log.d("SMSMonitor", "SMS received");
        }
    }
}
