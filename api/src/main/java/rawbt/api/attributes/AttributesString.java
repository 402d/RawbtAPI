package rawbt.api.attributes;

import android.os.Parcel;
import android.os.Parcelable;

import static rawbt.api.Constant.*;

public class AttributesString implements Parcelable {

    // type: string

    private int fontsCpi = CPI_DEFAULT;
    private int internationalChars = 0;

    // if text printed as image
    private int truetypeFontSize = 21;

    // свойства для строки
    private int printerFont = FONT_DEFAULT ;
    private String alignment = ALIGNMENT_LEFT;
    private boolean bold = false;
    private boolean doubleHeight = false;
    private boolean doubleWidth = false;
    private boolean underline = false;

    private String lang = "default";

    // ============= constructor ====================

    public AttributesString() {
    }


    // ===================

    public boolean isFontA(){
        return getPrinterFont()==FONT_A;
    }

    public boolean isFontB(){
        return getPrinterFont()==FONT_B;
    }

    public boolean isFontC(){
        return getPrinterFont()==FONT_C;
    }

    // ============ get & set ==========


    public int getPrinterFont() {
        return printerFont;
    }
    public int getFontsCpi() {
        return fontsCpi;
    }
    public int getInternationalChars() {
        return internationalChars;
    }
    public int getTruetypeFontSize() {
        return truetypeFontSize;
    }
    public String getAlignment() {
        return alignment;
    }
    public boolean isBold() {
        return bold;
    }
    public boolean isDoubleHeight() {
        return doubleHeight;
    }
    public boolean isDoubleWidth() {
        return doubleWidth;
    }
    public boolean isUnderline() {
        return underline;
    }
    public String getLang() {
        return lang;
    }

    public AttributesString setPrinterFont(int printerFont) {
        this.printerFont = printerFont;
        return this;
    }

    public AttributesString setFontsCpi(int fontsCpi) {
        this.fontsCpi = fontsCpi;
        return this;
    }

    public AttributesString setInternationalChars(int internationalChars) {
        this.internationalChars = internationalChars;
        return this;
    }

    public AttributesString setTruetypeFontSize(int truetypeFontSize) {
        this.truetypeFontSize = truetypeFontSize;
        return this;
    }

    public AttributesString setAlignment(String alignment) {
        this.alignment = alignment;
        return this;
    }

    public AttributesString setBold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public AttributesString setDoubleHeight(boolean doubleHigh) {
        this.doubleHeight = doubleHigh;
        return this;
    }

    public AttributesString setDoubleWidth(boolean doubleWidth) {
        this.doubleWidth = doubleWidth;
        return this;
    }

    public AttributesString setUnderline(boolean underline) {
        this.underline = underline;
        return this;
    }

    public AttributesString setLang(String lang) {
        this.lang = lang;
        return this;
    }

    public AttributesString build(){
        return new AttributesString()
                .setFontsCpi(fontsCpi)
                .setInternationalChars(internationalChars)
                .setTruetypeFontSize(truetypeFontSize)
                .setPrinterFont(printerFont)
                .setAlignment(alignment)
                .setBold(bold)
                .setDoubleWidth(doubleWidth)
                .setDoubleHeight(doubleHeight)
                .setUnderline(underline)
                .setLang(lang);

    }

// implements parcelable

    protected AttributesString(Parcel in) {
        printerFont = in.readInt();
        fontsCpi = in.readInt();
        internationalChars = in.readInt();
        truetypeFontSize = in.readInt();
        alignment = in.readString();
        bold = in.readByte() != 0;
        doubleHeight = in.readByte() != 0;
        doubleWidth = in.readByte() != 0;
        underline = in.readByte() != 0;
        lang = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(printerFont);
        dest.writeInt(fontsCpi);
        dest.writeInt(internationalChars);
        dest.writeInt(truetypeFontSize);
        dest.writeString(alignment);
        dest.writeByte((byte) (bold ? 1 : 0));
        dest.writeByte((byte) (doubleHeight ? 1 : 0));
        dest.writeByte((byte) (doubleWidth ? 1 : 0));
        dest.writeByte((byte) (underline ? 1 : 0));
        dest.writeString(lang);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AttributesString> CREATOR = new Creator<AttributesString>() {
        @Override
        public AttributesString createFromParcel(Parcel in) {
            return new AttributesString(in);
        }

        @Override
        public AttributesString[] newArray(int size) {
            return new AttributesString[size];
        }
    };

}
