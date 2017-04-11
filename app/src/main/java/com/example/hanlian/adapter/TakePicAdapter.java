package com.example.hanlian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hanlian.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TakePicAdapter extends RecyclerView.Adapter<TakePicAdapter.TakePicViewHolder> {

    private Context context;
    private ArrayList<String> items = new ArrayList<>();    //存放选择的照片的地址
    private int maxCount = 3;                               //最多能选择几张照片,默认三张
    private OnItemClickListener l;

    public TakePicAdapter(Context context) {
        this.context = context;
    }

    //设置最多能选择几张照片
    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    //设置点击事件监听
    public void setItemClickListener(OnItemClickListener listener) {
        this.l = listener;
    }

    //添加图片
    public void setItem(String originPath) {
        items.add(originPath);
        notifyDataSetChanged();
    }

    //更换图片
    public void updateItem(int position, String originPath) {
        if (position < items.size()) {
            items.set(position, originPath);
            notifyItemChanged(position);
        } else {
            Toast.makeText(context, "数组越界", Toast.LENGTH_SHORT).show();
        }
    }

    //返回
    public ArrayList<String> getItems() {
        return items;
    }

    @Override
    public TakePicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_take_pic, parent, false);
        return new TakePicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TakePicViewHolder holder, final int position) {
        if (position >= items.size()) {
            holder.imageView.setImageResource(R.mipmap.icon_addpic_focused);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (l != null) {
                        //添加照片
                        l.itemClick2Add();
                    }
                }
            });
        } else {
            Glide.with(context).load(items.get(position)).into(holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (l != null) {
                        // 更换图片
                        l.itemClick2Change(position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (items.size() < maxCount) {
            return items.size() + 1;
        } else {
            return maxCount;
        }
    }

    public interface OnItemClickListener {
        void itemClick2Add();

        void itemClick2Change(int position);
    }

    public class TakePicViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_take_pic)
        ImageView imageView;

        public TakePicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}