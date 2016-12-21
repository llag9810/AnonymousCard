package xyz.viseator.anonymouscard.data;

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
    public static byte[] DataPackageToByte(UDPDataPackage udpDataPackage) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream;
        try {
            outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(udpDataPackage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static UDPDataPackage ByteToDataPackage(byte[] bytes) {
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
        DataPackage dataPackage = new DataPackage();
        UDPDataPackage udpDataPackage = new UDPDataPackage(dataPackage);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
            udpDataPackage = (UDPDataPackage) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return udpDataPackage;
    }
}
