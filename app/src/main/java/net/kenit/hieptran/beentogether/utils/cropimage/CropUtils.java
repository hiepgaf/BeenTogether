package net.kenit.hieptran.beentogether.utils.cropimage;

import android.support.annotation.Nullable;

import java.io.Closeable;

/**
 * Created by HiepTran on 16/1/4.
 */
public class CropUtils {
    public static void closeSilently(@Nullable Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (Throwable t) {
            // Do nothing
        }
    }
}
