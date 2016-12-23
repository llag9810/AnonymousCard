package xyz.viseator.anonymouscard.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.viseator.anonymouscard.R;
import xyz.viseator.anonymouscard.adapter.MainRecyclerViewAdapter;
import xyz.viseator.anonymouscard.data.DataPackage;
import xyz.viseator.anonymouscard.data.UDPDataPackage;

/**
 * Created by viseator on 2016/12/20.
 * Wudi
 * viseator@gmail.com
 */

public class MainFragment extends Fragment {
    @BindView(R.id.main_recyclerView)
    RecyclerView recyclerView;
    String name;
    private ArrayList<UDPDataPackage> udpDataPackages;

    public void setDataPackages(ArrayList<DataPackage> dataPackages) {
        udpDataPackages = new ArrayList<>();
        for (DataPackage dataPackage : dataPackages) {
            udpDataPackages.add(new UDPDataPackage(dataPackage));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fregment, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(new MainRecyclerViewAdapter(getActivity(), udpDataPackages));
        return view;
    }
}
