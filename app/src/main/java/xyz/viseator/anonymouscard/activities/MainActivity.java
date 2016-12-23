package xyz.viseator.anonymouscard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.viseator.anonymouscard.R;
import xyz.viseator.anonymouscard.adapter.ViewPagerAdapter;
import xyz.viseator.anonymouscard.data.ConvertData;
import xyz.viseator.anonymouscard.data.DataPackage;
import xyz.viseator.anonymouscard.data.DataStore;
import xyz.viseator.anonymouscard.data.UDPDataPackage;
import xyz.viseator.anonymouscard.network.ComUtil;
import xyz.viseator.anonymouscard.network.SingleUtil;
import xyz.viseator.anonymouscard.ui.MainFragment;

public class MainActivity extends FragmentActivity {
    private static final int SEND_CARD = 1;
    private static final String TAG = "wudi MainActivity";
    private int cardId = 0;
    private MainFragment mainFragment, mainFragment1, mainFragment2;
    private List<Fragment> fragments;
    private ViewPagerAdapter viewPagerAdapter;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private ArrayList<DataPackage> dataPackages;
    private ArrayList<UDPDataPackage> udpDataPackages;
    private ComUtil comUtil;
    private SingleUtil singleUtil;
    private DataStore dataStore;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ComUtil.BROADCAST_PORT:
                    UDPDataPackage udpDataPackage = (UDPDataPackage) ConvertData.byteToObject((byte[]) msg.obj);
                    udpDataPackages.add(udpDataPackage);
                    dataStore.setDataPackages(dataPackages);
                    mainFragment.recyclerView.getAdapter().notifyDataSetChanged();
                    Log.d(TAG, "handleMessage: Receive UDP");
                    break;
                case SingleUtil.SINGLE_PORT:
                    DataPackage dataPackage = (DataPackage) msg.obj;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        dataPackages = new ArrayList<>();
        udpDataPackages = new ArrayList<>();
        dataStore = new DataStore();
        init();
        initViews();
    }

    private void initViews() {
        fragments = new ArrayList<>();
        mainFragment = new MainFragment();
        mainFragment.setName("主页");
        mainFragment1 = new MainFragment();
        mainFragment1.setName("次页");
        mainFragment2 = new MainFragment();
        mainFragment2.setName("三页");
        fragments.add(mainFragment);
        fragments.add(mainFragment1);
        fragments.add(mainFragment2);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), MainActivity.this, fragments);
        viewPager.setAdapter(viewPagerAdapter);
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
                    comUtil.broadCast(ConvertData.objectToByte(new UDPDataPackage(dataPackage)));
                    dataPackages.add(dataPackage);
                    udpDataPackages.add(new UDPDataPackage(dataPackage));
                    dataStore.setDataPackages(dataPackages);
                    cardId++;
                }
            }
        }
    }


    public UDPDataPackage getDataById(String id) {
        for (UDPDataPackage udpDataPackage : udpDataPackages) {
            if (Objects.equals(udpDataPackage.getId(), id)) {
                Log.d(TAG, "getDataById: found data");
                return udpDataPackage;
            }
        }
        return null;
    }


    public ArrayList<UDPDataPackage> getUdpDataPackages() {
        return udpDataPackages;
    }

    public ArrayList<DataPackage> getDataPackages() {
        return dataPackages;
    }

    private void init() {
        comUtil = new ComUtil(handler);
        comUtil.startRecieveMsg();
        singleUtil = new SingleUtil(handler, dataStore);
        singleUtil.startRecieveMsg();
    }


}
