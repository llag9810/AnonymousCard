package xyz.viseator.anonymouscard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import xyz.viseator.anonymouscard.R;
import xyz.viseator.anonymouscard.data.UDPDataPackage;

/**
 * Created by viseator on 2016/12/20.
 * Wudi
 * viseator@gmail.com
 */

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ItemViewHolder>
        implements View.OnClickListener {
    private Context context;
    private List<UDPDataPackage> lists;
    public OnItemClickListener mListener;

    public MainRecyclerViewAdapter(Context context, List<UDPDataPackage> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_recyclerview_content, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        UDPDataPackage item = lists.get(position);
        holder.title.setText(item.getTitle());
        holder.itemView.setTag(item.getId());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onItemClickListener(view, (String) view.getTag());
        }
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, source;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.content_logo);
            title = (TextView) itemView.findViewById(R.id.content_title);
            source = (TextView) itemView.findViewById(R.id.content_source);
        }
    }

    public static interface OnItemClickListener {
        public void onItemClickListener(View view, String id);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
