package xyz.viseator.anonymouscard;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import xyz.viseator.anonymouscard.network.ComUtil;

public class MainActivity extends AppCompatActivity
                            implements View.OnClickListener{
    private Button mbuttonSend,mButtonJoin;
    private EditText editText;
    private TextView textView;
    private ComUtil comUtil=null;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ComUtil.BROADCAST_PORT:
                    String str=(String)msg.obj;
                    Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
                    textView.setText(str);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbuttonSend=(Button)findViewById(R.id.send_msg);
        mbuttonSend.setOnClickListener(this);
        mButtonJoin=(Button)findViewById(R.id.join_group);
        mButtonJoin.setOnClickListener(this);
        editText=(EditText)findViewById(R.id.edit_msg);
        textView=(TextView)findViewById(R.id.show_rec_content);
        comUtil=new ComUtil(handler);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_msg:
                String str=editText.getText().toString();
                comUtil.broadCast(str);
                break;
            case R.id.join_group:
                comUtil.startRecieveMsg();
                break;
        }
    }
}
