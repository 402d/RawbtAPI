package rawbt.api.attributes;

import android.os.Parcel;
import android.os.Parcelable;

import static rawbt.api.Constant.ALIGNMENT_LEFT;
import static rawbt.api.Constant.FONT_A;
import static rawbt.api.Constant.HRI_NONE;

public class AttributesBarcode implements Parcelable {
    String type;

    String hri = HRI_NONE;
    int font =  FONT_A;
    int height = 162;
    int width = 3;

    String alignment = ALIGNMENT_LEFT;

    public AttributesBarcode() {
    }

    // =========== get & set ========================


    public String getType() {
        return type;
    }

    public String getHri() {
        return hri;
    }

    public int getFont() {
        return font;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getAlignment() {
        return alignment;
    }

    public AttributesBarcode setType(String type) {
        this.type = type;
        return this;
    }

    public AttributesBarcode setHri(String hri) {
        this.hri = hri;
        return this;
    }

    public AttributesBarcode setFont(int font) {
        this.font = font;
        return this;
    }

    public AttributesBarcode setHeight(int height) {
        this.height = height;
        return this;
    }

    public AttributesBarcode setWidth(int width) {
        this.width = width;
        return this;
    }

    public AttributesBarcode setAlignment(String alignment) {
        this.alignment = alignment;
        return this;
    }


    public AttributesBarcode build(){
        return new AttributesBarcode()
                .setType(this.type)
                .setHri(this.hri)
                .setFont(this.font)
                .setHeight(this.height)
                .setWidth(this.width)
                .setAlignment(this.alignment);
    }

    // implements parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(hri);
        dest.writeInt(font);
        dest.writeInt(height);
        dest.writeInt(width);
        dest.writeString(alignment);
    }

    protected AttributesBarcode(Parcel in) {
        type = in.readString();
        hri = in.readString();
        font = in.readInt();
        height = in.readInt();
        width = in.readInt();
        alignment = in.readString();
    }

    public static final Creator<AttributesBarcode> CREATOR = new Creator<AttributesBarcode>() {
        @Override
        public AttributesBarcode createFromParcel(Parcel in) {
            return new AttributesBarcode(in);
        }

        @Override
        public AttributesBarcode[] newArray(int size) {
            return new AttributesBarcode[size];
        }
    };

}
