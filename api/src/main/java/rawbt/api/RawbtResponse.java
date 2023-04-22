package rawbt.api;

import com.google.gson.Gson;

import java.util.List;

import rawbt.sdk.PrinterInfo;

public class RawbtResponse {

    static public final String RESPONSE_SUCCESS = "success";
    static public final String RESPONSE_ERROR = "error";
    static public final String RESPONSE_CANCELED = "canceled";
    static public final String RESPONSE_PROGRESS = "progress";

    static public final String RESPONSE_PRINTERS = "printers";

    String jobId;
    String responseType; // see constant RESPONSE_xxx
    String errorMessage;
    Float progress;

    List<PrinterInfo> printers;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }

    public List<PrinterInfo> getPrinters() {
        return printers;
    }

    public void setPrinters(List<PrinterInfo> printers) {
        this.printers = printers;
    }

    public String GSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
