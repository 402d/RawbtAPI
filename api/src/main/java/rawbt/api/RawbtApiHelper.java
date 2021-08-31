package rawbt.api;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import com.google.gson.Gson;

import static rawbt.api.RawbtPrintJob.ACTION_PRINT_JOB;
import static rawbt.api.RawbtPrintJob.EXTRA_JOB;

public class RawbtApiHelper {
    public static final String SERVICE_PERMISSION = "ru.a402d.rawbtprinter.PERMISSION";
    public static final String SERVICE_PACKAGE = "ru.a402d.rawbtprinter";


    public static void startActionPrintJob(Context context, RawbtPrintJob job) {
        try {
            Gson gson = new Gson();
            String jobJson = gson.toJson(job);

            Intent intent = new Intent();
            intent.setAction(ACTION_PRINT_JOB);
            intent.setPackage(SERVICE_PACKAGE);
            intent.putExtra(EXTRA_JOB, jobJson);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent);
            }else {
                context.startService(intent); // ok
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Intent createExplicitIntent(){
        Intent intent = new Intent();
        intent.setPackage("ru.a402d.rawbtprinter");
        intent.setAction("rawbt.action.SERVICE");
        return intent;
    }

    static public boolean isServiceInstalled(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(RawbtApiHelper.SERVICE_PACKAGE, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


}
