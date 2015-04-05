package com.Skyrim.Alchemy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 14. 1. 26.
 */
public class ImageListAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater flater;
    private String[] arrayList;
    private FunctionDeclare FD = new FunctionDeclare();

    public ImageListAdapter(Context c,int layoutid, String st[]){
        this.context = c;
        this.flater = (LayoutInflater)c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        this.arrayList = new String[st.length];
        this.arrayList = st;
    }

    @Override
    public int getCount() {
        return arrayList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = flater.inflate(R.layout.imagelist_sub,parent,false);
        }
        TextView tv = (TextView)convertView.findViewById(R.id.TV_listtext);
        tv.setText(arrayList[position]);
        ImageView img = (ImageView)convertView.findViewById(R.id.IV_listimage);
        img.setImageBitmap(FD.getBitmap(context,FD.ReListindex(arrayList[position],0)));

        return convertView;
    }
}
