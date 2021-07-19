package rawbt.api.command;

import rawbt.api.attributes.AttributesBarcode;

import static rawbt.api.Constant.ALIGNMENT_LEFT;
import static rawbt.api.Constant.FONT_A;
import static rawbt.api.Constant.HRI_NONE;

public class CommandBarcode implements RawbtCommand{
    final public static String TAG = "barcode"; // for GSON
    String command = TAG;

    String data;
    String type;

    String hri = HRI_NONE;
    int font =  FONT_A;
    int height = 162;
    int width = 3;

    String alignment = ALIGNMENT_LEFT;

    public CommandBarcode(String type, String data) {
        this.type = type;
        this.data = data;
    }

    public CommandBarcode(String data, AttributesBarcode attr) {
        this.data = data;
        this.type = attr.getType();

        this.hri = attr.getHri();
        this.font = attr.getFont();
        this.height = attr.getHeight();
        this.width = attr.getWidth();

        this.alignment = attr.getAlignment();

    }

    public String getData() {
        return data;
    }

    public CommandBarcode setData(String data) {
        this.data = data;
        return this;
    }

    public String getType() {
        return type;
    }

    public CommandBarcode setType(String type) {
        this.type = type;
        return this;
    }

    public String getHri() {
        return hri;
    }

    public CommandBarcode setHri(String hri) {
        this.hri = hri;
        return this;
    }

    public int getFont() {
        return font;
    }

    public CommandBarcode setFont(int font) {
        this.font = font;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public CommandBarcode setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public CommandBarcode setWidth(int width) {
        this.width = width;
        return this;
    }

    public String getAlignment() {
        return alignment;
    }

    public CommandBarcode setAlignment(String alignment) {
        this.alignment = alignment;
        return this;
    }
}
