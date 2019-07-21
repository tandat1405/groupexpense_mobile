package com.datnt.groupexpense.Acitivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.datnt.groupexpense.Acitivity.ui.main.SectionsPagerAdapter;
import com.datnt.groupexpense.R;

public class ExpandManagementAcitivity extends AppCompatActivity {
    ImageView iv_logout;
    RelativeLayout rl_viewInvitation;
    TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_management_acitivity);
        Intent intent = getIntent();
        String groupName = intent.getStringExtra("groupName");
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        //FloatingActionButton fab = findViewById(R.id.fab);

        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});

        //action bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        View view = getSupportActionBar().getCustomView();
        rl_viewInvitation = view.findViewById(R.id.iv_invitation);
        iv_logout = view.findViewById(R.id.iv_logout);
        tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(groupName);
        rl_viewInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpandManagementAcitivity.this,ViewInvitationsActivity.class));
            }
        });
        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(ExpandManagementAcitivity.this).create();
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
                        startActivity(new Intent(ExpandManagementAcitivity.this, MainActivity.class));
                    }
                });
                alertDialog.show();
            }
        });
        //end action bar
    }
}