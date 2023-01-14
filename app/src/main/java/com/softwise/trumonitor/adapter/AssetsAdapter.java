package com.softwise.trumonitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.softwise.trumonitor.R;
import com.softwise.trumonitor.models.AssetAndSensorInfo;
import com.softwise.trumonitor.viewHolders.AssetsViewHolder;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AssetsAdapter extends RecyclerView.Adapter<AssetsViewHolder> implements Filterable {
    List<AssetAndSensorInfo> mAssetAndSensorInfo = new ArrayList();
    List<AssetAndSensorInfo> mAssetAndSensorInfoFilter = new ArrayList();
    List<AssetAndSensorInfo> mAssetAndSensorInfoOriginal = new ArrayList();
    Context mContext;
    OnAssetsSelectListeners mOnAssetsSelectListeners;

    public interface OnAssetsSelectListeners {
        void assetsSelect(AssetAndSensorInfo assetAndSensorInfo);
    }

    public AssetsAdapter(Context context, List<AssetAndSensorInfo> list, OnAssetsSelectListeners onAssetsSelectListeners) {
        this.mOnAssetsSelectListeners = onAssetsSelectListeners;
        this.mAssetAndSensorInfoOriginal = list;
        this.mAssetAndSensorInfoFilter = list;
        this.mAssetAndSensorInfo = list;
        this.mContext = context;
    }

    public AssetsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new AssetsViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.assets_item_unit, viewGroup, false));
    }

    public void onBindViewHolder(AssetsViewHolder assetsViewHolder, int i) {
        AssetAndSensorInfo assetAndSensorInfo = this.mAssetAndSensorInfo.get(i);
        assetsViewHolder.txtAssetName.setText(assetAndSensorInfo.getAssetName());
        assetsViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnAssetsSelectListeners.assetsSelect(assetAndSensorInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mAssetAndSensorInfo.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            /* access modifiers changed from: protected */
            @Override
            public FilterResults performFiltering(CharSequence charSequence) {
                String charSequence2 = charSequence.toString();
                if (charSequence2.isEmpty()) {
                    AssetsAdapter assetsAdapter = AssetsAdapter.this;
                    assetsAdapter.mAssetAndSensorInfoFilter = assetsAdapter.mAssetAndSensorInfoOriginal;
                } else {
                    ArrayList arrayList = new ArrayList();
                    for (AssetAndSensorInfo next : AssetsAdapter.this.mAssetAndSensorInfo) {
                        if (next.getAssetName().toLowerCase().contains(charSequence2.toLowerCase())) {
                            arrayList.add(next);
                        }
                    }
                    AssetsAdapter.this.mAssetAndSensorInfoFilter = arrayList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = AssetsAdapter.this.mAssetAndSensorInfoFilter;
                return filterResults;
            }

            /* access modifiers changed from: protected */
            @Override
            public void publishResults(CharSequence charSequence, FilterResults filterResults) {
                AssetsAdapter.this.mAssetAndSensorInfo.clear();
                AssetsAdapter.this.mAssetAndSensorInfo = (ArrayList) filterResults.values;
                AssetsAdapter.this.notifyDataSetChanged();
            }
        };
    }

    public void updateList(List<AssetAndSensorInfo> list) {
        this.mAssetAndSensorInfo.clear();
        this.mAssetAndSensorInfo.addAll(list);
        notifyDataSetChanged();
    }
}
