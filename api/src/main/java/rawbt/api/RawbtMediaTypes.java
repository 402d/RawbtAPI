package rawbt.api;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;



public class RawbtMediaTypes {

    static public Constant.RAWBT_CONTENT_TYPE detectContentType(Context context, Uri uri, String type0){

        if(uri == null) return Constant.RAWBT_CONTENT_TYPE.unsupported;

        // сперва пробуем взять реальный тип потока
        String type = getFileMimeType(context,uri);
        if(type == null) {
            // есть уточнение что это ?
            if(type0 == null){
                return Constant.RAWBT_CONTENT_TYPE.unsupported;
            }
            type = type0;
        }

        if(type.equals("application/pdf")){
            return  Constant.RAWBT_CONTENT_TYPE.pdf;
        }

        if (type.startsWith("video")
                || type.startsWith("audio")
                || type.startsWith("model")
                || type.startsWith("multipart")
        ) {
            return Constant.RAWBT_CONTENT_TYPE.unsupported;
        }

        if(type.startsWith("image")){
            return Constant.RAWBT_CONTENT_TYPE.image;
        }

        if(type.equals("text/html")){
            return Constant.RAWBT_CONTENT_TYPE.html;
        }
        if(type.equals("text/prn")){
            return Constant.RAWBT_CONTENT_TYPE.prn;
        }

        return Constant.RAWBT_CONTENT_TYPE.txt;
    }

    public static String getFileMimeType(Context ctx,Uri uri) {
        try {
            String strUrl = uri.toString().toLowerCase();

            if ("content".equalsIgnoreCase(uri.getScheme())) {


                String ret;
                Cursor returnCursor = ctx.getContentResolver().query(uri, null, null, null, null);
                if (returnCursor != null) {
                    int mimeIndex = returnCursor.getColumnIndex("mime_type");

                    returnCursor.moveToFirst();
                    if(mimeIndex>-1) {
                        try {
                            ret = returnCursor.getString(mimeIndex);
                            if (!"application/octet-stream".equals(ret)) {
                                returnCursor.close();
                                return ret;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    int column_index = returnCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
                    returnCursor.moveToFirst();
                    // поищем расширение в отображаемом имени
                    try {
                        strUrl = returnCursor.getString(column_index);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    returnCursor.close();
                }


            }
            // если в урле есть известное расширение.

            if (strUrl.endsWith(".jpg") || strUrl.endsWith(".jpeg") || strUrl.endsWith(".png") || strUrl.endsWith(".webp") || strUrl.endsWith(".gif") || strUrl.endsWith(".bmp")) {
                return "image/*";
            }
            if (strUrl.endsWith(".pdf")) {
                return "application/pdf";
            }
            if (strUrl.endsWith(".txt")) {
                return "text/plain";
            }
            if (strUrl.endsWith(".prn")) {
                return "text/prn";
            }
            if (strUrl.endsWith(".htm") || strUrl.endsWith(".html")) {
                return "text/html";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
