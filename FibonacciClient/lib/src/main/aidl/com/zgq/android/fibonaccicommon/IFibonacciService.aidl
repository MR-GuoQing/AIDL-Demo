// IFibonacciService.aidl
package com.zgq.android.fibonaccicommon;
import com.zgq.android.fibonaccicommon.FibonacciRequest;
import com.zgq.android.fibonaccicommon.FibonacciResponse;
// Declare any non-default types here with import statements

interface IFibonacciService {
  long fibJR(in long n);
  long fibJI(in long n);
  long fibNR(in long n);
  long fibNI(in long n);
  FibonacciResponse fib(in FibonacciRequest request);
}
