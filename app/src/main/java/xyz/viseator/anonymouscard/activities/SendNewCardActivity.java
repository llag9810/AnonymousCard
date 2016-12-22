package xyz.viseator.anonymouscard.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.viseator.anonymouscard.R;
import xyz.viseator.anonymouscard.data.DataPackage;
import xyz.viseator.anonymouscard.network.GetNetworkInfo;

public class SendNewCardActivity extends AppCompatActivity {

    private static final int GET_IMAGE = 1;
    private static final String TAG = "wudi SendNewCard";
    Bitmap bitmap;
    @BindView(R.id.send_image)
    ImageView imageView;
    @BindView(R.id.send_title)
    EditText cardTitle;
    @BindView(R.id.send_content)
    EditText cardContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new_card);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.send_float_button)
    public void sendCard() {
        DataPackage dataPackage = new DataPackage();
        dataPackage.setMyIp(GetNetworkInfo.getIp(this));
        dataPackage.setMyMac(GetNetworkInfo.getMac());
        dataPackage.setContent(cardContent.getText().toString());
        dataPackage.setTitle(cardTitle.getText().toString());
        dataPackage.setId(getIntent().getIntExtra("cardId", -1));
        // TODO: 2016/12/22 there
    }

    @OnClick(R.id.send_image)
    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, GET_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.d(TAG, "onActivityResult: " + uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.d(TAG, "onActivityResult: error");
            }

        }
    }
}
