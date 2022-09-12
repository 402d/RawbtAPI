package rawbt.api;

public class RawbtResponse {

    static final String RESPONSE_SUCCESS = "success";
    static final String RESPONSE_ERROR = "error";
    static final String RESPONSE_CANCELED = "canceled";
    static final String RESPONSE_PROGRESS = "progress";

    String jobId;
    String responseType; // see constant RESPONSE_xxx
    String errorMessage;
    Float progress;
}
