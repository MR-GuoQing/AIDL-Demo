package com.zgq.android.fibonaccicommon;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zgq on 2018/3/16.
 */

public class FibonacciRequest implements Parcelable{
    public  static enum Type{
        RECURSIVE_JAVA,ITERATIVE_JAVA,RECURSIVE_NATIVE,ITERATIVE_NATIVE
    }
    private final long n;
    private final Type type;

    public FibonacciRequest(long n, Type type){
        this.n = n;
        if(type == null){
            throw new NullPointerException("Type must not be null");
        }
        this.type = type;
    }

    public long getN() {
        return n;
    }

    public Type getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.n);
        parcel.writeInt(this.type.ordinal());
    }
    public static final Creator<FibonacciRequest> CREATOR = new Creator<FibonacciRequest>(){
        public FibonacciRequest createFromParcel(Parcel in){
            long n = in.readLong();
            Type type = Type.values()[in.readInt()];
            return new FibonacciRequest(n,type);
        }
        public FibonacciRequest[] newArray(int size){
            return  new FibonacciRequest[size];
        }

};


}
