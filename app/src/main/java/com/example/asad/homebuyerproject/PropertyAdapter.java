package com.example.asad.homebuyerproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import android.view.LayoutInflater;

import android.view.ViewGroup;


import java.util.List;
import org.w3c.dom.Text;

/**
 * Created by hassan on 05/01/2017.
 */

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.MyViewHolder>  {
    private List<Property> mPropertyList;
    private Context c;
    private int placeholder;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mPropertyPrice,mPropertyDealer,mPropertyType,mPropertyAddress;
        public ImageView mPropertyImage;
        public MyViewHolder(View view) {
            super(view);
            placeholder = R.drawable.placeholder;
            mPropertyPrice = (TextView) view.findViewById(R.id.mPPrice);
            mPropertyAddress = (TextView) view.findViewById(R.id.mPLocation);
            mPropertyDealer = (TextView) view.findViewById(R.id.mDtype);
            mPropertyType = (TextView) view.findViewById(R.id.mStype);
            mPropertyImage = (ImageView)view.findViewById(R.id.image);
        }
    }
    public PropertyAdapter(List<Property> propertyList) {
        if (this.mPropertyList != null)
        clear();

        this.mPropertyList = propertyList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.propertylist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Property mProperty = mPropertyList.get(position);
        holder.mPropertyPrice.setText(mProperty.getPrice());
        PicassoClient.downloadImage(c,mProperty.getImage(),holder.mPropertyImage,placeholder,0.1f);
        holder.mPropertyAddress.setText(mProperty.getAddress());
        holder.mPropertyDealer.setText(mProperty.getDealer());
        holder.mPropertyType.setText(mProperty.getSelltype());


    }
    public void clear() {
       /* int size = this.mPropertyList.size();
        this.mPropertyList.clear();
        notifyItemRangeRemoved(0, size);
        notifyItemRangeChanged(0,size);
*/
    }
    @Override
    public int getItemCount() {
        return mPropertyList.size();
    }
}
