package rawbt.sdk;


interface ICallback {
    oneway void onPrintSuccess(in String jobId);
    oneway void onPrintError(in String jobId,in String errMessage);
    oneway void onPrintCancel(in String jobId);
    oneway void onProgress(in String jobId,in float p);
}