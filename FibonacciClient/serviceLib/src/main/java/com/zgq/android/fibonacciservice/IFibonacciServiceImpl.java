package com.zgq.android.fibonacciservice;
//import com.zgq.android.f

import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.zgq.android.fibonaccicommon.FibonacciRequest;
import com.zgq.android.fibonaccicommon.FibonacciResponse;
import com.zgq.android.fibonaccicommon.IFibonacciService;
import com.zgq.android.fibonaccinative.Fiblib;

/**
 * Created by zgq on 2018/3/18.
 */

public class IFibonacciServiceImpl extends IFibonacciService.Stub{

    public static final String TAG = "IFibonacciServiceImpl";
    @Override
    public long fibJR(long n) throws RemoteException {
        return Fiblib.fibJR(n);
    }

    @Override
    public long fibJI(long n) throws RemoteException {
        return Fiblib.fibJI(n);
    }

    @Override
    public long fibNR(long n) throws RemoteException {
        return Fiblib.fibJI(n);
    }

    @Override
    public long fibNI(long n) throws RemoteException {
        return Fiblib.fibJI(n);
    }

    @Override
    public FibonacciResponse fib(FibonacciRequest request) throws RemoteException {
        Log.d(TAG,String.format("fib(%d,%s)",request.getN(),request.getType()));
        long timeInMillis = SystemClock.uptimeMillis();
        long result;
        switch (request.getType()){
            case ITERATIVE_JAVA:
                result = this.fibJI(request.getN());
                break;
            case RECURSIVE_JAVA:
                result = this.fibJR(request.getN());
                break;
            case ITERATIVE_NATIVE:
                result = this.fibNI(request.getN());
                break;
            case RECURSIVE_NATIVE:
                result = this.fibNR(request.getN());
                break;
                default:
                    return null;
        }
        timeInMillis = SystemClock.uptimeMillis()-timeInMillis;

        return new FibonacciResponse(result,timeInMillis);
    }
}
