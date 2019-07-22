package com.datnt.groupexpense.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.datnt.groupexpense.Acitivity.GroupMemberActivity;
import com.datnt.groupexpense.Api.ApiUtil;
import com.datnt.groupexpense.Api.GroupClient;
import com.datnt.groupexpense.Api.UserExpenseClient;
import com.datnt.groupexpense.ModelRetrofit.CategoryResult;
import com.datnt.groupexpense.ModelRetrofit.MemberResult;
import com.datnt.groupexpense.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewFragment extends Fragment {
    Button btn_AddCategory, btn_AddExpense;
    private EditText edt_Quantity;
    private TextView tv_ManageGroup,tv_NewCategoryName, tv_NumberOfMember;
    private Spinner spn_ExpenseType;
    private String[] expenseTypeArr;
    int groupId, userId;
    int quantity;
    GroupClient groupClient = ApiUtil.groupClient();
    UserExpenseClient userExpenseClient = ApiUtil.userExpenseClient();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new,container,false);
        //declare
        tv_ManageGroup = view.findViewById(R.id.tv_manage_group);
        spn_ExpenseType = view.findViewById(R.id.spn_expense_type);
        btn_AddCategory = view.findViewById(R.id.btn_add_new_category);
        tv_NewCategoryName = view.findViewById(R.id.tv_new_category_name);
        btn_AddExpense = view.findViewById(R.id.btn_add_new_expense);
        edt_Quantity = view.findViewById(R.id.edt_quantity);
        tv_NumberOfMember =view.findViewById(R.id.tv_number_of_member);


        //pref
        SharedPreferences prefs = getContext().getSharedPreferences("Expense", Context.MODE_PRIVATE);
        groupId = prefs.getInt("groupId", 0);
        userId = prefs.getInt("UserId",0);





        //
        tv_ManageGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GroupMemberActivity.class));
            }
        });
        btn_AddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    quantity = Integer.parseInt(edt_Quantity.getText().toString());
                    if(quantity<1){
                        Toast.makeText(getContext(), "Số tiền chi tiêu phải lớn hơn 0.", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(getContext(), "Vui lòng nhập số tiền chi tiêu.", Toast.LENGTH_SHORT).show();
                }

                Call<ResponseBody> call = userExpenseClient.createNewExpense(groupId, userId, categoryId, quantity);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Thêm chi tiêu thành công.", Toast.LENGTH_SHORT).show();
                            edt_Quantity.setText("");
                        }
                        else {
                            Toast.makeText(getContext(), "Thêm chi tiêu thất bại.", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "Không thể kết nối tới server.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btn_AddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCategoryName = tv_NewCategoryName.getText().toString();
                if(newCategoryName.trim().length()<1){
                    Toast.makeText(getContext(), "Xin nhập tên loại chi tiêu mới.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Call<ResponseBody> call = groupClient.createNewCategory(groupId,newCategoryName);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Tạo mới loại chi tiêu thành công.", Toast.LENGTH_SHORT).show();
                            tv_NewCategoryName.setText("");
                            getListCategory();
                        }
                        else {
                            Toast.makeText(getContext(), "Tạo mới loại chi tiêu thất bại.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "Không thể kết nối tới server.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        //spinner data
        getListCategory();
        //spinner on item selected
        spn_ExpenseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryId = categoryResults.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        GroupClient groupClient = ApiUtil.groupClient();
        Call<List<MemberResult>> call = groupClient.getListMember(groupId);
        call.enqueue(new Callback<List<MemberResult>>() {
            @Override
            public void onResponse(Call<List<MemberResult>> call, Response<List<MemberResult>> response) {
                if (response.isSuccessful()) {
                    tv_NumberOfMember.setText("Thành viên("+response.body().size()+")");
                }

            }

            @Override
            public void onFailure(Call<List<MemberResult>> call, Throwable t) {

            }
        });



        return view;
    }
    int categoryId;
    List<CategoryResult> categoryResults;
    private void getListCategory(){

        Call<List<CategoryResult>> call = groupClient.getGroupCategory(groupId);
        call.enqueue(new Callback<List<CategoryResult>>() {
            @Override
            public void onResponse(Call<List<CategoryResult>> call, Response<List<CategoryResult>> response) {
                if (response.isSuccessful()) {
                    categoryResults = new ArrayList<>();
                    categoryResults = response.body();
                    expenseTypeArr = new String[response.body().size()];
                    for(int i = 0; i<response.body().size();i++){
                        expenseTypeArr[i] = response.body().get(i).getName();
                    }
                    ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,expenseTypeArr);
                    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_ExpenseType.setAdapter(aa);


                }
                else {
                    Toast.makeText(getContext(), "Không thể tải thể loại chi tiêu.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryResult>> call, Throwable t) {
                Toast.makeText(getContext(), "Không thể kết nối đến server.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
