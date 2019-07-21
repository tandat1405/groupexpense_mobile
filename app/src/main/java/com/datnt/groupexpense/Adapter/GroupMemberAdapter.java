package com.datnt.groupexpense.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.datnt.groupexpense.ModelRetrofit.MemberResult;
import com.datnt.groupexpense.R;

import java.util.List;

public class GroupMemberAdapter extends BaseAdapter {
    private List<MemberResult> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public GroupMemberAdapter(Context aContext,  List<MemberResult> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_item_group_member, null);
            holder = new ViewHolder();
            holder.memberName = convertView.findViewById(R.id.tv_member_name);
            holder.role = convertView.findViewById(R.id.tv_member_role);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MemberResult memberResult = this.listData.get(position);
        holder.memberName.setText(memberResult.getUsername());
        if(memberResult.getRole().equals("admin")){
            holder.role.setText("Admin");
        }
        return convertView;
    }
    static class ViewHolder{
        TextView memberName, role;
    }
}
