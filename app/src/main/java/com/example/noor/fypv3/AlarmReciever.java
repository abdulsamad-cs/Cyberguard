package com.example.noor.fypv3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"Received the broadcast from AlarmManager");
        //Toast.makeText(context, "Received the broadcast from AlarmManager", Toast.LENGTH_SHORT).show();

        SimpleJobIntentService.enqueueWork(context,intent);

    }
}
