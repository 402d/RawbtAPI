package rawbt.api;

public class Constant {
    public enum RAWBT_CONTENT_TYPE {image,pdf,prn,txt,html, unsupported}

    public static final int DITHERING_BW = 0;
    public static final int DITHERING_SF = 1;
    public static final int DITHERING_ATKINSON = 2;
    public static final int DITHERING_BURKES = 3;
    public static final int DITHERING_SIERA = 4;
    public static final int DITHERING_SKETCH = 5;
    public static final int DITHERING_BEST_CONTRAST = 6;
    public static final int DITHERING_REGULAR = 7 ;
    public static final int DITHERING_NONE_RESIZE_ONLY = 8 ;
    public static final int DITHERING_127 = 9;


    public static final int FONT_DEFAULT = 0;
    public static final int FONT_A = 1;
    public static final int FONT_B = 2;
    public static final int FONT_C = 3;
    public static final int FONT_TRUE_TYPE = 4;

    public static final int CPI_DEFAULT = 0;
    public static final int CPI_NORMAL = 1;
    public static final int CPI_CONDENSED = 2;
    public static final int CPI_MONO = 3;


    // Alignment
    public static final String ALIGNMENT_LEFT = "left";
    public static final String ALIGNMENT_RIGHT = "right";
    public static final String ALIGNMENT_CENTER = "center";

    // barcode
    public static final String BARCODE_UPC_A = "upc_a";
    public static final String BARCODE_UPC_E = "upc_e";
    public static final String BARCODE_EAN13 = "ean13";
    public static final String BARCODE_JAN13 = "jan13";
    public static final String BARCODE_EAN8 = "ean8";
    public static final String BARCODE_JAN8 = "jan8";
    public static final String BARCODE_CODE39 = "code39";
    public static final String BARCODE_ITF = "itf";
    public static final String BARCODE_CODABAR = "codabar";
    public static final String BARCODE_CODE93 = "code93";
    public static final String BARCODE_CODE128 = "code128";
    public static final String BARCODE_GS1_128 = "gs1_128";
    public static final String BARCODE_GS1_DATABAR_OMNIDIRECTIONAL = "databar_omni";
    public static final String BARCODE_GS1_DATABAR_TRUNCATED = "databar_trunc";
    public static final String BARCODE_GS1_DATABAR_LIMITED = "databar_limit";
    public static final String BARCODE_GS1_DATABAR_EXPANDED = "databar_expand";

    // hri
    public static final String HRI_NONE = "none";
    public static final String HRI_ABOVE = "above";
    public static final String HRI_BELOW = "below";
    public static final String HRI_BOTH = "both";


}
