package rawbt.api.command;

import rawbt.api.attributes.AttributesQRcode;
import static rawbt.api.Constant.ALIGNMENT_LEFT;

public class CommandQRcode implements RawbtCommand{
    final public static String TAG = "qrcode"; // for GSON
    String command = TAG;

    String data;
    String alignment = ALIGNMENT_LEFT;
    int multiply = 3;

    public CommandQRcode(){

    }

    public CommandQRcode(String data, AttributesQRcode attr) {
        this.data = data;
        this.alignment = attr.getAlignment();
        this.multiply = attr.getMultiply();
    }

    public String getData() {
        return data;
    }

    public CommandQRcode setData(String data) {
        this.data = data;
        return this;
    }

    public String getAlignment() {
        return alignment;
    }

    public CommandQRcode setAlignment(String alignment) {
        this.alignment = alignment;
        return this;
    }

    public int getMultiply() {
        return multiply;
    }

    public CommandQRcode setMultiply(int multiply) {
        this.multiply = multiply;
        return this;
    }
}
