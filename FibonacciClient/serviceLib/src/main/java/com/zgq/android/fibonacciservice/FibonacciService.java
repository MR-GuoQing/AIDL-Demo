package com.zgq.android.fibonacciservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.zgq.android.fibonaccicommon.IFibonacciService;

/**
 * Created by zgq on 2018/3/18.
 */

public class FibonacciService extends Service {
    private static final String TAG = "FibonacciService";
    private IFibonacciServiceImpl service;

    @Override
    public void onCreate() {
        super.onCreate();
        this.service = new IFibonacciServiceImpl();
        Log.d(TAG,"onCreate");

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return this.service;

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        this.service = null;
        super.onDestroy();
    }
}
