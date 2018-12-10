package com.zgq.android.fibonaccinative;

/**
 * Created by zgq on 2018/3/16.
 */

public class Fiblib {


    public static long fibJR(long n){
        return n <= 0 ? 0 : n== 1 ? 1:(fibJR(n-1)+fibJR(n-2));
    }
    public static long fibJI(long n){
        long previous = -1;
        long result = 1;
        for(long i = 0; i<=n;i++){
            long sum = previous + result;
            previous = result;
            result = sum;
        }
        return  result;
    }
}
