package net.kenit.hieptran.beentogether.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by hiepthb on 04/07/2016.
 */
public class Utils {
    public  enum LOVER_TYPE {
        MALE,
        FEMALE
    }
    public static String saveAvatar(LOVER_TYPE type, ImageView imv) {
        BitmapDrawable btmpDr = (BitmapDrawable) imv.getDrawable();
        Bitmap bmp = btmpDr.getBitmap();

/*File sdCardDirectory = Environment.getExternalStorageDirectory();*/
        String imageNameForSDCard = "";
        try {
            File sdCardDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + "BeenTogether");
            sdCardDirectory.mkdirs();

            imageNameForSDCard = type.toString() + System.currentTimeMillis() + ".jpg.tmp";

            File image = new File(sdCardDirectory, imageNameForSDCard);
            FileOutputStream outStream;

            outStream = new FileOutputStream(image);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
    /* 100 to keep full quality of the image */
            outStream.flush();
            outStream.close();


            //Refreshing SD card
            //sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
        } catch (Exception e) {
            e.printStackTrace();

        }
        return imageNameForSDCard;
    }

    public static void saveLover(Context context,String json,String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key,json);
        editor.apply();
    }

    public static String getLover(Context context,String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key,"0");
    }
}
