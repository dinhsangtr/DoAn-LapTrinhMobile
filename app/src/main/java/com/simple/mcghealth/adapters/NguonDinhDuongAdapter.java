package com.simple.mcghealth.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simple.mcghealth.R;
import com.simple.mcghealth.entities.NguonDinhDuong;

import java.util.List;

public class NguonDinhDuongAdapter extends BaseAdapter {
    private List<NguonDinhDuong> NDDList;
    private Context context;
    private int layout;

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public NguonDinhDuongAdapter(Context context2, int i, List<NguonDinhDuong> list) {
        this.context = context2;
        this.layout = i;
        this.NDDList = list;
    }

    public int getCount() {
        return this.NDDList.size();
    }

    public class ViewHolder {
        ImageView imgHinh;
        TextView txtTieuDe;

        public ViewHolder() {
        }
    }

    @SuppressLint("WrongConstant")
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(this.layout, (ViewGroup) null);
            viewHolder.imgHinh = (ImageView) view2.findViewById(R.id.imageviewanhndd);
            viewHolder.txtTieuDe = (TextView) view2.findViewById(R.id.textviewtieudendd);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        NguonDinhDuong nguonDinhDuong = this.NDDList.get(i);
        viewHolder.txtTieuDe.setText(nguonDinhDuong.getTieude());
        viewHolder.imgHinh.setImageResource(nguonDinhDuong.getAnh());
        return view2;
    }
}
