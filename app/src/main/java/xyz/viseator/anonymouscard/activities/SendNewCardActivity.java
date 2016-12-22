package xyz.viseator.anonymouscard.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.viseator.anonymouscard.R;

public class SendNewCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new_card);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.send_float_button)
    public void sendCard() {
        Toast.makeText(this, "Send!", Toast.LENGTH_SHORT).show();
    }
}
