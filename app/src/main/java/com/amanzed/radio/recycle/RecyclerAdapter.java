package com.amanzed.radio.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amanzed.radio.R;
import com.amanzed.radio.MainActivity;
import com.amanzed.radio.list.Radio;
import com.pkmmte.view.CircularImageView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Amanze on 10/16/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Radio> radioList = Collections.emptyList();
    OnItemClickListener mItemClickListener;
    Context ctx;

    public RecyclerAdapter(Context context, List<Radio> data){
        ctx = context;
        inflater = LayoutInflater.from(context);
        this.radioList = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.radio_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Radio current = radioList.get(position);
        holder.name.setText(current.getName().toUpperCase().trim());
        try{
            holder.logo.setImageResource(getImageId("r"+current.getId(), "raw"));
        }catch (Exception e){
            holder.logo.setImageResource(getImageId("ic_radio_64", "drawable"));
            holder.logo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }
    }

    @Override
    public int getItemCount() {
        return radioList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CircularImageView logo;
        TextView name;
        public MyViewHolder(View itemView) {
            super(itemView);
            logo = (CircularImageView)itemView.findViewById(R.id.radio_list_thumb);
            name = (TextView)itemView.findViewById(R.id.radio_list_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public int getImageId(String imageName, String folder) {
        return ctx.getResources().getIdentifier(imageName, folder, ctx.getPackageName());
    }
}
