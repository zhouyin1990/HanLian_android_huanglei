package com.example.hanlian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.help.Tip;
import com.example.hanlian.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchLocationAdapter extends RecyclerView.Adapter<SearchLocationAdapter.LocationViewHolder> {

    private Context context;
    private ArrayList<Tip> models = new ArrayList();
    private OnItemClickListener l;

    public SearchLocationAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        l = listener;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, final int position) {
        final Tip model = models.get(position);
        holder.address.setText(model.getAddress());
        holder.name.setText(model.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l.itemClick(position, model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setData(List<Tip> tips) {
        models.clear();
        models.addAll(tips);
        notifyDataSetChanged();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.address)
        TextView address;

        LocationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void itemClick(int position, Tip model);
    }
}