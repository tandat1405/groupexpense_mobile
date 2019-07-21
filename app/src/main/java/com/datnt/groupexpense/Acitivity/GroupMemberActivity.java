package com.datnt.groupexpense.Acitivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.datnt.groupexpense.Adapter.GroupMemberAdapter;
import com.datnt.groupexpense.Api.ApiUtil;
import com.datnt.groupexpense.Api.GroupClient;
import com.datnt.groupexpense.Api.InvitationClient;
import com.datnt.groupexpense.Model.GroupMember;
import com.datnt.groupexpense.ModelRetrofit.MemberResult;
import com.datnt.groupexpense.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupMemberActivity extends AppCompatActivity {
    ImageView iv_logout;
    RelativeLayout rl_viewInvitation;
    ListView lv_listMember;
    List<GroupMember> groupMemberList;
    private EditText edt_MemberName;
    int groupId, userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);
        //action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));

        View view = getSupportActionBar().getCustomView();
        rl_viewInvitation = view.findViewById(R.id.iv_invitation);
        iv_logout = view.findViewById(R.id.iv_logout);
        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(GroupMemberActivity.this).create();
                alertDialog.setTitle("Đăng xuất:");
                alertDialog.setMessage("Bạn có muốn đăng xuất không?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Không",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(GroupMemberActivity.this, MainActivity.class));
                    }
                });

                alertDialog.show();
            }
        });
        //end action bar
        edt_MemberName = findViewById(R.id.edt_member_name);
        lv_listMember = findViewById(R.id.lv_group_member);

        //pref
        SharedPreferences prefs = getSharedPreferences("Expense", Context.MODE_PRIVATE);
        groupId = prefs.getInt("groupId", 0);
        userId = prefs.getInt("UserId",0);
        getMemberInGroup();

        //GroupMemberAdapter adapter = new GroupMemberAdapter(this,groupMemberList);
        //lv_listMember.setAdapter(adapter);


    }
    public void getMemberInGroup(){
        GroupClient groupClient = ApiUtil.groupClient();
        Call<List<MemberResult>> call = groupClient.getListMember(groupId);
        call.enqueue(new Callback<List<MemberResult>>() {
            @Override
            public void onResponse(Call<List<MemberResult>> call, Response<List<MemberResult>> response) {
                if (response.isSuccessful()) {
                    GroupMemberAdapter adapter = new GroupMemberAdapter(GroupMemberActivity.this,response.body());
                    lv_listMember.setAdapter(adapter);
                }
                else {
                    Toast.makeText(GroupMemberActivity.this, "Không thể lấy danh sách thành viên.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MemberResult>> call, Throwable t) {
                Toast.makeText(GroupMemberActivity.this, "Không thể kết nối tới server.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    InvitationClient invitationClient = ApiUtil.invitationClient();
    public void clickToAddMember(View view) {
        final String memberName = edt_MemberName.getText().toString();
        if(memberName.trim().length()<1){
            Toast.makeText(this, "Xin nhập tài khoản người bạn muốn mời.", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<ResponseBody> call = invitationClient.invite(groupId,userId,memberName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(GroupMemberActivity.this, "Bạn đã mời thành công user: "+memberName, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(GroupMemberActivity.this, "Tên người dùng:"+memberName +" không khả dụng.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(GroupMemberActivity.this, "Không thể kết nối tới máy chủ.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
