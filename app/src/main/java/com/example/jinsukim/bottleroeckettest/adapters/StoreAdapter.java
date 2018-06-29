package com.example.jinsukim.bottleroeckettest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jinsukim.bottleroeckettest.R;
import com.example.jinsukim.bottleroeckettest.models.StoreModel;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.MyViewHolder> {
    private Context mContext;
    private List<StoreModel> mStoreList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public TextView address, city, state, phone;

        public MyViewHolder(View view){
            super(view);
            thumbnail = (ImageView) view.findViewById(R.id.iv_thumbnail);
            address = (TextView) view.findViewById(R.id.address);
            city = (TextView) view.findViewById(R.id.city);
            state = (TextView) view.findViewById(R.id.state);
            phone = (TextView) view.findViewById(R.id.phone);
        }
    }

    public StoreAdapter(Context context, List<StoreModel> storeList){
        this.mContext = context;
        this.mStoreList = storeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StoreModel store = mStoreList.get(position);
        holder.address.setText(store.getAddress());
        holder.city.setText(store.getCity());
        holder.state.setText(store.getState());
        holder.phone.setText(store.getPhone());

        //load thumbnail from url
        Glide.with(mContext).load(store.getLogoURL()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return mStoreList.size();
    }

}

