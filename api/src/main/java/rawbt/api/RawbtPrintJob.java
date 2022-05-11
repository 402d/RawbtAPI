package rawbt.api;

import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import java.util.ArrayList;
import rawbt.api.attributes.AttributesBarcode;
import rawbt.api.attributes.AttributesImage;
import rawbt.api.attributes.AttributesPdf;
import rawbt.api.attributes.AttributesQRcode;
import rawbt.api.attributes.AttributesString;
import rawbt.api.command.CommandBarcode;
import rawbt.api.command.CommandBytesInBase64;
import rawbt.api.command.CommandCut;
import rawbt.api.command.CommandDelimiterImages;
import rawbt.api.command.CommandDrawLine;
import rawbt.api.command.CommandEmulate;
import rawbt.api.command.CommandLeftRightText;
import rawbt.api.command.CommandNewLine;
import rawbt.api.command.CommandParcelable;
import rawbt.api.command.CommandQRcode;
import rawbt.api.command.CommandString;
import rawbt.api.command.RawbtCommand;

public class RawbtPrintJob {
    public static final String ACTION_PRINT_JOB = "rawbt.action.PRINT";
    public static final String EXTRA_JOB = "rawbt.action.extra.JOB_JSON";

    String idJob = null;

    ArrayList<RawbtCommand> commands = new ArrayList<>();

    int copies = 1;

    public final static String TEMPLATE_NONE = "none";
    public final static String TEMPLATE_DEFAULT = "default";
    public final static String TEMPLATE_SIMPLE = "simple";

    String template = TEMPLATE_DEFAULT;

    public final static String PRINTER_CURRENT = "current";
    public final static String PRINTER_VIRTUAL = "virtual";
    public final static String PRINTER_RAW_TRANSFER = "raw_transfer";
    public final static String PRINTER_GALLERY = "save_into_gallery";

    String printer = PRINTER_CURRENT;

    // ------------------
    AttributesString defaultAttrString = null;
    AttributesImage defaultAttrImage = null;
    AttributesPdf defaultAttrPdf = null;
    // -----------------

    boolean premium = false;

    // ============= get & set ==================

    public String getIdJob() {
        return idJob;
    }

    public void setIdJob(String idJob) {
        this.idJob = idJob;
    }

    public void add(RawbtCommand command){
        commands.add(command);
    }

    public ArrayList<RawbtCommand> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<RawbtCommand> commands) {
        this.commands = commands;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(@NonNull String template) {
        this.template = template;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(@NonNull String printer) {
        this.printer = printer;
    }

    public AttributesString getDefaultAttrString() {
        return defaultAttrString == null? new AttributesString():defaultAttrString;
    }

    public void setDefaultAttrString(@NonNull AttributesString defaultAttrString) {
        this.defaultAttrString = defaultAttrString;
    }
    public boolean isNullDefaultAttrString(){
        return  defaultAttrString == null;
    }
    public AttributesImage getDefaultAttrImage() {
        return defaultAttrImage == null?new AttributesImage():defaultAttrImage;
    }

    public void setDefaultAttrImage(@NonNull AttributesImage defaultAttrImage) {
        this.defaultAttrImage = defaultAttrImage;
    }

    public boolean isNullDefaultAttrImage(){
        return defaultAttrImage == null;
    }


    public AttributesPdf getDefaultAttrPdf() {
        return defaultAttrPdf == null?new AttributesPdf():defaultAttrPdf;
    }

    public void setDefaultAttrPdf(@NonNull AttributesPdf defaultAttrPdf) {
        this.defaultAttrPdf = defaultAttrPdf;
    }
    public boolean isNullDefaultAttrPdf(){
        return defaultAttrPdf == null;
    }

    // ========== строка текста ===========

    public void println(@NonNull String string){
        commands.add(new CommandString(string,getDefaultAttrString()));
    }

    public void println(@NonNull String string,@NonNull  AttributesString attr){
        commands.add(new CommandString(string,attr));
    }

    public void ln(){
        commands.add(new CommandNewLine(1));
    }

    public void ln(int n){
        commands.add(new CommandNewLine(n));
    }

    // ========== rich format ==============

    public void leftRightText(String leftText, String rightText){
        commands.add(new CommandLeftRightText(leftText,rightText,getDefaultAttrString()));
    }

    public void leftRightTextWithFormat(String leftText, String rightText,AttributesString attr){
        commands.add(new CommandLeftRightText(leftText,rightText,attr));
    }

    public void leftRightTextWithBothFormat(String leftText, String rightText,AttributesString attrLeft,AttributesString attrRight){
        commands.add(new CommandLeftRightText(leftText,rightText,0,0,attrLeft,attrRight));
    }


    public void leftIndentRightText(int leftIndent, String leftText, String rightText){
        CommandLeftRightText command = new CommandLeftRightText(leftText,rightText,getDefaultAttrString());
        command.setLeftIndent(leftIndent);
        commands.add(command);
    }

    public void leftIndentRightTextWithFormat(int leftIndent, String leftText, String rightText,AttributesString attr){
        CommandLeftRightText command = new CommandLeftRightText(leftText,rightText,attr);
        command.setLeftIndent(leftIndent);
        commands.add(command);
    }


    public void leftRightIndentText(int rightIndent, String leftText, String rightText){
        CommandLeftRightText command = new CommandLeftRightText(leftText,rightText,getDefaultAttrString());
        command.setRightIndent(rightIndent);
        commands.add(command);
    }


    public void leftRightIndentTextWithFormat(int rightIndent, String leftText, String rightText, AttributesString attr){
        CommandLeftRightText command = new CommandLeftRightText(leftText,rightText,attr);
        command.setRightIndent(rightIndent);
        commands.add(command);
    }

    // ========= картинка =================
    public void image(@NonNull Parcelable parcelable){
        image(parcelable, getDefaultAttrImage());
    }

    public void image(@NonNull Parcelable parcelable,@NonNull  AttributesImage attributesImage){
        CommandParcelable command = new CommandParcelable(parcelable, Constant.RAWBT_CONTENT_TYPE.image);
        command.setAttributesImage(attributesImage);
        commands.add(command);
    }

    // ========= текстовый файл  =================
    public void text(@NonNull Parcelable parcelable){
        text(parcelable,getDefaultAttrString());
    }

    public void text(@NonNull Parcelable parcelable,@NonNull AttributesString attr){
        CommandParcelable command = new CommandParcelable(parcelable, Constant.RAWBT_CONTENT_TYPE.txt);
        command.setAttributesString(attr);
        commands.add(command);
    }

    // ============== prn & bytes =====================
    public void bytes(@NonNull Parcelable parcelable){
        commands.add(new CommandParcelable(parcelable, Constant.RAWBT_CONTENT_TYPE.prn));
    }

    public void sendBytes(@NonNull String base64){
        commands.add(new CommandBytesInBase64(base64));
    }

    // ========== pdf ============================
    public void pdf(@NonNull Parcelable parcelable,@NonNull  AttributesPdf attr){
        CommandParcelable command = new CommandParcelable(parcelable, Constant.RAWBT_CONTENT_TYPE.pdf);
        command.setAttributesPdf(attr);
        command.setAttributesImage(getDefaultAttrImage());
        commands.add(command);
    }
    public void pdf(@NonNull Parcelable parcelable,@NonNull  AttributesPdf attr,@NonNull  AttributesImage attrImg){
        CommandParcelable command = new CommandParcelable(parcelable,Constant.RAWBT_CONTENT_TYPE.pdf);
        command.setAttributesPdf(attr);
        command.setAttributesImage(attrImg);
        commands.add(command);
    }

    // ========= Emulator ==============

    public void emulate(@NonNull Parcelable parcelable,@NonNull String mode){
        commands.add(new CommandEmulate(parcelable,mode));
    }

    // ========== barcode & qr ==========

    public void barcode(CommandBarcode commandBarcode){
        commands.add(commandBarcode);
    }

    public void barcode(String data, AttributesBarcode attr){
        commands.add(new CommandBarcode(data,attr));
    }

    public void qrcode(CommandQRcode commandQRcode) { commands.add(commandQRcode); }
    public void qrcode(String data, AttributesQRcode attr){
        commands.add(new CommandQRcode(data,attr));
    }


    // ========= cut ===========

    public void cut(){
        commands.add(new CommandCut());
    }

    // =======================
    public void drawLine(Character ch){
        commands.add( new CommandDrawLine(ch,getDefaultAttrString()));
    }

    public void drawLine(Character ch, AttributesString attr){
        commands.add( new CommandDrawLine(ch,attr));
    }

    // =============================

    public void delimiterImages() {
        add(new CommandDelimiterImages());
    }


    // ==============

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    // ====================
    public String GSON(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
