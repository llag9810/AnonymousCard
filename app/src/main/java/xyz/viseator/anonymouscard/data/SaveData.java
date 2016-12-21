package xyz.viseator.anonymouscard.data;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by viseator on 2016/12/21.
 * Wudi
 * viseator@gmail.com
 */

public class SaveData {
    public static void writeToFile(Context context, byte[] data, String fileName) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName + ".txt", Context.MODE_PRIVATE);
            fos.write(data);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
