package com.datnt.groupexpense.Acitivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.datnt.groupexpense.Api.ApiUtil;
import com.datnt.groupexpense.Api.UserClient;
import com.datnt.groupexpense.ModelRetrofit.LoginResult;
import com.datnt.groupexpense.ModelRetrofit.RegisterResult;
import com.datnt.groupexpense.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private PopupWindow pw;
    private View ll_login;
    private EditText register_username, register_password, register_password_reenter, login_username, login_password;
    private Button btn_register;
    private ApiUtil apiUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ll_login = findViewById(R.id.ll_login);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.register_popup, null);
        pw = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        pw.setAnimationStyle(R.style.popup_window_animation_phone);
        //login declare
        login_username = findViewById(R.id.tv_username);
        login_password = findViewById(R.id.tv_password);

        //register declare
        register_username = popupView.findViewById(R.id.edt_register_username);
        register_password = popupView.findViewById(R.id.edt_register_password);
        register_password_reenter = popupView.findViewById(R.id.edt_register_password_reenter);
        btn_register = popupView.findViewById(R.id.btn_register);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = register_username.getText().toString();
                String password = register_password.getText().toString();
                String reenterpassword = register_password_reenter.getText().toString();
                if(password.length()<6){
                    createAlertDialog("Mật khẩu phải dài hơn 6 kí tự.");
                    return;
                }
                if (!password.equals(reenterpassword)) {
                    createAlertDialog("Mật khẩu và mật khẩu nhập lại không giống nhau.");
                    return;
                } else {
                    UserClient userClient = ApiUtil.userClient();
                    Call<ResponseBody> call = userClient.createAccount(username, password);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {

                                pw.dismiss();
                                Toast.makeText(MainActivity.this, "Tạo tài khoản thành công, bây giờ bạn có thể đăng nhập.", Toast.LENGTH_SHORT).show();
                            } else {

                                pw.dismiss();
                                Toast.makeText(MainActivity.this, "Tạo tài khoản thất bại.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            pw.dismiss();
                            Toast.makeText(MainActivity.this, "Tạo tài khoản thất bại.", Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });


    }


    public void clickToLogin(View view) {

        String username = login_username.getText().toString();
        String password = login_password.getText().toString();


        if (username.length() < 1) {
            createAlertDialog("Hãy nhập tên đăng nhập.");
            return;
        }
        if (password.length() < 1) {
            createAlertDialog("Hãy nhập mật khẩu.");
            return;
        }
        UserClient userClient = ApiUtil.userClient();
        Call<LoginResult> call = userClient.login(username, password);
        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful()) {
                    //save ip server to shared pref
                    SharedPreferences.Editor editor = getSharedPreferences("Expense", MODE_PRIVATE).edit();
                    editor.putInt("UserId", response.body().getId());
                    editor.putString("UserName", response.body().getUsername().toString());
                    editor.apply();
                    startActivity(new Intent(MainActivity.this, ExpenseActivity.class));
                } else {
                    createAlertDialog("Sai tên đăng nhập hoặc mật khẩu.");

                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                createAlertDialog("Sai tên đăng nhập hoặc mật khẩu.");
            }
        });


    }

    public void clickToCreateAccount(View view) {
        pw.showAtLocation(ll_login, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    AlertDialog alertDialog;

    public void createAlertDialog(String mes) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Lỗi")
                .setMessage(mes)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                })
                .setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();
    }
}
