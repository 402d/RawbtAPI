package rawbt.api.command;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CommandEmulate implements RawbtCommand{
    final public static String TAG = "emulate"; // for GSON
    String command = TAG;

    String parcelable;
    String mode;

    public CommandEmulate(@NonNull Parcelable parcelable, @NonNull String mode) {
        this.parcelable = parcelable.toString();
        this.mode = mode;
    }

    // ============ get & set =============

    @Nullable
    public String getParcelable() {
        return parcelable;
    }

    public void setParcelable(@NonNull String parcelable) {
        this.parcelable = parcelable;
    }

    @Nullable
    public String getMode() {
        return mode;
    }

    public void setMode(@NonNull String mode) {
        this.mode = mode;
    }
}
