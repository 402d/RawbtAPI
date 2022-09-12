package rawbt.api;


import static rawbt.api.RawbtPrintJob.EXTRA_JOB;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;


abstract public class AppCompatWithRawbtWsActivity extends AppCompatActivity {

    final protected Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            client = new WebSocketClient(new URI("ws://localhost:40213/")) {
                @Override
                public void onOpen(ServerHandshake handshake) {
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
                        }
                    }catch (Exception e){
                        handler.post(()->handlePrintError(null,e.getClass().getSimpleName()));
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    //
                }

                @Override
                public void onError(Exception ex) {
                    handler.post(()->handlePrintError(null,ex.getClass().getSimpleName()));
                }
            };
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
        try {
            client.send(job.GSON());
        }catch (Exception e){
            handlePrintError(job.getIdJob(),e.getClass().getSimpleName());
        }
    }

    abstract protected void handleServiceConnected();
    abstract protected void handlePrintSuccess(String jobId);
    abstract protected void handlePrintCancel(String jobId);
    abstract protected void handlePrintError(@Nullable String jobId, String message);
    abstract protected void handlePrintProgress(String jobId,Float p);
}
