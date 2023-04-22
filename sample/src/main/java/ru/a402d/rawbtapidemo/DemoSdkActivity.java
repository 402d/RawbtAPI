package ru.a402d.rawbtapidemo;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Objects;

import rawbt.api.AppCompatWithRawbtActivity;

import rawbt.api.SelectPrinterAdapter;
import rawbt.api.attributes.AttributesBarcode;
import rawbt.api.attributes.AttributesString;
import rawbt.api.attributes.AttributesImage;
import rawbt.api.command.CommandBarcode;
import rawbt.api.RawbtPrintJob;
import static rawbt.api.Constant.*;

// extend AppCompatWithRawbtActivity or AppCompatWithRawbtWsActivity
public class DemoSdkActivity extends AppCompatWithRawbtActivity {



    private final RawbtPrintJob attrJob = new RawbtPrintJob();

    // -----------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo_sdk);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout =  findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // AttrJob

        attrJob.setCopies(1);

        spinnerSelectPrinter = findViewById(R.id.spinnerSelectPrinter);

        adapterSelectPrinter = new SelectPrinterAdapter(this,android.R.layout.simple_spinner_item);
        adapterSelectPrinter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerSelectPrinter.setAdapter(adapterSelectPrinter);
        spinnerSelectPrinter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(adapterSelectPrinter.getCount()<1) return;
                    try {
                        attrJob.setPrinter(Objects.requireNonNull(adapterSelectPrinter.getItem(position)).name);
                    }catch (Exception ignored){}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Spinner spinnerSelectTemplate = findViewById(R.id.spinnerSelectTemplate);
        ArrayAdapter<String> adapterSelectTemplate = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, getTemplateList());
        adapterSelectTemplate.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerSelectTemplate.setAdapter(adapterSelectTemplate);
        spinnerSelectTemplate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    attrJob.setTemplate(RawbtPrintJob.TEMPLATE_NONE);
                }else{
                    attrJob.setTemplate(RawbtPrintJob.TEMPLATE_DEFAULT);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerNumbersCopies = findViewById(R.id.spinnerNumbersCopies);
        ArrayAdapter<String> adapterNumbersCopies = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,getNumbersCopies());
        adapterNumbersCopies.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerNumbersCopies.setAdapter(adapterNumbersCopies);
        spinnerNumbersCopies.setSelection(attrJob.getCopies()-1);
        spinnerNumbersCopies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                attrJob.setCopies(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Examples

        findViewById(R.id.sdk_test1).setOnClickListener((v)->demo_job());
        findViewById(R.id.sdk_test_font).setOnClickListener((v)->demo_font());
        findViewById(R.id.sdk_test_image).setOnClickListener((v)->demo_image());

        findViewById(R.id.sdk_test_barcode).setOnClickListener((v)->demo_barcode());
        findViewById(R.id.sdk_test_barcode2).setOnClickListener((v)->demo_barcode2());

        findViewById(R.id.sdk_test_rich_format).setOnClickListener((v)->demo_rich_format());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // ------------------------------


    private ArrayList<String> getTemplateList() {
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(RawbtPrintJob.TEMPLATE_DEFAULT);
        arrayList.add(RawbtPrintJob.TEMPLATE_NONE);

        return arrayList;
    }

    private static ArrayList<String> getNumbersCopies(){
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");
        arrayList.add("6");
        arrayList.add("7");
        arrayList.add("8");
        arrayList.add("9");
        arrayList.add("10");

        return arrayList;
    }

    // ------------------------------

    private void demo_job(){
        RawbtPrintJob job = new RawbtPrintJob();

        job.setPrinter(attrJob.getPrinter());
        job.setTemplate(attrJob.getTemplate());
        job.setCopies(attrJob.getCopies());

        job.println("Hello,World!");

        Toast.makeText(this, getString(R.string.btnTxtPrint), Toast.LENGTH_SHORT).show();
        printJob(job);
    }

    private void demo_font(){
        RawbtPrintJob job = new RawbtPrintJob();

        job.setPrinter(attrJob.getPrinter());
        job.setTemplate(attrJob.getTemplate());
        job.setCopies(attrJob.getCopies());

        // default values
        AttributesString attrStr = new AttributesString();
        attrStr.setPrinterFont(FONT_A);
        attrStr.setAlignment(ALIGNMENT_LEFT);
        attrStr.setBold(false);
        attrStr.setUnderline(false);
        attrStr.setDoubleHeight(false);
        attrStr.setDoubleWidth(false);

        // set it as default for job
        job.setDefaultAttrString(attrStr);

        // attr for title
        AttributesString attrStrTitle = (new AttributesString()).setAlignment(ALIGNMENT_CENTER).setDoubleHeight(true);

        job.println("Important",attrStrTitle);


        job.println("If a document requires several styles, then specify them explicitly for each purpose.");
        job.println("Since the arguments are passed by reference, do not change the values of the arguments along the way.");
        job.ln();

        job.println("Font sizes",attrStrTitle);

        AttributesString fAw1h1 = (new AttributesString())
                .setPrinterFont(FONT_A);
        AttributesString fAw1h2 = (new AttributesString())
                .setPrinterFont(FONT_A)
                .setDoubleHeight(true);
        AttributesString fAw2h1 = new AttributesString()
                .setPrinterFont(FONT_A)
                .setDoubleWidth(true);
        AttributesString fAw2h2 = (new AttributesString())
                .setPrinterFont(FONT_A)
                .setDoubleHeight(true)
                .setDoubleWidth(true);
        AttributesString fBw1h1 = (new AttributesString())
                .setPrinterFont(FONT_B);
        AttributesString fBw1h2 = (new AttributesString())
                .setPrinterFont(FONT_B)
                .setDoubleHeight(true);
        AttributesString fBw2h1 = (new AttributesString())
                .setPrinterFont(FONT_B)
                .setDoubleWidth(true);
        AttributesString fBw2h2 = (new AttributesString())
                .setPrinterFont(FONT_B)
                .setDoubleHeight(true)
                .setDoubleWidth(true);

        job.println("Normal Font A",fAw1h1);
        job.println("Double height",fAw1h2);
        job.println("Double width",fAw2h1);
        job.println("width & height",fAw2h2);
        job.ln();

        job.println("Normal Font B",fBw1h1);
        job.println("Double height",fBw1h2);
        job.println("Double width",fBw2h1);
        job.println("width & height",fBw2h2);
        job.ln(3);

        job.println("True Type",attrStrTitle);

        AttributesString attrTrueType = (new AttributesString()).setPrinterFont(FONT_TRUE_TYPE);
        job.println("If the printer does not support the required language, then text printing is available by creating a picture with text displayed in a truetype font.",attrTrueType);
        job.println("à, è, ì, ò, ù, À, È, Ì, Ò, Ù, á, é, í, ó, ú, ý, Á, É, Í, Ó, Ú, Ý, â, ê, î, ô, û, ð, Â, Ê, Î, Ô, Û, Ð, ã, ñ, õ, Ã, Ñ, Õ, ä, ë, ï, ö, ü, ÿ, Ä, Ë, Ï, Ö, Ü, Ÿ, å, Å, æ, œ, Æ, Œ or ß, ç, Ç, ¿ , ¡",attrTrueType);
        job.ln(2);

        job.println("Alignment",attrStrTitle);
        AttributesString left = new AttributesString().setAlignment(ALIGNMENT_LEFT);
        AttributesString right = new AttributesString().setAlignment(ALIGNMENT_RIGHT);
        AttributesString center = new AttributesString().setAlignment(ALIGNMENT_CENTER);

        job.println("left",left);
        job.println("center",center);
        job.println("right",right);
        job.ln(2);

        job.println("Decoration",attrStrTitle);
        AttributesString bold = new AttributesString().setBold(true);
        AttributesString underline = new AttributesString().setUnderline(true);
        AttributesString underlineBold = new AttributesString().setUnderline(true).setBold(true);
        job.println("Normal text");
        job.println("Bold text.",bold);
        job.println("Underline text",underline);
        job.println("Underline bold",underlineBold);
        job.ln(2);

        job.println("Notice",bold);
        job.println("Using different styles on the same line is not supported. See rich format.");
        job.ln();

        Toast.makeText(this, getString(R.string.btnTxtPrint), Toast.LENGTH_SHORT).show();
        printJob(job);
    }

    private void demo_rich_format(){
        RawbtPrintJob job = new RawbtPrintJob();

        job.setPrinter(attrJob.getPrinter());
        job.setTemplate(attrJob.getTemplate());
        job.setCopies(attrJob.getCopies());

        // default values
        AttributesString attrStr = new AttributesString();
        attrStr.setPrinterFont(FONT_A);
        attrStr.setAlignment(ALIGNMENT_LEFT);
        attrStr.setBold(false);
        attrStr.setUnderline(false);
        attrStr.setDoubleHeight(false);
        attrStr.setDoubleWidth(false);

        // set it as default for job
        job.setDefaultAttrString(attrStr);

        // attr for title
        AttributesString attrStrTitle = (new AttributesString()).setPrinterFont(FONT_A).setAlignment(ALIGNMENT_CENTER).setDoubleHeight(true).setDoubleWidth(true);

        job.println("Rich Format",attrStrTitle);
        job.drawLine('*' ,attrStrTitle);

        job.println("drawLine(char) - print full line of char");
        job.drawLine('-');
        job.drawLine('=');
        job.drawLine('.');
        job.ln(2);

        job.println("leftRightText() ");
        job.drawLine('-');
        job.leftRightText("left part","right part");
        job.leftIndentRightText(6,"left indent 6","right part");
        job.leftRightIndentText(4,"left part","right indent 4");
        job.drawLine('=');
        job.leftRightTextWithFormat("Total","100.00",attrStrTitle);
        job.leftRightText("Long text as left part","compare 58/80 mm");
        job.leftRightTextWithBothFormat("width","Height",
                attrStr.build().setDoubleWidth(true),
                attrStr.build().setDoubleHeight(true));

        Toast.makeText(this, getString(R.string.btnTxtPrint), Toast.LENGTH_SHORT).show();
        printJob(job);
    }

    private void demo_image(){
        RawbtPrintJob job = new RawbtPrintJob();

        job.setPrinter(attrJob.getPrinter());
        job.setTemplate(attrJob.getTemplate());
        job.setCopies(attrJob.getCopies());

        // use content provider
        // Uri uri = FileProvider.getUriForFile(this, YOU_APP_FILE_PROVIDER, imageFile);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bwlogo);

        // attr for title
        AttributesString attrStrTitle = new AttributesString().setAlignment(ALIGNMENT_CENTER).setDoubleHeight(true);

        job.println("Images",attrStrTitle);
        job.println("The picture is scaled to the width of the printer as a fraction. 16/16 means full width.Values 1-16 allowed.");
        job.image64(this,uri);
        job.ln();
        job.println("Scale 16(default) - full width");
        job.ln();

        AttributesImage im50center = new AttributesImage().setScale(8).setAlignment(ALIGNMENT_CENTER);
        job.image64(this,uri,im50center);
        job.ln();
        job.println("Scale: 8(50%). Alignment: center");
        job.ln();

        AttributesImage im75right = new AttributesImage().setScale(12).setAlignment(ALIGNMENT_RIGHT);
        job.image64(this,uri,im75right);
        job.ln();
        job.println("Scale: 12(75%). Alignment: right");
        job.ln();

        AttributesImage im25left = new AttributesImage().setScale(4).setAlignment(ALIGNMENT_LEFT);
        job.image64(this,uri,im25left);
        job.ln();
        job.println("Scale: 4(25%). Alignment: left");
        job.ln();

        AttributesImage imRotated = new AttributesImage().setRotateImage(true);
        job.image64(this,uri,imRotated);
        job.ln();
        job.println("Rotate");
        job.ln(3);

        Toast.makeText(this, getString(R.string.btnTxtPrint), Toast.LENGTH_SHORT).show();
        printJob(job);
    }

    private void demo_barcode(){

        RawbtPrintJob job = new RawbtPrintJob();

        job.setPrinter(attrJob.getPrinter());
        job.setTemplate(attrJob.getTemplate());
        job.setCopies(attrJob.getCopies());

        // attr for title
        AttributesString attrStrTitle = (new AttributesString()).setAlignment(ALIGNMENT_CENTER).setDoubleHeight(true);

        job.println("Barcode",attrStrTitle);
        job.println("HRI & Alignment");
        job.ln();
        // Можно добавлять команды явно
        job.println("Center / HRI none");
        job.add((new CommandBarcode(BARCODE_UPC_A,"012345678905")).setHeight(64).setAlignment(ALIGNMENT_CENTER));
        job.ln();

        job.println("Left / HRI above");
        job.add(new CommandBarcode(BARCODE_UPC_A,"012345678905").setHeight(64).setHri(HRI_ABOVE).setAlignment(ALIGNMENT_LEFT));
        job.ln();

        job.println("Right / HRI bellow");
        job.add(new CommandBarcode(BARCODE_UPC_A,"012345678905").setHeight(64).setHri(HRI_BELOW).setAlignment(ALIGNMENT_RIGHT));
        job.ln();

        job.println("Height",new AttributesString().setBold(true));
        job.ln();

        job.println("Default look");
        job.add(new CommandBarcode(BARCODE_UPC_E,"0123456").setHri(HRI_BOTH));
        job.ln();

        job.println("Minimum. 12 dots");
        job.add(new CommandBarcode(BARCODE_UPC_E,"0123456").setHeight(12).setHri(HRI_BOTH));
        job.ln();

        job.println("Safe max. 192 dots");
        job.add(new CommandBarcode(BARCODE_UPC_E,"01234565").setHeight(192).setHri(HRI_BOTH));
        job.ln();

        job.println("Width",new AttributesString().setBold(true));
        job.ln();
        job.println("Default look");
        job.add(new CommandBarcode(BARCODE_EAN13,"012345678901").setHeight(32).setHri(HRI_BOTH));
        job.println("1-4 for 58mm");
        job.add(new CommandBarcode(BARCODE_EAN13,"012345678901").setHeight(32).setWidth(1).setHri(HRI_BOTH));
        job.add(new CommandBarcode(BARCODE_EAN13,"012345678901").setHeight(32).setWidth(2).setHri(HRI_BOTH));
        job.add(new CommandBarcode(BARCODE_EAN13,"012345678901").setHeight(32).setWidth(3).setHri(HRI_BOTH));
        job.add(new CommandBarcode(BARCODE_EAN13,"012345678901").setHeight(32).setWidth(4).setHri(HRI_BOTH));
        job.println("5-6 for 80mm or for 300 dpi");
        job.println("5)");
        job.add(new CommandBarcode(BARCODE_EAN13,"012345678901").setHeight(64).setWidth(5).setHri(HRI_BOTH));
        job.println("6)");
        job.add(new CommandBarcode(BARCODE_EAN13,"012345678901").setHeight(64).setWidth(6).setHri(HRI_BOTH));
        job.println("Reduces the width when overflow");
        job.println("7)");
        job.add(new CommandBarcode(BARCODE_EAN13,"012345678901").setHeight(96).setWidth(7).setHri(HRI_BOTH));
        job.println("8)");
        job.add(new CommandBarcode(BARCODE_EAN13,"012345678901").setHeight(96).setWidth(8).setHri(HRI_BOTH));
        job.ln(2);


        Toast.makeText(this, getString(R.string.btnTxtPrint), Toast.LENGTH_SHORT).show();
        printJob(job);
    }

    private void demo_barcode2() {

        RawbtPrintJob job = new RawbtPrintJob();
        job.setPrinter(attrJob.getPrinter());
        job.setTemplate(attrJob.getTemplate());
        job.setCopies(attrJob.getCopies());

        // attr for barcode
        AttributesBarcode a = new AttributesBarcode().setHeight(64).setHri(HRI_BELOW);

        // attr for title
        AttributesString attrStrTitle = (new AttributesString()).setAlignment(ALIGNMENT_CENTER).setDoubleHeight(true).setDoubleWidth(true);

        job.println("UPC-A", attrStrTitle);
        job.barcode("40204020402",a.setType(BARCODE_UPC_A).build());
        job.ln();

        job.println("UPC-E", attrStrTitle);
        job.barcode("0402402",a.setType(BARCODE_UPC_E).build());
        job.ln();

        job.println("EAN13", attrStrTitle);
        job.barcode("4606224236582",a.setType(BARCODE_EAN13).build());
        job.ln();

        job.println("EAN8", attrStrTitle);
        job.barcode("4020402",a.setType(BARCODE_EAN8).build());
        job.ln();

        job.println("CODE39", attrStrTitle);
        job.barcode("RAWBT-402D",a.setType(BARCODE_CODE39).build());
        job.ln();

        job.println("ITF", attrStrTitle);
        job.barcode("30712345000010",a.setType(BARCODE_ITF).build());
        job.ln();

        job.println("CODOBAR", attrStrTitle);
        job.barcode("A4020402A",a.setType(BARCODE_CODABAR).build());
        job.ln();

        job.println("CODE93", attrStrTitle);
        job.barcode("RAWBT-402-/+",a.setType(BARCODE_CODE93).build());
        job.ln();

        job.println("CODE128", attrStrTitle);
        job.barcode("RawBT402d",a.setType(BARCODE_CODE128).build());
        job.ln();


        Toast.makeText(this, getString(R.string.btnTxtPrint), Toast.LENGTH_SHORT).show();
        printJob(job);
    }




    // ---------------------- implements ----------------

    @Override
    protected void handleServiceConnected() {
        // auto start print where
    }

    @Override
    protected void handlePrintSuccess(String jobId) {
        Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void handlePrintCancel(String jobId) {
        Toast.makeText(this,"Canceled",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void handlePrintError(@Nullable String jobId, String message) {
        if(message == null) return;
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void handlePrintProgress(String jobId, Float p) {
        // nothing
    }

    @Override
    protected String getSelectedPrinterName() {
        return ""; // return previous selected name
    }

}