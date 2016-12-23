package xyz.viseator.anonymouscard.network;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import xyz.viseator.anonymouscard.data.DataPackage;

/**
 * Created by yanhao on 16-12-21.
 */
public class SingleUtil {
    private DatagramSocket singleSocket = null;
    private Handler handler = null;
    public static final int SINGLE_PORT = 7817;
    private static final int DATA_LEN = 4096;

    public SingleUtil(Handler handler) {
        try {
            singleSocket = new DatagramSocket(SINGLE_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.handler = handler;
    }

    public SingleUtil() {
        try {
            singleSocket = new DatagramSocket(SINGLE_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void startRecieveMsg() {
        Thread thread = new Thread(new ReadSingle());
        thread.start();
    }

    public void sendSingle0(final byte[] buff, final String ipAddress) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                OutputStream os = null;
                ObjectOutputStream objectos = null;
                try {
                    socket = new Socket(ipAddress, SINGLE_PORT);
                    DataPackage data = new DataPackage();  //将来被替换
                    data.setContent("Hello World");
                    data.setSign(0);                                     //将来被替换
                    data.setBitmap(null);
                    os = socket.getOutputStream();
                    objectos = new ObjectOutputStream(os);
                    objectos.writeObject(data);
                    os.flush();
                    socket.shutdownOutput();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                        os.close();
                        objectos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void sendSingle1(final String ipAddress, final String cardId, final String myIP) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                OutputStream os = null;
                ObjectOutputStream objectos = null;
                try {
                    socket = new Socket(ipAddress, SINGLE_PORT);
                    DataPackage data = new DataPackage();
                    data.setSign(1);
                    data.setId(cardId);
                    data.setIp(myIP);
                    os = socket.getOutputStream();
                    objectos = new ObjectOutputStream(os);
                    objectos.writeObject(data);
                    os.flush();
                    socket.shutdownOutput();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        socket.close();
                        os.close();
                        objectos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }


    class ReadSingle implements Runnable {
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(SINGLE_PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                Socket socket = null;
                InputStream in = null;
                ObjectInputStream objinput = null;
                if (serverSocket != null) {
                    try {
                        socket = serverSocket.accept();
                        in = socket.getInputStream();
                        objinput = new ObjectInputStream(in);
                        DataPackage data1 = (DataPackage) objinput.readObject();
                        int sign = data1.getSign();
                        if (sign == 1) {
                            Log.d("信息:", "收到请求大包");
                            sendConcreteData(data1.getId(), data1.getIp());
                        } else {
                            Message msg = new Message();
                            msg.what = SINGLE_PORT;
                            msg.obj = data1;
                            handler.sendMessage(msg);
                        }

                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.shutdownOutput();
                            objinput.close();
                            in.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        public void sendConcreteData(String cardId, String ipAddress) {
            byte[] bytes = null;      //将来被替换
            sendSingle0(bytes, ipAddress);
        }
    }
}
