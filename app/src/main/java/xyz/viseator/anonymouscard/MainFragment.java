package xyz.viseator.anonymouscard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by viseator on 2016/12/20.
 * Wudi
 * viseator@gmail.com
 */

public class MainFragment extends Fragment {
    @BindView(R.id.test_text)
    TextView textView;
    String name = " ";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fregment, container, false);
        ButterKnife.bind(this, view);
        textView.setText(getName());
        return view;
    }
}
