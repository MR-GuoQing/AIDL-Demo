package com.zgq.android.fibonacciclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zgq.android.fibonaccicommon.FibonacciRequest;
import com.zgq.android.fibonaccicommon.FibonacciResponse;
import com.zgq.android.fibonaccicommon.IFibonacciService;
import com.zgq.android.fibonacciservice.FibonacciService;



public class FibonacciActivity extends Activity implements View.OnClickListener,ServiceConnection{

    private static final String TAG = "FibonacciActivity";
    private EditText input;
    private Button button;
    private RadioGroup type;
    private TextView outPut;
    private IFibonacciService service;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.input = super.findViewById(R.id.input);
        this.button = super.findViewById(R.id.button);
        this.outPut = super.findViewById( R.id.output);
        this.type = super.findViewById(R.id.type);
        this.button.setOnClickListener(this);
        this.button.setEnabled(false);


    }

    @Override
    protected void onResume() {
        Log.d(TAG,"onResume");
        super.onResume();
        Intent intent = new Intent(this,FibonacciService.class);
        intent.setAction(IFibonacciService.class.getName());
        if(!super.bindService(intent,this,BIND_AUTO_CREATE)){
            Log.w(TAG,"fail to bind service");
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG,"onPause()'ed");
        super.onPause();
        super.unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        boolean isLocal = false;
        Log.d(TAG,"onServiceConnected()'ed"+componentName);
        IInterface ll = iBinder.queryLocalInterface("com.zgq.android.fibonaccicommon.IFibonacciService");
        if(ll!=null && ll instanceof com.zgq.android.fibonaccicommon.IFibonacciService)
            isLocal = true;
        Log.d(TAG,String.valueOf(isLocal));
        this.service = IFibonacciService.Stub.asInterface(iBinder);
        this.button.setEnabled(true);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d(TAG,"onServiceDisconnected()'ed"+componentName);
        this.service = null;
        this.button.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        final long n;
        String s = this.input.getText().toString();
        if(TextUtils.isEmpty(s)){
            return;
        }
        try {
            n = Long.parseLong(s);
        }catch (NumberFormatException e){
            this.input.setError(super.getText(R.string.input_error));
            return;
        }
        final FibonacciRequest.Type type;
        switch (FibonacciActivity.this.type.getCheckedRadioButtonId()){
            case R.id.type_fib_jr:
                type = FibonacciRequest.Type.RECURSIVE_JAVA;
                break;
            case R.id.type_fib_ji:
                type = FibonacciRequest.Type.ITERATIVE_JAVA;
                break;
                default:
                    return;
        }
        final FibonacciRequest request = new FibonacciRequest(n,type);
        final ProgressDialog dialog = ProgressDialog.show(this,"",super.getText(R.string.progress_text),true);
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {
                try{
                    long totalTime = SystemClock.uptimeMillis();
                    FibonacciResponse response = FibonacciActivity.this.service.fib(request);
                    totalTime = SystemClock.uptimeMillis()-totalTime;
                    return String.format("fibonacci(%d)=%d\nin %d ms\n(+%d ms)",n,response.getResult(),response.getTimeInMullis(),totalTime-response.getTimeInMullis());
                }catch (RemoteException e){
                    Log.wtf(TAG,"fail to communicate with the service",e);
                    return  null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                dialog.dismiss();
                if(s == null){
                    Toast.makeText(FibonacciActivity.this,R.string.fib_error,Toast.LENGTH_SHORT).show();
                }else{
                    FibonacciActivity.this.outPut.setText(s);
                }
            }
        }.execute();

    }
}
