package com.datnt.groupexpense.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.datnt.groupexpense.Adapter.ChartAdapter;
import com.datnt.groupexpense.Api.ApiUtil;
import com.datnt.groupexpense.Api.UserExpenseClient;
import com.datnt.groupexpense.ModelRetrofit.ExpenseResult;
import com.datnt.groupexpense.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ByMonthFragment extends Fragment {
    private Spinner spn_Month, spn_Year;
    private String[] monthArr = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String[] yearArr = {"2015", "2016", "2017", "2018", "2019"};
    private Button btn_Search;
    int userId, groupId;
    private ListView lv_BarChart;
    private List<ExpenseResult> expenseResultList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_by_month, container, false);
        spn_Month = view.findViewById(R.id.spn_month);
        spn_Year = view.findViewById(R.id.spn_year);
        btn_Search = view.findViewById(R.id.btn_Search);
        lv_BarChart = view.findViewById(R.id.lv_bar_chart_month);
        //set spinner data
        ArrayAdapter aaMonth = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, monthArr);
        aaMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Month.setAdapter(aaMonth);
        ArrayAdapter aaYear = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, yearArr);
        aaYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Year.setAdapter(aaYear);
        //pref
        SharedPreferences prefs = getContext().getSharedPreferences("Expense", Context.MODE_PRIVATE);
        groupId = prefs.getInt("groupId", 0);
        userId = prefs.getInt("UserId", 0);


        btn_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseResultList = new ArrayList<>();
                String month = spn_Month.getSelectedItem().toString();
                String year = spn_Year.getSelectedItem().toString();
                UserExpenseClient userExpenseClient = ApiUtil.userExpenseClient();
                Call<List<ExpenseResult>> call = userExpenseClient.getExpenseByMonth(groupId, month + "/" + year);
                call.enqueue(new Callback<List<ExpenseResult>>() {
                    @Override
                    public void onResponse(Call<List<ExpenseResult>> call, Response<List<ExpenseResult>> response) {
                        if (response.isSuccessful()) {
                            expenseResultList = response.body();
                            ChartAdapter ca = new ChartAdapter(getContext(), expenseResultList);

                        } else {
                            Toast.makeText(getContext(), "Không có dữ liệu.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ExpenseResult>> call, Throwable t) {
                        Toast.makeText(getContext(), "Không thể kết nối đến server.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        return view;
    }
}
