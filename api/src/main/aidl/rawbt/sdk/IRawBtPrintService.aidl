package rawbt.sdk;

import rawbt.sdk.ICallback;
import rawbt.sdk.IConsole;
import rawbt.sdk.IGetPrintersCallback;

interface IRawBtPrintService {
    void printRawbtPrintJob(in String jobJSON);
    void registerCallback(in ICallback callback);
    void unregisterCallback(in ICallback callback);
    void registerConsole(in IConsole console);
    void unregisterConsole(in IConsole console);
    void printerConnect();
    void printerConnectByName(in String name);
    void printerDisconnect();
    void printerSend(in byte[] rawData);
    void sendFile(in Uri uri,in boolean isUtfText, in String needCP);
    // new
    boolean getPrinters(in IGetPrintersCallback callback);
}
