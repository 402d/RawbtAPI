package rawbt.api.attributes;

import android.os.Parcel;
import android.os.Parcelable;


import static rawbt.api.Constant.ALIGNMENT_CENTER;

public class AttributesQRcode implements Parcelable{

    String alignment = ALIGNMENT_CENTER;
    int multiply = 3;

    public AttributesQRcode() {
    }

    // =========== get & set ========================

    public String getAlignment() {
        return alignment;
    }

    public int getMultiply() {
        return multiply;
    }




    public  AttributesQRcode setAlignment(String alignment) {
        this.alignment = alignment;
        return this;
    }


    public AttributesQRcode setMultiply(int multiply) {
        this.multiply = multiply;
        return this;
    }



    // builder
    public  AttributesQRcode build(){
        return new AttributesQRcode()
                .setAlignment(this.alignment)
                .setMultiply(this.multiply);
    }

    // implements parcelable

    @Override
    public int describeContents() {
        return 0;
    }

    protected AttributesQRcode(Parcel in) {
        alignment = in.readString();
        multiply = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(alignment);
        dest.writeInt(multiply);
    }

    public static final Creator<AttributesQRcode> CREATOR = new Creator<AttributesQRcode>() {
        @Override
        public AttributesQRcode createFromParcel(Parcel in) {
            return new AttributesQRcode(in);
        }

        @Override
        public AttributesQRcode[] newArray(int size) {
            return new AttributesQRcode[size];
        }
    };

}
