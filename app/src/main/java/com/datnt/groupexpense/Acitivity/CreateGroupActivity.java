package com.datnt.groupexpense.Acitivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.datnt.groupexpense.Api.ApiUtil;
import com.datnt.groupexpense.Api.GroupClient;
import com.datnt.groupexpense.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGroupActivity extends AppCompatActivity {
    private TextView tv_groupname;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        //action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));

        View view = getSupportActionBar().getCustomView();

        //
        tv_groupname = findViewById(R.id.tv_create_group_name);
        SharedPreferences prefs = getSharedPreferences("Expense", MODE_PRIVATE);
        userId = prefs.getInt("UserId", 0);
    }

    public void clickToCreate(View view) {
        final String groupName = tv_groupname.getText().toString();
        if(groupName.length()<1){
            Toast.makeText(this, "Xin hãy nhập tên nhóm.", Toast.LENGTH_SHORT).show();
            return;
        }
        GroupClient groupClient = ApiUtil.groupClient();
        Call<ResponseBody> call = groupClient.createGroup(userId, groupName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("groupName", groupName);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
                else {
                    Toast.makeText(CreateGroupActivity.this, "Tạo nhóm thất bại.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(CreateGroupActivity.this, "Không thể kết nối tới server.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
