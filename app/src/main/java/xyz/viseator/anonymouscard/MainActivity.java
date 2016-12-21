package xyz.viseator.anonymouscard;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import xyz.viseator.anonymouscard.data.DataPackage;
import xyz.viseator.anonymouscard.data.UDPDataPackage;
import xyz.viseator.anonymouscard.network.ComUtil;
import xyz.viseator.anonymouscard.network.GetNetworkInfo;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "wudi MainActivity";
    private DataPackage dataPackage;
    private Button mbuttonSend, mButtonJoin;
    private EditText editText;
    private TextView textViewShowIP,textViewShowMac,textViewTitle;
    private ComUtil comUtil = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ComUtil.BROADCAST_PORT:
                    byte[] data = (byte[]) msg.obj;
                    Toast.makeText(MainActivity.this, "Received", Toast.LENGTH_SHORT).show();
                    ByteArrayInputStream byteInputStream = new ByteArrayInputStream(data);
                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
                        UDPDataPackage udpDataPackage = (UDPDataPackage) objectInputStream.readObject();
                        textView.setText(udpDataPackage.getTitle());
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbuttonSend = (Button) findViewById(R.id.send_msg);
        mbuttonSend.setOnClickListener(this);
        mButtonJoin = (Button) findViewById(R.id.join_group);
        mButtonJoin.setOnClickListener(this);
        editText = (EditText) findViewById(R.id.edit_msg);
        //textView = (TextView) findViewById(R.id.show_rec_content);
        //textView.setText(GetNetworkInfo.getIp(this) + " " + GetNetworkInfo.getMac(this));
        textViewShowIP=(TextView)findViewById(R.id.show_ip);
        textViewShowMac=(TextView)findViewById(R.id.show_mac);
        textViewTitle=(TextView)findViewById(R.id.show_title);
        comUtil = new ComUtil(handler);
        dataPackage = new DataPackage();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_msg:
                String str = "Test Title";
                dataPackage.setTitle(str);
                dataPackage.setIpAddress(GetNetworkInfo.getIp(this));
                dataPackage.setMacAddress(GetNetworkInfo.getMac(this));

                UDPDataPackage udpDataPackage = new UDPDataPackage(dataPackage);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream outputStream;

                try {
                    outputStream = new ObjectOutputStream(byteArrayOutputStream);
                    outputStream.writeObject(udpDataPackage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                byte[] data = byteArrayOutputStream.toByteArray();
                Log.d(TAG, String.valueOf(data));
                comUtil.broadCast(data);
                break;
            case R.id.join_group:
                comUtil.startRecieveMsg();
                break;
        }
    }


}
