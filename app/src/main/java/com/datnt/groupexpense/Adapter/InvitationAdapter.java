package com.datnt.groupexpense.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.datnt.groupexpense.Acitivity.ViewInvitationsActivity;
import com.datnt.groupexpense.Api.ApiUtil;
import com.datnt.groupexpense.Api.InvitationClient;
import com.datnt.groupexpense.ModelRetrofit.GroupResult;
import com.datnt.groupexpense.ModelRetrofit.InvitationResult;
import com.datnt.groupexpense.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvitationAdapter extends BaseAdapter {
    InvitationClient invitationClient = ApiUtil.invitationClient();
    private List<InvitationResult> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    public InvitationAdapter(Context aContext,  List<InvitationResult> listData) {
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
            convertView = layoutInflater.inflate(R.layout.list_item_invitations, null);
            holder = new ViewHolder();
            holder.invitationTextView = convertView.findViewById(R.id.tv_inviteText);
            holder.btnAccept = convertView.findViewById(R.id.btn_accept);
            holder.btnDeny = convertView.findViewById(R.id.btn_deny);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final InvitationResult invitationResult = this.listData.get(position);
        holder.invitationTextView.setText(Html.fromHtml("<b>"+invitationResult.getUser()+"</b> đã mời bạn vào nhóm <b>"+invitationResult.getGroup()+"</b>."));
        final int invitationId = invitationResult.getId();
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<ResponseBody> call = invitationClient.checkInvitations(invitationId, true);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Bạn đã đồng ý tham gia nhóm: "+ invitationResult.getGroup(), Toast.LENGTH_SHORT).show();
                            if (context instanceof ViewInvitationsActivity) {
                                ((ViewInvitationsActivity)context).getInvitations();
                            }
                        }
                        else {
                            Toast.makeText(context, "Không thể tham gia nhóm: "+ invitationResult.getGroup(), Toast.LENGTH_SHORT).show();
                            if (context instanceof ViewInvitationsActivity) {
                                ((ViewInvitationsActivity)context).getInvitations();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "Không thể kết nối đến server.", Toast.LENGTH_SHORT).show();
                    }
                });

                //

            }
        });
        holder.btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = invitationClient.checkInvitations(invitationId, false);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Bạn đã từ chối tham gia nhóm: "+ invitationResult.getGroup(), Toast.LENGTH_SHORT).show();
                            if (context instanceof ViewInvitationsActivity) {
                                ((ViewInvitationsActivity)context).getInvitations();
                            }
                        }
                        else {
                            Toast.makeText(context, "Không thể từ chối tham gia nhóm: "+ invitationResult.getGroup(), Toast.LENGTH_SHORT).show();
                            if (context instanceof ViewInvitationsActivity) {
                                ((ViewInvitationsActivity)context).getInvitations();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "Không thể kết nối đến server.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return convertView;
    }
    static class ViewHolder {
        TextView invitationTextView;
        Button btnAccept, btnDeny;
    }
}
