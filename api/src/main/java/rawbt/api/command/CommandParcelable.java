package rawbt.api.command;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import rawbt.api.Constant;
import rawbt.api.attributes.AttributesImage;
import rawbt.api.attributes.AttributesPdf;
import rawbt.api.attributes.AttributesString;

public class CommandParcelable implements RawbtCommand{
    final public static String TAG = "stream"; // for GSON
    String command = TAG;

    Constant.RAWBT_CONTENT_TYPE type;
    String parcelable;
    AttributesImage attributesImage = null;
    AttributesPdf attributesPdf = null;
    AttributesString attributesString = null;

    public CommandParcelable( @NonNull Parcelable parcelable, Constant.RAWBT_CONTENT_TYPE type) {
        this.type = type;
        this.parcelable = parcelable.toString();
    }

    // ============ get & set =============
    public Constant.RAWBT_CONTENT_TYPE getType() {
        return type;
    }

    public void setType(@NonNull Constant.RAWBT_CONTENT_TYPE type) {
        this.type = type;
    }

    @NonNull
    public String getParcelable() {
        return parcelable;
    }

    public void setParcelable(@NonNull String parcelable) {
        this.parcelable = parcelable;
    }

    @Nullable
    public AttributesImage getAttributesImage() {
        return attributesImage;
    }

    public void setAttributesImage(@NonNull AttributesImage attributesImage) {
        this.attributesImage = attributesImage;
    }

    @Nullable
    public AttributesPdf getAttributesPdf() {
        return attributesPdf;
    }

    public void setAttributesPdf(@NonNull  AttributesPdf attributesPdf) {
        this.attributesPdf = attributesPdf;
    }

    @Nullable
    public AttributesString getAttributesString() {
        return attributesString;
    }

    public void setAttributesString(@NonNull AttributesString attributesString) {
        this.attributesString = attributesString;
    }


}
