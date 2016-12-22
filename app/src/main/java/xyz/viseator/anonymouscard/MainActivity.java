package xyz.viseator.anonymouscard;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import xyz.viseator.anonymouscard.data.ConvertData;
import xyz.viseator.anonymouscard.data.DataPackage;
import xyz.viseator.anonymouscard.data.DataPackageInSingle;
import xyz.viseator.anonymouscard.data.UDPDataPackage;
import xyz.viseator.anonymouscard.network.ComUtil;
import xyz.viseator.anonymouscard.network.GetNetworkInfo;
import xyz.viseator.anonymouscard.network.SingleUtil;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    private static final String TAG = "wudi MainActivity";
    private DataPackage dataPackage;
    private Button mbuttonSend, mButtonJoin, mButtonSendSingle;
    private EditText editText;
    private TextView textViewShowIP, textViewShowMac, textViewTitle;
    private Button button;
    private ComUtil comUtil = null;
    private ArrayList<String> receivedIds;
    private SingleUtil singleUtil = null;
    private static String testIP = null;
    public static Context context;
    private ImageView imageView;
    public static Bitmap bitmap;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ComUtil.BROADCAST_PORT:
                    byte[] data = (byte[]) msg.obj;
                    UDPDataPackage udpDataPackage = (UDPDataPackage) ConvertData.ByteToObject(data);
                    //if (!receivedIds.contains(udpDataPackage.getId())) {
                        Toast.makeText(MainActivity.this, "Received", Toast.LENGTH_SHORT).show();
                        receivedIds.add(udpDataPackage.getId());
                        textViewTitle.setText(udpDataPackage.getTitle());
                        textViewShowMac.setText(udpDataPackage.getMacAddress());
                        textViewShowIP.setText(udpDataPackage.getIpAddress());
                        testIP = udpDataPackage.getIpAddress();
                    //}
                    break;
                case SingleUtil.SINGLE_PORT:
                    DataPackageInSingle data1=(DataPackageInSingle) ConvertData.ByteToObject((byte[]) msg.obj);
                    imageView.setImageBitmap(data1.getBitmap());
                    Toast.makeText(MainActivity.this,"soudao",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getApplicationContext();
        receivedIds = new ArrayList<>();
        mbuttonSend = (Button) findViewById(R.id.send_msg);
        mbuttonSend.setOnClickListener(this);
        mButtonJoin = (Button) findViewById(R.id.join_group);
        mButtonJoin.setOnClickListener(this);
        mButtonSendSingle = (Button) findViewById(R.id.send_single);
        mButtonSendSingle.setOnClickListener(this);
        button = (Button) findViewById(R.id.select_img);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });
        editText = (EditText) findViewById(R.id.edit_msg);
        textViewShowIP = (TextView) findViewById(R.id.show_ip);
        textViewShowMac = (TextView) findViewById(R.id.show_mac);
        textViewShowMac.setText(GetNetworkInfo.getMac());
        textViewTitle = (TextView) findViewById(R.id.show_title);
        comUtil = new ComUtil(handler);
        dataPackage = new DataPackage();
        singleUtil = new SingleUtil(handler);
        imageView=(ImageView)findViewById(R.id.showImage);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_msg:
                String str = "Test Title";
                dataPackage.setTitle(str);
                dataPackage.setIpAddress(GetNetworkInfo.getIp(this));
                dataPackage.setMacAddress(GetNetworkInfo.getMac());
                dataPackage.setId(1);
                UDPDataPackage udpDataPackage = new UDPDataPackage(dataPackage);
                byte[] data = ConvertData.ObjectToByte(udpDataPackage);
                Log.d(TAG, String.valueOf(data));
                for (int i = 0; i < 3; i++)
                    comUtil.broadCast(data);
                break;
            case R.id.join_group:
                comUtil.startRecieveMsg();
                singleUtil.startRecieveMsg();
                break;
            case R.id.send_single:
                singleUtil.sendSingle1(testIP,"1",GetNetworkInfo.getIp(MainActivity.this));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = data.getData();
        Log.d(TAG, "onActivityResult: "+uri.toString());
        ContentResolver contentResolver = this.getContentResolver();
        try {
            bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
