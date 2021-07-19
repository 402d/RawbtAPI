package rawbt.api.command;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CommandBytesInBase64 implements RawbtCommand {
    final public static String TAG = "sendBytes"; // for GSON
    String command = TAG;
    String base64 = null;

    public CommandBytesInBase64() {
    }

    public CommandBytesInBase64(String base64) {
        this.base64 = base64;
    }

    @Nullable
    public String getBase64() {
        return base64;
    }

    public void setBase64(@NonNull String base64) {
        this.base64 = base64;
    }
}
