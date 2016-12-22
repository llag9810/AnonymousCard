package xyz.viseator.anonymouscard.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by viseator on 2016/12/21.
 * Wudi
 * viseator@gmail.com
 */

public class ConvertData {
    public static byte[] objectToByte(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream;
        try {
            outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static Object byteToObject(byte[] bytes) {
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
        Object object = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
            object = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
        return byteStream.toByteArray();

    }

    public static Bitmap byteToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
