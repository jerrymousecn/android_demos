package com.example.contacttest;

import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

public class SMSObserver extends ContentObserver {

    public SMSObserver(Handler handler) {
        super(handler);
    }
    
    @Override
    public void onChange(boolean selfChange) {
        Log.i("sms", "sms");
    }

}