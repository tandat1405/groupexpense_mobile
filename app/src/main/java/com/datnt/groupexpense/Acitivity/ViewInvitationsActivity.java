package com.datnt.groupexpense.Acitivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.datnt.groupexpense.Adapter.InvitationAdapter;
import com.datnt.groupexpense.Api.ApiUtil;
import com.datnt.groupexpense.Api.InvitationClient;
import com.datnt.groupexpense.ModelRetrofit.InvitationResult;
import com.datnt.groupexpense.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewInvitationsActivity extends AppCompatActivity {
    ImageView iv_logout;
    RelativeLayout rl_viewInvitation;
    int userId;
    ListView lv_Invitations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_invitations);
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
                AlertDialog alertDialog = new AlertDialog.Builder(ViewInvitationsActivity.this).create();
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
                        startActivity(new Intent(ViewInvitationsActivity.this, MainActivity.class));
                    }
                });

                alertDialog.show();
            }
        });
        //end action bar
        //
        lv_Invitations = findViewById(R.id.lv_invitations);
        getInvitations();




    }
    public void getInvitations(){
        SharedPreferences prefs = getSharedPreferences("Expense", MODE_PRIVATE);
        userId = prefs.getInt("UserId", 0);
        InvitationClient invitationClient = ApiUtil.invitationClient();
        Call<List<InvitationResult>> call = invitationClient.getListInvitation(userId);
        call.enqueue(new Callback<List<InvitationResult>>() {
            @Override
            public void onResponse(Call<List<InvitationResult>> call, Response<List<InvitationResult>> response) {
                if (response.isSuccessful()) {
                    InvitationAdapter aa = new InvitationAdapter(ViewInvitationsActivity.this,response.body());
                    lv_Invitations.setAdapter(aa);
                    aa.notifyDataSetChanged();
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<List<InvitationResult>> call, Throwable t) {

            }
        });
    }
}
