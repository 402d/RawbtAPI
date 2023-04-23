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
import android.os.RemoteException;
import android.widget.Spinner;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rawbt.sdk.ICallback;
import rawbt.sdk.IGetPrintersCallback;
import rawbt.sdk.IRawBtPrintService;
import rawbt.sdk.PrinterInfo;

abstract public class AppCompatWithRawbtActivity extends AppCompatActivity {

    final protected Handler handler = new Handler(Looper.getMainLooper());
    final ExecutorService executor = Executors.newSingleThreadExecutor();
    final ICallback serviceCallback = new ICallback.Stub() {

        @Override
        public void onPrintSuccess(String jobId) {
            final String finalJobId = jobId;
            handler.post(() -> handlePrintSuccess(finalJobId));
        }

        @Override
        public void onPrintError(String jobId, String errMessage) {
            final String finalJobId = jobId;
            final String finalMes = errMessage;
            handler.post(() -> handlePrintError(finalJobId, finalMes));
        }

        @Override
        public void onPrintCancel(String jobId) {
            final String finalJobId = jobId;
            handler.post(() -> handlePrintCancel(finalJobId));
        }

        @Override
        public void onProgress(String jobId, float p) {
            final String finalJobId = jobId;
            final float finalP = p;
            handler.post(() -> handlePrintProgress(finalJobId, finalP));
        }


    };

    // -----------------------------------------
    protected SelectPrinterAdapter adapterSelectPrinter;
    protected Spinner spinnerSelectPrinter;
    private final IGetPrintersCallback getPrintersCallback = new IGetPrintersCallback.Stub() {

        @Override
        public void onResult(PrinterInfo[] printers) throws RemoteException {
            handler.post(() -> {
                int curId = -1;
                String needName = getSelectedPrinterName();
                adapterSelectPrinter.clear();
                PrinterInfo p1 = new PrinterInfo();
                p1.name = "current";
                p1.description = "Default";
                adapterSelectPrinter.add(p1);
                int i = 1;
                for (PrinterInfo info : printers) {
                    adapterSelectPrinter.add(info);
                    if(needName.equals(info.name)){
                        curId = i;
                    }
                    i++;
                }
                adapterSelectPrinter.notifyDataSetChanged();
                if(curId>-1){
                    spinnerSelectPrinter.setSelection(curId);
                }
            });
        }
    };    private final ServiceConnection connectService = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder s) {
            serviceRawBT = IRawBtPrintService.Stub.asInterface(s);
            try {
                serviceRawBT.registerCallback(serviceCallback);
                handler.post(() -> handleServiceConnected());
                try {
                    boolean flag = serviceRawBT.getPrinters(getPrintersCallback);
                    if (!flag) {
                        PrinterInfo p1 = new PrinterInfo();
                        p1.name = "current";
                        p1.description = "Default";
                        adapterSelectPrinter.add(p1);
                        adapterSelectPrinter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                handlePrintError(null, e.getMessage());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceRawBT = null;
            handler.postDelayed(() -> bindRawBT(false), 5000);
        }

    };

    // -----------------------------------------
    public volatile IRawBtPrintService serviceRawBT = null;

    protected void bindRawBT(boolean allowRequestPerm) {
        try {
            bindService(RawbtApiHelper.createExplicitIntent(), connectService, Context.BIND_AUTO_CREATE);
        } catch (SecurityException s) {
            if (allowRequestPerm) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(RawbtApiHelper.SERVICE_PERMISSION)
                            != PackageManager.PERMISSION_GRANTED) {

                        mRequestPermission.launch(RawbtApiHelper.SERVICE_PERMISSION);

                    }

                }
            } else {
                handlePrintError(null, getString(R.string.rawbt_permission_not_granted));
            }
        } catch (Exception e) {
            handlePrintError(null, getString(R.string.rawbt_connect_error));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (serviceRawBT == null) {
            bindRawBT(true);
        }
    }    // --------------------------------------------------------
    private final ActivityResultLauncher<String> mRequestPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> bindRawBT(false));
    // --------------------------------------------------------

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceRawBT != null) {
            unbindService(connectService);
        }
    }

    protected void printJob(@NonNull RawbtPrintJob job) {
        if (serviceRawBT == null) {
            if (!RawbtApiHelper.isServiceInstalled(this)) {
                handlePrintError(job.idJob, getString(R.string.rawb_not_installed));
                return;
            }
            bindRawBT(false);
            handlePrintError(job.idJob, getString(R.string.rawbt_please_wait));
            return;
        }
        executor.execute(() -> {
            try {
                String gson = job.GSON();
                serviceRawBT.printRawbtPrintJob(gson);
            } catch (SecurityException s) {
                handler.post(() -> handlePrintError(job.idJob, getString(R.string.rawbt_permission_not_granted)));
            } catch (Exception e) {
                handler.post(() -> handlePrintError(job.idJob, e.getLocalizedMessage()));
            }
        });

    }

    abstract protected void handleServiceConnected();

    abstract protected void handlePrintSuccess(String jobId);

    abstract protected void handlePrintCancel(String jobId);

    abstract protected void handlePrintError(@Nullable String jobId, String message);

    abstract protected void handlePrintProgress(String jobId, Float p);

     abstract protected String getSelectedPrinterName();




}
