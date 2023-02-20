package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AdapterCV extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<CongViec> congViecList;
    public AdapterCV(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }
    @Override
    public int getCount() {
        return congViecList.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHoder{
          TextView txtName;
          ImageView imgUpdate, imgDelete;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        if (convertView == null){
            viewHoder = new ViewHoder();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(layout, null);
            viewHoder.txtName = (TextView) convertView.findViewById(R.id.textViewName);
            viewHoder.imgUpdate = (ImageView) convertView.findViewById(R.id.imageViewUpdate);
            viewHoder.imgDelete = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            convertView.setTag(viewHoder);
        }else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        CongViec congViec = congViecList.get(position);
        viewHoder.txtName.setText(congViec.getTenCongViec());

        viewHoder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 context.DialogSuaCV(congViec.getTenCongViec(), congViec.getId());
            }
        });
        viewHoder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.dialogXoaCv(congViec.getTenCongViec(), congViec.getId());
            }
        });
        return convertView;
    }
}
