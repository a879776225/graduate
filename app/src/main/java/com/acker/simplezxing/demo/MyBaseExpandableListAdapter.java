package com.acker.simplezxing.demo;

import android.content.ClipData;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {
    private ArrayList<Group> gData;
    private ArrayList<ArrayList<Truck>> iData;
    private Context mContext;

    public MyBaseExpandableListAdapter(ArrayList<Group> gData, ArrayList<ArrayList<Truck>> iData, Context mContext) {
        this.gData = gData;
        this.iData = iData;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return gData.size();
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return iData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return iData.get(groupPosition).get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup groupHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.parent, parent, false);
            groupHolder = new ViewHolderGroup();
            groupHolder.tv_group_name = (TextView) convertView.findViewById(R.id.Groupp);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (ViewHolderGroup) convertView.getTag();
        }
        groupHolder.tv_group_name.setText(gData.get(groupPosition).getgName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolderItem itemHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.son, parent, false);
            itemHolder = new ViewHolderItem();
            itemHolder.start = (TextView) convertView.findViewById(R.id.start1);
            itemHolder.whither = (TextView) convertView.findViewById(R.id.whither);
            itemHolder.time = convertView.findViewById(R.id.stime);
            itemHolder.typename = convertView.findViewById(R.id.textView5);
            convertView.setTag(itemHolder);
        }else{
            itemHolder = (ViewHolderItem) convertView.getTag();
        }
        itemHolder.start.setText(iData.get(groupPosition).get(childPosition).getStart());
        itemHolder.whither.setText(iData.get(groupPosition).get(childPosition).getWhither());
        itemHolder.time.setText(iData.get(groupPosition).get(childPosition).getCargoTruckDate());
        itemHolder.typename.setText(iData.get(groupPosition).get(childPosition).getCargoNameTruckType());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    private static class ViewHolderGroup{
        private TextView tv_group_name;
    }

    private static class ViewHolderItem{
        private TextView start;
        private TextView whither;
        private TextView time;
        private TextView typename;
    }
}
