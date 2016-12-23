package xyz.viseator.anonymouscard.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import xyz.viseator.anonymouscard.R;
import xyz.viseator.anonymouscard.data.ConvertData;
import xyz.viseator.anonymouscard.data.DataPackage;
import xyz.viseator.anonymouscard.data.UDPDataPackage;
import xyz.viseator.anonymouscard.network.GetNetworkInfo;
import xyz.viseator.anonymouscard.network.SingleUtil;

public class CardDetailActivity extends AppCompatActivity {
    private static final String TAG = "wudi CardDetail";
    private SingleUtil singleUtil;
    private DataPackage dataPackage;
    private UDPDataPackage receivedDataPackage;
    @BindView(R.id.detail_content)
    TextView content;
    @BindView(R.id.detail_image)
    ImageView imageView;
    @BindView(R.id.detail_title)
    TextView title;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SingleUtil.SINGLE_PORT) {
                Log.d(TAG, "handleMessage: Receive Data");
                dataPackage = (DataPackage) msg.obj;
//                content.setText(dataPackage.getContent());
//                title.setText(dataPackage.getTitle());
                if (dataPackage.getBitmap() == null) Log.d(TAG, "handleMessage: Error bitmap");
                imageView.setImageBitmap(ConvertData.byteToBitmap(dataPackage.getBitmap() ));
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        init();
        SingleUtil singleUtil = new SingleUtil();
        receivedDataPackage = (UDPDataPackage) (getIntent().getSerializableExtra("data"));
        singleUtil.sendSingle1(receivedDataPackage.getIpAddress(),
                receivedDataPackage.getId(), GetNetworkInfo.getIp(this));

    }


    private void init() {
        singleUtil = new SingleUtil(handler);
        singleUtil.startRecieveMsgForBack();
    }

}
