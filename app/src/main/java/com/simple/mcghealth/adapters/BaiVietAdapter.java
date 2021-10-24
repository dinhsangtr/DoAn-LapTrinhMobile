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
import com.simple.mcghealth.entities.BaiViet;

import java.util.ArrayList;
import java.util.List;

public class BaiVietAdapter extends BaseAdapter {
    private List<com.simple.mcghealth.entities.BaiViet> BVList;
    private Context context;
    private int layout;

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public BaiVietAdapter(Context context2, int i, ArrayList<BaiViet> list) {
        this.context = context2;
        this.layout = i;
        this.BVList = list;
    }

    public int getCount() {
        return this.BVList.size();
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
            viewHolder.imgHinh = (ImageView) view2.findViewById(R.id.imageviewanhbv);
            viewHolder.txtTieuDe = (TextView) view2.findViewById(R.id.textviewtieudebv);
            view2.setTag(viewHolder);
        } else {
            view2 = view;
            viewHolder = (ViewHolder) view.getTag();
        }
        com.simple.mcghealth.entities.BaiViet baiViet = this.BVList.get(i);
        viewHolder.txtTieuDe.setText(baiViet.getTieude());
        viewHolder.imgHinh.setImageResource(baiViet.getAnh());
        return view2;
    }
}
