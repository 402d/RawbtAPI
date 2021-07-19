package rawbt.sdk;


interface IConsole {
    oneway void onPrinterConnect();
    oneway void onPrinterDisconnect();
    oneway void onError(in String message);
    oneway void onNotify(in String message);
    oneway void onDeviceReceived(in byte[] response);
    oneway void onDeviceSent(in byte[] request);
}