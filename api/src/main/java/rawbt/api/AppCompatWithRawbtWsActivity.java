package rawbt.api;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rawbt.sdk.PrinterInfo;


abstract public class AppCompatWithRawbtWsActivity extends AppCompatActivity {

    final protected Handler handler = new Handler(Looper.getMainLooper());
    final ExecutorService executor = Executors.newSingleThreadExecutor();
    protected SelectPrinterAdapter adapterSelectPrinter;
    protected Spinner spinnerSelectPrinter;
    boolean isConnected = false;

    class RawbtWebSocketClient extends WebSocketClient{
        public RawbtWebSocketClient(URI serverUri) {
            super(serverUri);
        }

        @Override
        public void onOpen(ServerHandshake handshake) {
            isConnected = true;
            getPrinters();
            handleServiceConnected();
        }

        @Override
        public void onMessage(String message) {
            try{
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                RawbtResponse response = gson.fromJson(message, RawbtResponse.class);
                if(RawbtResponse.RESPONSE_SUCCESS.equals(response.getResponseType())){
                    handler.post(()->handlePrintSuccess(response.getJobId()));
                }else if(RawbtResponse.RESPONSE_CANCELED.equals(response.getResponseType())){
                    handler.post(()->handlePrintCancel(response.getJobId()));
                }else if(RawbtResponse.RESPONSE_ERROR.equals(response.getResponseType())){
                    handler.post(()->handlePrintError(response.getJobId(),response.getErrorMessage()));
                }else if(RawbtResponse.RESPONSE_PROGRESS.equals(response.getResponseType())){
                    handler.post(()->handlePrintProgress(response.getJobId(),response.getProgress()));
                }else if(RawbtResponse.RESPONSE_PRINTERS.equals(response.getResponseType())){

                    handler.post(() -> {
                        int curId = -1;
                        String needName = getSelectedPrinterName();
                        adapterSelectPrinter.clear();
                        PrinterInfo p1 = new PrinterInfo();
                        p1.name = "current";
                        p1.description = "Default";
                        adapterSelectPrinter.add(p1);
                        int i = 1;
                        for (PrinterInfo info : response.getPrinters()) {
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
            }catch (Exception e){
                handler.post(()->handlePrintError(null,e.getClass().getSimpleName()));
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            isConnected = false;
        }

        @Override
        public void onError(Exception ex) {
            isConnected = false;
            handler.post(()->handlePrintError(null,ex.getClass().getSimpleName()));
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myConnect();
    }

    private void myConnect(){
        try{
            client = new RawbtWebSocketClient(new URI("ws://localhost:40213/")) ;
            client.connect();
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    WebSocketClient client;

    protected void printJob(@NonNull RawbtPrintJob job){
        executor.execute(()-> {
            if(!isConnected){
                handler.post(()->handlePrintError(job.getIdJob(), "Try again later"));
                myConnect();
                return;
            }
            try {
                client.send(job.GSON());
            } catch (Exception e) {
                handler.post(()->handlePrintError(job.getIdJob(), e.getClass().getSimpleName()));
            }
        });
    }

    protected boolean getPrinters(){
        try {
            client.send("GET_PRINTERS");
        }catch (Exception e){
            return false;
        }
        return true;
    }

    abstract protected void handleServiceConnected();
    abstract protected void handlePrintSuccess(String jobId);
    abstract protected void handlePrintCancel(String jobId);
    abstract protected void handlePrintError(@Nullable String jobId, String message);
    abstract protected void handlePrintProgress(String jobId,Float p);

    abstract protected String getSelectedPrinterName();
}
