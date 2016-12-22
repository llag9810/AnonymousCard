package xyz.viseator.anonymouscard.network;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

/**
 * Created by yanhao on 16-12-21.
 */

public class SingleUtil {
    private DatagramSocket singleSocket = null;
    private Handler handler=null;
    public static final int SINGLE_PORT=7817;
    private static final int DATA_LEN = 4096;

    public SingleUtil(Handler handler){
        this.handler=handler;
    }

    public void startRecieveMsg(){
        try {
            singleSocket=new DatagramSocket(SINGLE_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        Thread thread=new Thread(new ReadSingle());
        thread.start();
    }

    public void sendSingle(final byte[] buff, final String ipAddress){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetSocketAddress address=new InetSocketAddress(ipAddress,SINGLE_PORT);
                    DatagramPacket packet=new DatagramPacket(buff,buff.length,address);
                    singleSocket.send(packet);
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    class ReadSingle implements Runnable{
        byte[] singleBuff = new byte[DATA_LEN];
        private DatagramPacket singlePacket = new DatagramPacket(singleBuff , singleBuff.length);
        @Override
        public void run() {
            while(true){
                try {
                    singleSocket.receive(singlePacket);
                    Message msg=new Message();
                    msg.what=SINGLE_PORT;
                    msg.obj=singleBuff;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
