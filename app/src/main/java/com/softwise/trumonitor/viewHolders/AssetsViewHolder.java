package com.softwise.trumonitor.viewHolders;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softwise.trumonitor.R;

import androidx.recyclerview.widget.RecyclerView;

public class AssetsViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout layout;
    public TextView txtAssetName;

    public AssetsViewHolder(View view) {
        super(view);
        this.layout = (RelativeLayout) view.findViewById(R.id.list_item);
        this.txtAssetName = (TextView) view.findViewById(R.id.txt_asset_name);
    }
}
