package rawbt.api.command;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public CommandImageInBase64(Context context,Uri uri, AttributesImage attributesImage) {
        this.base64 = getImageBase64String(uri,context);
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

    // -----------------
    private String getImageBase64String(Uri uri, Context context) {
        Bitmap image;

            if ("https".equals(uri.getScheme()) || "http".equals(uri.getScheme())) {
                image = getBitmapFromURL(uri.toString());
            } else {
                try {
                    image = MediaStore.Images.Media.getBitmap(context.getContentResolver(),uri);
                } catch (IOException e) {
                    return null;
                }
            }
            if (image == null) {
                return null;
            }
            return bitmapToBase64(image);
    }

    private Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();

            Bitmap bitmap = BitmapFactory.decodeStream(input);

            try {
                input.close();
                connection.disconnect();
            }catch (Exception ignored){}

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


}
