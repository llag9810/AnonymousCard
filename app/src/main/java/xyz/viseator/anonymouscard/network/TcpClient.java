package xyz.viseator.anonymouscard.network;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import xyz.viseator.anonymouscard.data.DataPackage;
import xyz.viseator.anonymouscard.data.UDPDataPackage;

/**
 * Created by viseator on 2016/12/23.
 * Wudi
 * viseator@gmail.com
 */

public class TcpClient {
    public static int SERVER_PORT = 7889;
    private Thread thread;
    private String ipAddress;
    private UDPDataPackage udpDataPackage;
    private Handler handler;


    class SendData implements Runnable {
        @Override
        public void run()  {
            try {
                Socket socket = new Socket(ipAddress, SERVER_PORT);
                socket.setReuseAddress(true);
                socket.setKeepAlive(true);

                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(udpDataPackage);

                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                DataPackage dataPackage = (DataPackage) objectInputStream.readObject();

                Message msg = new Message();
                msg.what = SERVER_PORT;
                msg.obj = dataPackage;
                handler.sendMessage(msg);
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendRequest(String ipAddress, UDPDataPackage udpDataPackage, Handler handler) {
        this.ipAddress = ipAddress;
        this.udpDataPackage = udpDataPackage;
        this.handler = handler;
        thread = new Thread(new SendData());
        thread.start();
    }
}
