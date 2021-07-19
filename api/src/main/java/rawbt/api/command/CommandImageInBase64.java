package rawbt.api.command;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import rawbt.api.attributes.AttributesImage;

public class CommandImageInBase64 implements RawbtCommand {
    final public static String TAG = "image"; // for GSON
    String command = TAG;

    String base64 = null;
    AttributesImage attributesImage = null;

    public CommandImageInBase64() {
    }

    public CommandImageInBase64(String base64, AttributesImage attributesImage) {
        this.base64 = base64;
        this.attributesImage = attributesImage;
    }

    @Nullable
    public String getBase64() {
        return base64;
    }

    public void setBase64(@NonNull String base64) {
        this.base64 = base64;
    }

    @Nullable
    public AttributesImage getAttributesImage() {
        return attributesImage;
    }

    public void setAttributesImage(@NonNull AttributesImage attributesImage) {
        this.attributesImage = attributesImage;
    }
}
