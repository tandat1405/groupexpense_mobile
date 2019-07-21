package com.datnt.groupexpense.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.datnt.groupexpense.Model.Group;
import com.datnt.groupexpense.ModelRetrofit.GroupResult;
import com.datnt.groupexpense.R;

import java.util.List;

public class GroupAdapter extends BaseAdapter {
    private List<GroupResult> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public GroupAdapter(Context aContext,  List<GroupResult> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_my_group, null);
            holder = new ViewHolder();
            holder.groupName = convertView.findViewById(R.id.tv_list_item_group_name);
            holder.imgOwner = convertView.findViewById(R.id.iv_list_item_owner);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GroupResult groupResult = this.listData.get(position);
        holder.groupName.setText(groupResult.getNameGroup());
        SharedPreferences prefs = context.getSharedPreferences("Expense", Context.MODE_PRIVATE);
        int userId = prefs.getInt("UserId", 0);
        if(userId == groupResult.getOwnerGroupResult().getId()){
            holder.imgOwner.setImageResource(R.drawable.crowns);
        }

        return convertView;
    }
    static class ViewHolder {
        TextView groupName;
        ImageView imgOwner;
    }
}
