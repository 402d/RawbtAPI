package rawbt.api.command;

import androidx.annotation.NonNull;

import rawbt.api.attributes.AttributesString;

public class CommandDrawLine implements RawbtCommand {
    final public static String TAG = "line"; // for GSON
    String command = TAG;

    Character ch = '-';
    AttributesString attributesString = new AttributesString();

    public CommandDrawLine(){}

    public CommandDrawLine(@NonNull Character ch, @NonNull AttributesString attributesString) {
        this.ch = ch;
        this.attributesString = attributesString;
    }

    public Character getCh() {
        return ch;
    }

    public CommandDrawLine setCh(Character ch) {
        this.ch = ch;
        return this;
    }

    public AttributesString getAttributesString() {
        return attributesString;
    }

    public CommandDrawLine setAttributesString(AttributesString attributesString) {
        this.attributesString = attributesString;
        return this;
    }
}
