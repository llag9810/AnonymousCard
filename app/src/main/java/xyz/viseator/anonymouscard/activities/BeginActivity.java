package xyz.viseator.anonymouscard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import xyz.viseator.anonymouscard.R;

/**
 * Created by yanhao on 16-12-24.
 */

public class BeginActivity extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_begin);
        button=(Button)findViewById(R.id.begin);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(v.getId()==R.id.begin){
                    Intent intent=new Intent(BeginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
