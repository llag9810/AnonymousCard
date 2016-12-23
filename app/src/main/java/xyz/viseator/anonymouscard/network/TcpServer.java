package xyz.viseator.anonymouscard.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import xyz.viseator.anonymouscard.data.DataPackage;
import xyz.viseator.anonymouscard.data.DataStore;
import xyz.viseator.anonymouscard.data.UDPDataPackage;

/**
 * Created by viseator on 2016/12/23.
 * Wudi
 * viseator@gmail.com
 */

public class TcpServer {
    public static final int SERVER_PORT = 7889;
    private DataStore dataStore;
    private Thread thread;

    public TcpServer(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    class RunServer implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket();
                serverSocket.setReuseAddress(true);
                serverSocket.bind(new InetSocketAddress(SERVER_PORT));
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    InputStream inputStream;
                    inputStream = socket.getInputStream();
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                    UDPDataPackage udpDataPackage = (UDPDataPackage) objectInputStream.readObject();
                    DataPackage dataPackage = dataStore.getDataById(udpDataPackage.getId());

                    OutputStream outputStream = socket.getOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(dataPackage);
                    objectOutputStream.flush();

                    objectOutputStream.close();
                    outputStream.close();
                    objectInputStream.close();
                    inputStream.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    public void startServer() {
        thread = new Thread(new RunServer());
        thread.start();
    }
}
