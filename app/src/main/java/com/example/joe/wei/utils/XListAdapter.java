package com.example.joe.wei.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joe.wei.R;

import java.util.List;

/**
 * Created by Joe on 2016/11/3.
 */

public class XListAdapter extends ArrayAdapter<XListUtil> {
    private int resourceId;
    public XListAdapter(Context context, int textViewResourceId, List<XListUtil> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        XListUtil xListUtil = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        ImageView shopImage = (ImageView) view.findViewById(R.id.shop_list_image);
        TextView shopTitle = (TextView)view.findViewById(R.id.shop_list_title);
        TextView shopText = (TextView)view.findViewById(R.id.shop_list_text);
        TextView shopPrice = (TextView)view.findViewById(R.id.shop_list_price);
        shopImage.setImageResource(xListUtil.getXlist_imageId());
        shopTitle.setText(xListUtil.getXlist_title());
        shopText.setText(xListUtil.getXlist_text());
        shopPrice.setText(xListUtil.getXlist_price());
        return view;
    }
}
