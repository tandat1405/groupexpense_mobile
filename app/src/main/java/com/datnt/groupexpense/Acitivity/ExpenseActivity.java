package com.datnt.groupexpense.Acitivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.datnt.groupexpense.Adapter.GroupAdapter;
import com.datnt.groupexpense.Api.ApiUtil;
import com.datnt.groupexpense.Api.UserClient;
import com.datnt.groupexpense.Model.Group;
import com.datnt.groupexpense.ModelRetrofit.GroupResult;
import com.datnt.groupexpense.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseActivity extends AppCompatActivity {
    ListView listView;
    List<Group> groupList;
    GroupAdapter adapter;
    ImageView iv_logout;
    RelativeLayout rl_viewInvitation;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        //action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));

        View view = getSupportActionBar().getCustomView();
        rl_viewInvitation = view.findViewById(R.id.iv_invitation);
        iv_logout = view.findViewById(R.id.iv_logout);
        rl_viewInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpenseActivity.this,ViewInvitationsActivity.class));
            }
        });
        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(ExpenseActivity.this).create();
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
                        startActivity(new Intent(ExpenseActivity.this, MainActivity.class));
                    }
                });

                alertDialog.show();
            }
        });
        //end action bar



        //declare
        listView = findViewById(R.id.lv_my_group);
        getGroupList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = getSharedPreferences("Expense", MODE_PRIVATE).edit();
                editor.putInt("groupId", groupResultList.get(position).getIdGroup());
                editor.apply();
                Intent intent = new Intent(ExpenseActivity.this, ExpandManagementAcitivity.class);
                intent.putExtra("groupName",groupResultList.get(position).getNameGroup());
                startActivity(intent);
            }
        });



    }
    private static int CREATE_GROUP_REQ_CODE = 101;
    public void clickToCreateNewGroup(View view) {
        startActivityForResult(new Intent(this, CreateGroupActivity.class), CREATE_GROUP_REQ_CODE);

    }
    private List<GroupResult> groupResultList;
    public void getGroupList(){
        groupResultList = new ArrayList<>();
        SharedPreferences prefs = getSharedPreferences("Expense", MODE_PRIVATE);
        userId = prefs.getInt("UserId", 0);
        UserClient userClient = ApiUtil.userClient();
        Call<List<GroupResult>> call = userClient.getMyGroup(userId);
        call.enqueue(new Callback<List<GroupResult>>() {
            @Override
            public void onResponse(Call<List<GroupResult>> call, Response<List<GroupResult>> response) {
                if (response.isSuccessful()) {

                    groupResultList = response.body();
                    adapter = new GroupAdapter(ExpenseActivity.this, groupResultList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(ExpenseActivity.this, "Không thể lấy danh sách nhóm.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GroupResult>> call, Throwable t) {
                Toast.makeText(ExpenseActivity.this, "Không thể kết nối server.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CREATE_GROUP_REQ_CODE){
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "Tạo nhóm: "+data.getStringExtra("groupName")+" thành công.", Toast.LENGTH_SHORT).show();
                //groupList.add(new Group(1,data.getStringExtra("groupName"),"datnt"));
                //adapter.notifyDataSetChanged();
                //listView.setAdapter(adapter);
                getGroupList();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getGroupList();
    }
}
