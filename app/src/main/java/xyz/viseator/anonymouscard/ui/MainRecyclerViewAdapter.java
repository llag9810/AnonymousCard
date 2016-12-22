package xyz.viseator.anonymouscard.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.viseator.anonymouscard.R;

/**
 * Created by viseator on 2016/12/20.
 * Wudi
 * viseator@gmail.com
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewHolder>{
    private Context context;
    private String[] item = {"One", "Two", "Three", "Four"};

    public MainRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MainRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_recyclerview_content,
                parent, false);
        return new MainRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainRecyclerViewHolder holder, int position) {
        holder.textView.setText(item[position]);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
