package com.example.movielibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (int i = 0; i < messages.length; i++) {
            SmsMessage currentMessage = messages[i];
            String senderNum = currentMessage.getDisplayOriginatingAddress();
            String message = currentMessage.getDisplayMessageBody();

            /*
            Intent myIntent = new Intent();
            myIntent.setAction("MySMS");
            myIntent.putExtra("KEY1", message.substring(1,5));
            myIntent.putExtra("KEY2", message);
            context.sendBroadcast(myIntent);
            */

            Intent myIntent = new Intent();

            int startIndex = 0;
            int keyNumber = 1;
            for (int j = 0; j < message.length() & keyNumber <= 6; j++) {
                if (message.charAt(j) == ';') {
                    myIntent.setAction("MySMS");
                    myIntent.putExtra("KEY"+keyNumber, message.substring(startIndex, j));
                    keyNumber += 1;
                    startIndex = j+1;
                }

            }
            if (startIndex < message.length()) {
                myIntent.putExtra("KEY"+keyNumber, message.substring(startIndex));
            }

            context.sendBroadcast(myIntent);
        }

    }
}
