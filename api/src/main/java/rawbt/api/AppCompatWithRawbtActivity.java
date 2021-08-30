package rawbt.api;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rawbt.sdk.ICallback;
import rawbt.sdk.IRawBtPrintService;

abstract public class AppCompatWithRawbtActivity extends AppCompatActivity {

    final protected ExecutorService executor = Executors.newSingleThreadExecutor();
    final protected Handler handler = new Handler(Looper.getMainLooper());
    // -----------------------------------------

    public volatile IRawBtPrintService serviceRawBT = null;
    private final ServiceConnection connectService = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder s) {
            serviceRawBT = IRawBtPrintService.Stub.asInterface(s);
            try {
                serviceRawBT.registerCallback(serviceCallback);
                handler.post(()-> handleServiceConnected());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceRawBT = null;
        }

    };

    // -----------------------------------------

    final ICallback serviceCallback = new ICallback.Stub() {

        @Override
        public void onPrintSuccess(String jobId)  {
            final String finalJobId = jobId;
            handler.post(()-> handlePrintSuccess(finalJobId));
        }

        @Override
        public void onPrintError(String jobId,String errMessage) {
            final String finalJobId = jobId;
            final String finalMes = errMessage;
            handler.post(()-> handlePrintError(finalJobId,finalMes));
        }

        @Override
        public void onPrintCancel(String jobId) {
            final String finalJobId = jobId;
            handler.post(()-> handlePrintCancel(finalJobId));
        }

        @Override
        public void onProgress(String jobId, float p)  {
            final String finalJobId = jobId;
            final float finalP = p;
            handler.post(()-> handlePrintProgress(finalJobId,finalP));
        }


    };
    // --------------------------------------------------------
    private final ActivityResultLauncher<String> mRequestPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {

            try {
                bindService(RawbtApiHelper.createExplicitIntent(), connectService, Context.BIND_AUTO_CREATE);
            }catch (Exception e){
                if(isGranted) {
                    handlePrintError(null,getString(R.string.rawbt_connect_error));
                }else{
                    handlePrintError(null,getString(R.string.rawbt_permission_not_granted));
                }
            }

    });
    // --------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(serviceRawBT==null) {

            try {
                bindService(RawbtApiHelper.createExplicitIntent(), connectService, Context.BIND_AUTO_CREATE);
            }catch (SecurityException s){

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(RawbtApiHelper.SERVICE_PERMISSION)
                            != PackageManager.PERMISSION_GRANTED) {

                        mRequestPermission.launch(RawbtApiHelper.SERVICE_PERMISSION);

                    }

                }
            }catch (Exception e){
                handlePrintError(null,getString(R.string.rawbt_connect_error));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serviceRawBT != null){
            unbindService(connectService);
        }
    }

    abstract protected void handleServiceConnected();
    abstract protected void handlePrintSuccess(String jobId);
    abstract protected void handlePrintCancel(String jobId);
    abstract protected void handlePrintError(@Nullable String jobId, String message);
    abstract protected void handlePrintProgress(String jobId,Float p);
}
