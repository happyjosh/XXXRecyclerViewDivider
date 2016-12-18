package com.jph.xxxrecyclerviewdivider.sample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by jph on 2016/12/15.
 */
public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.showViewContent(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return 83;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTxt;

        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.divider_item_h, parent, false));
            mTxt = (TextView) itemView.findViewById(R.id.item_txt);
        }

        public void showViewContent(String txt) {
            mTxt.setText(txt);
        }
    }
}
