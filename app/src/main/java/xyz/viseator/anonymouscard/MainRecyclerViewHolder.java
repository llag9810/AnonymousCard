package xyz.viseator.anonymouscard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by viseator on 2016/12/20.
 * Wudi
 * viseator@gmail.com
 */

public class MainRecyclerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.content_title)
    TextView textView;

    public MainRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
