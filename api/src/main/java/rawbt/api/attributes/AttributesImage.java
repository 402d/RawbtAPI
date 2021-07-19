package rawbt.api.attributes;


import android.os.Parcel;
import android.os.Parcelable;

import static rawbt.api.Constant.ALIGNMENT_LEFT;
import static rawbt.api.Constant.DITHERING_BW;

public class AttributesImage implements Parcelable {
    int graphicFilter = DITHERING_BW;
    boolean rotateImage = false;
    boolean inverseColor = false;
    private int scale = 16;
    private boolean doScale = true;
    private String alignment = ALIGNMENT_LEFT;



    public AttributesImage() {
    }

    public AttributesImage(int graphicFilter) {
        this.graphicFilter = graphicFilter;
    }



    // =========== get & set ========================
    public int getGraphicFilter() {
        return graphicFilter;
    }
    public boolean isRotateImage() {
        return rotateImage;
    }
    public boolean isInverseColor() {
        return inverseColor;
    }
    public int getScale() {
        return scale;
    }
    public String getAlignment() {
        return alignment;
    }

    public boolean isDoScale() {
        return doScale;
    }

    public AttributesImage setGraphicFilter(int graphicFilter) {
        this.graphicFilter = graphicFilter;
        return this;
    }

    public AttributesImage setRotateImage(boolean rotateImage) {
        this.rotateImage = rotateImage;
        return this;
    }

    public AttributesImage setInverseColor(boolean inverseColor) {
        this.inverseColor = inverseColor;
        return this;
    }

    public AttributesImage setScale(int scale) {
        this.scale = scale;
        return this;
    }

    public AttributesImage setAlignment(String alignment) {
        this.alignment = alignment;
        return this;
    }

    public AttributesImage setDoScale(boolean doScale) {
        this.doScale = doScale;
        return this;
    }

    // implements parcelable

    protected AttributesImage(Parcel in) {
        graphicFilter = in.readInt();
        rotateImage = in.readByte() != 0;
        inverseColor = in.readByte() != 0;
        scale = in.readInt();
        alignment = in.readString();
        doScale  = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(graphicFilter);
        dest.writeByte((byte) (rotateImage ? 1 : 0));
        dest.writeByte((byte) (inverseColor ? 1 : 0));
        dest.writeInt(scale);
        dest.writeString(alignment);
        dest.writeByte((byte) (doScale ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AttributesImage> CREATOR = new Creator<AttributesImage>() {
        @Override
        public AttributesImage createFromParcel(Parcel in) {
            return new AttributesImage(in);
        }

        @Override
        public AttributesImage[] newArray(int size) {
            return new AttributesImage[size];
        }
    };

}
