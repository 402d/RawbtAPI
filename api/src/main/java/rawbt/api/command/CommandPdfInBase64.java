package rawbt.api.command;

import rawbt.api.attributes.AttributesImage;
import rawbt.api.attributes.AttributesPdf;

import static rawbt.api.Constant.DITHERING_SF;

public class CommandPdfInBase64 implements RawbtCommand {
    final public static String TAG = "pdf64"; // for GSON
    String command = TAG;

    String base64;
    AttributesPdf attributesPdf = null;
    AttributesImage attributesImage = null;

    public CommandPdfInBase64() {
    }

    public CommandPdfInBase64(String base64, AttributesPdf attributesPdf) {
        this.base64 = base64;
        this.attributesPdf = attributesPdf;
        this.attributesImage = new AttributesImage(DITHERING_SF);
    }

    public CommandPdfInBase64(String base64, AttributesPdf attributesPdf,AttributesImage attributesImage) {
        this.base64 = base64;
        this.attributesPdf = attributesPdf;
        this.attributesImage = attributesImage;
    }

    // ================== get & set ==============

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public AttributesPdf getAttributesPdf() {
        return attributesPdf;
    }

    public void setAttributesPdf(AttributesPdf attributesPdf) {
        this.attributesPdf = attributesPdf;
    }

    public AttributesImage getAttributesImage() {
        return attributesImage;
    }

    public void setAttributesImage(AttributesImage attributesImage) {
        this.attributesImage = attributesImage;
    }
}
