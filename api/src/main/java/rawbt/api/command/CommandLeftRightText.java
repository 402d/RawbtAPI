package rawbt.api.command;

import rawbt.api.attributes.AttributesString;

public class CommandLeftRightText implements RawbtCommand{
    final public static String TAG = "leftRightText"; // for GSON
    String command = TAG;

    // ===== param ====

    String leftText = "";
    String rightText = "";
    int leftIndent = 0;
    int rightIndent = 0;
    AttributesString leftAttr = null;
    AttributesString rightAttr = null;

    // ===== construct ======
    public CommandLeftRightText() {
    }


    public CommandLeftRightText(String leftText, String rightText, AttributesString attr) {
        this.leftText = leftText;
        this.rightText = rightText;
        this.leftAttr = attr;
        this.rightAttr = attr;
    }

    public CommandLeftRightText(String leftText, String rightText, int leftIndent, int rightIndent, AttributesString leftAttr, AttributesString rightAttr) {
        this.leftText = leftText;
        this.rightText = rightText;
        this.leftIndent = leftIndent;
        this.rightIndent = rightIndent;
        this.leftAttr = leftAttr;
        this.rightAttr = rightAttr;
    }

    // ========= get =========

    public String getLeftText() {
        return leftText;
    }

    public String getRightText() {
        return rightText;
    }

    public int getLeftIndent() {
        return leftIndent;
    }

    public int getRightIndent() {
        return rightIndent;
    }

    public AttributesString getLeftAttr() {
        return leftAttr;
    }

    public AttributesString getRightAttr() {
        return rightAttr;
    }

    // ===== set =========


    public CommandLeftRightText setLeftText(String leftText) {
        this.leftText = leftText;
        return this;
    }

    public CommandLeftRightText setRightText(String rightText) {
        this.rightText = rightText;
        return this;
    }

    public CommandLeftRightText setLeftIndent(int leftIndent) {
        this.leftIndent = leftIndent;
        return this;
    }

    public CommandLeftRightText setRightIndent(int rightIndent) {
        this.rightIndent = rightIndent;
        return this;
    }

    public CommandLeftRightText setLeftAttr(AttributesString leftAttr) {
        this.leftAttr = leftAttr;
        return this;
    }

    public CommandLeftRightText setRightAttr(AttributesString rightAttr) {
        this.rightAttr = rightAttr;
        return this;
    }
}
