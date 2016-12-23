package xyz.viseator.anonymouscard.ui;

import android.content.Intent;
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
import xyz.viseator.anonymouscard.activities.CardDetailActivity;
import xyz.viseator.anonymouscard.activities.MainActivity;
import xyz.viseator.anonymouscard.adapter.MainRecyclerViewAdapter;
import xyz.viseator.anonymouscard.data.UDPDataPackage;
import xyz.viseator.anonymouscard.network.ComUtil;

/**
 * Created by viseator on 2016/12/20.
 * Wudi
 * viseator@gmail.com
 */

public class MainFragment extends Fragment  {
    @BindView(R.id.main_recyclerView)
    public RecyclerView recyclerView;
    String name;
    private ArrayList<UDPDataPackage> udpDataPackages;
    private ComUtil comUtil;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fregment, container, false);
        ButterKnife.bind(this, view);
        udpDataPackages = ((MainActivity) getActivity()).getUdpDataPackages();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.hasFixedSize();
        MainRecyclerViewAdapter mainRecyclerViewAdapter = new MainRecyclerViewAdapter(getActivity(), udpDataPackages);
        mainRecyclerViewAdapter.setOnItemClickListener(new MainRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, String id) {
                Intent intent = new Intent(getContext(), CardDetailActivity.class);
                intent.putExtra("data", ((MainActivity) getActivity()).getDataById(id));
                startActivityForResult(intent, 1);
            }
        });
        recyclerView.setAdapter(mainRecyclerViewAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ((MainActivity)getActivity()).getDataPackages().add(data.getSerializableExtra("data"))
    }
}
