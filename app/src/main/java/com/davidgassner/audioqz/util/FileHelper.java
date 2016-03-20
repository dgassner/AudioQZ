package com.davidgassner.audioqz.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileHelper {

    private static final String TAG = "FILEHELPER";

    public static String copyAssetToCache(Context context, String fileName) {

        String cachedFileName = context.getCacheDir() + fileName;
        File cachedFile = new File(cachedFileName);

//      If the file already exists, just return it
//        TODO: check that the source file hasn't changed!
        if (cachedFile.exists()) {
            Log.d("FileHeper", "Cached file already exists!");
        } else {
            try {
                InputStream inputStream = context.getAssets().open(fileName);
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                int ignore = inputStream.read(buffer);
                inputStream.close();
                FileOutputStream fos = new FileOutputStream(cachedFile);
                fos.write(buffer);
                fos.close();
            } catch (Exception e) {
                Log.e(TAG, "copyAssetToCache: " + e.getMessage() + ", file: " + fileName);
                throw new RuntimeException(e);
            }
        }
        return cachedFileName;
    }
}
