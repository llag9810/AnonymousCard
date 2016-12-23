package xyz.viseator.anonymouscard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.viseator.anonymouscard.R;
import xyz.viseator.anonymouscard.adapter.ViewPagerAdapter;
import xyz.viseator.anonymouscard.data.DataPackage;
import xyz.viseator.anonymouscard.data.UDPDataPackage;

public class MainActivity extends FragmentActivity {
    private static final int SEND_CARD = 1;
    private static final String TAG = "wudi MainActivity";
    private int cardId = 0;

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private ArrayList<DataPackage> dataPackages;
    private ArrayList<UDPDataPackage> udpDataPackages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);

        dataPackages = new ArrayList<>();
        udpDataPackages = new ArrayList<>();

        initViews();
    }

    private void initViews() {
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), this, dataPackages));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        View view1 = getLayoutInflater().inflate(R.layout.tab_view, null);
        ((ImageView) view1.findViewById(R.id.tab_image)).setImageResource(R.drawable.left_icon_selector);
        View view2 = getLayoutInflater().inflate(R.layout.tab_view, null);
        ((ImageView) view2.findViewById(R.id.tab_image)).setImageResource(R.drawable.center_icon_selector);
        View view3 = getLayoutInflater().inflate(R.layout.tab_view, null);
        ((ImageView) view3.findViewById(R.id.tab_image)).setImageResource(R.drawable.right_icon_selector);

        tabLayout.getTabAt(0).setCustomView(view1);
        tabLayout.getTabAt(1).setCustomView(view2);
        tabLayout.getTabAt(2).setCustomView(view3);


    }

    @OnClick(R.id.float_button)
    public void clickFloatButton() {
        Intent intent = new Intent(this, SendNewCardActivity.class);
        intent.putExtra("cardId", cardId);
        startActivityForResult(intent, SEND_CARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEND_CARD) {
            if (resultCode == RESULT_OK) {
                DataPackage dataPackage = (DataPackage) data.getSerializableExtra("data");
                if (dataPackage != null) {
                    Log.d(TAG, "onActivityResult: Got Data");
//                    comUtil.broadCast(ConvertData.objectToByte(new UDPDataPackage(dataPackage)));
                    dataPackages.add(dataPackage);
                    udpDataPackages.add(new UDPDataPackage(dataPackage));
                    cardId++;
                }
            }
        }
    }



    public DataPackage getDataById(String id) {
        for (DataPackage dataPackage : dataPackages) {
            if (Objects.equals(dataPackage.getId(), id)) return dataPackage;
        }
        return null;
    }

    public ArrayList<UDPDataPackage> getUdpDataPackages() {
        return udpDataPackages;
    }
}
