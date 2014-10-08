package com.example.test.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class HeadlessSmsSenderService extends Service {
    public HeadlessSmsSenderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
