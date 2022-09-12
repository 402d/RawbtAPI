package rawbt.api;

import com.google.gson.Gson;

public class RawbtResponse {

    static public final String RESPONSE_SUCCESS = "success";
    static public final String RESPONSE_ERROR = "error";
    static public final String RESPONSE_CANCELED = "canceled";
    static public final String RESPONSE_PROGRESS = "progress";

    String jobId;
    String responseType; // see constant RESPONSE_xxx
    String errorMessage;
    Float progress;

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

    public String GSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
