package rawbt.api.attributes;


import android.os.Parcel;
import android.os.Parcelable;

public class AttributesPdf implements Parcelable {
    public static final int WIDTH_TRUNCATE_NONE = 0;
    public static final int WIDTH_TRUNCATE_1_2 = 1;
    public static final int WIDTH_TRUNCATE_MAX = 2;

    private int page = -1 ; // -1 - all pages or need number

    private boolean roll = true;
    private int widthTruncateMode = WIDTH_TRUNCATE_MAX;


    private int rotate = 0; // 0,90,270
    private boolean clearHeaderAndFooter = false;
    private boolean cutTop = false;
    private int cutTopVal = 0;

    private boolean cutFrame = false;
    private int cutFrameVal = 0;

    private boolean paperCutAfterEachPage = false;



    public AttributesPdf() {
    }

    // get & set



    public int getPage() {
        return page;
    }
    public int getWidthTruncateMode() {
        return widthTruncateMode;
    }
    public boolean isClearHeaderAndFooter() {
        return clearHeaderAndFooter;
    }
    public boolean isCutTop() {
        return cutTop;
    }
    public int getCutTopVal() {
        return cutTopVal;
    }
    public boolean isPaperCutAfterEachPage() {
        return paperCutAfterEachPage;
    }
    public boolean isRoll() {
        return roll;
    }
    public int getRotate() {
        return rotate;
    }
    public boolean isCutFrame() {
        return cutFrame;
    }
    public int getCutFrameVal() {
        return cutFrameVal;
    }

    public AttributesPdf setPage(int page) {
        this.page = page;
        return this;
    }

    public AttributesPdf setRoll(boolean roll) {
        this.roll = roll;
        return this;
    }

    public AttributesPdf setWidthTruncateMode(int widthTruncateMode) {
        this.widthTruncateMode = widthTruncateMode;
        return this;
    }

    public AttributesPdf setRotate(int rotate) {
        this.rotate = rotate;
        return this;
    }

    public AttributesPdf setClearHeaderAndFooter(boolean clearHeaderAndFooter) {
        this.clearHeaderAndFooter = clearHeaderAndFooter;
        return this;
    }

    public AttributesPdf setCutTop(boolean cutTop) {
        this.cutTop = cutTop;
        return this;
    }

    public AttributesPdf setCutTopVal(int cutTopVal) {
        this.cutTopVal = cutTopVal;
        return this;
    }

    public AttributesPdf setPaperCutAfterEachPage(boolean paperCutAfterEachPage) {
        this.paperCutAfterEachPage = paperCutAfterEachPage;
        return this;
    }

    public AttributesPdf setCutFrame(boolean cutFrame) {
        this.cutFrame = cutFrame;
        return this;
    }

    public AttributesPdf setCutFrameVal(int cutFrameVal) {
        this.cutFrameVal = cutFrameVal;
        return this;
    }

    // implements parcelable

    protected AttributesPdf(Parcel in) {
        page = in.readInt();
        widthTruncateMode = in.readInt();
        clearHeaderAndFooter = in.readByte() != 0;
        cutTop = in.readByte() != 0;
        cutTopVal = in.readInt();
        paperCutAfterEachPage = in.readByte() != 0;
        roll = in.readByte() != 0;
        rotate = in.readInt();
        cutFrame = in.readByte() != 0;
        cutFrameVal = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(widthTruncateMode);
        dest.writeByte((byte) (clearHeaderAndFooter ? 1 : 0));
        dest.writeByte((byte) (cutTop ? 1 : 0));
        dest.writeInt(cutTopVal);
        dest.writeByte((byte) (paperCutAfterEachPage ? 1 : 0));
        dest.writeByte((byte) (roll ? 1 : 0));
        dest.writeInt(rotate);
        dest.writeByte((byte) (cutFrame ? 1 : 0));
        dest.writeInt(cutFrameVal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AttributesPdf> CREATOR = new Creator<AttributesPdf>() {
        @Override
        public AttributesPdf createFromParcel(Parcel in) {
            return new AttributesPdf(in);
        }

        @Override
        public AttributesPdf[] newArray(int size) {
            return new AttributesPdf[size];
        }
    };

}
