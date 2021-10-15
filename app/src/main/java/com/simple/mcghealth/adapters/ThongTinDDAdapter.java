package com.simple.mcghealth.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.simple.mcghealth.R;

import java.util.HashMap;
import java.util.List;

public class ThongTinDDAdapter extends BaseExpandableListAdapter {
    Context context;
    List<String> listHeader;
    HashMap<String,List<String>> listChild;

    public ThongTinDDAdapter(Context context, List<String> listHeader, HashMap<String, List<String>> listChild) {
        this.context = context;
        this.listHeader = listHeader;
        this.listChild = listChild;
    }

    @Override
    public int getGroupCount() {
        return listHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChild.get(listHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(listHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View converview, ViewGroup viewGroup) {
        String headerTitle = (String)  getGroup(groupPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        converview = inflater.inflate(R.layout.group_view_ttdd,null);
        TextView txtheader = (TextView) converview.findViewById(R.id.textviewheader);
        txtheader.setText(headerTitle);

        return converview;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View converview, ViewGroup viewGroup) {
        String item = (String)  getChild(groupPosition,childPosition);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        converview = inflater.inflate(R.layout.child_view_ttdd,null);
        TextView txtItem = (TextView) converview.findViewById(R.id.textviewchild);
        txtItem.setText(item);

        return converview;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true ;
    }

}
