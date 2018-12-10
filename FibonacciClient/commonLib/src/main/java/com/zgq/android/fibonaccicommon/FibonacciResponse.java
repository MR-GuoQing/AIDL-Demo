package com.zgq.android.fibonaccicommon;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zgq on 2018/3/16.
 */

public class FibonacciResponse implements Parcelable{

    private final long result;
    private final long timeInMullis;

    public FibonacciResponse(long result, long timeInMullis) {
        this.result = result;
        this.timeInMullis = timeInMullis;
    }

    public long getResult() {
        return result;
    }

    public long getTimeInMullis() {
        return timeInMullis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.result);
        parcel.writeLong(this.timeInMullis);
    }
    public static final Creator<FibonacciResponse> CREATOR = new Creator<FibonacciResponse>(){
        public FibonacciResponse createFromParcel(Parcel in){
            return  new FibonacciResponse(in.readLong(),in.readLong());
        }
        public FibonacciResponse[] newArray (int size){
            return  new FibonacciResponse[size];
        }
    };
}
