package rawbt.sdk;

import rawbt.sdk.PrinterInfo;

interface IGetPrintersCallback{
  oneway void onResult(in PrinterInfo[] printers);
}