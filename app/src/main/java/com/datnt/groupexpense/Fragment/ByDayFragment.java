package com.datnt.groupexpense.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.datnt.groupexpense.Adapter.ChartAdapter;
import com.datnt.groupexpense.Api.ApiUtil;
import com.datnt.groupexpense.Api.UserExpenseClient;
import com.datnt.groupexpense.ModelRetrofit.ExpenseResponse;
import com.datnt.groupexpense.ModelRetrofit.ExpenseResult;
import com.datnt.groupexpense.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ByDayFragment extends Fragment {
    String toDayString = "";
    private ListView lv_BarChart;
    int groupId, userId;
    private EditText edt_Date;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_by_day, container, false);
        lv_BarChart = view.findViewById(R.id.lv_bar_chart);
        edt_Date = view.findViewById(R.id.edt_date_picker);
        edt_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Date ToDay = new Date();
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        toDayString = sdf.format(ToDay);
        edt_Date.setText(toDayString);


        //pref
        SharedPreferences prefs = getContext().getSharedPreferences("Expense", Context.MODE_PRIVATE);
        groupId = prefs.getInt("groupId", 0);
        userId = prefs.getInt("UserId", 0);

        getDateAndDrawChart(toDayString);
        //fakeData();


        return view;
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edt_Date.setText(sdf.format(myCalendar.getTime()));
        getDateAndDrawChart(sdf.format(myCalendar.getTime()));
    }

    private void fakeData() {
        List<ExpenseResult> expenseResultList = new ArrayList<>();
        List<ExpenseResponse> expenseResponseList = new ArrayList<>();

        ExpenseResponse expenseResponse = new ExpenseResponse();
        expenseResponse.setName("Gas");
        expenseResponse.setQuantity(240000);

        ExpenseResponse expenseResponse1 = new ExpenseResponse();
        expenseResponse1.setName("Electric");
        expenseResponse1.setQuantity(700000);

        ExpenseResponse expenseResponse2 = new ExpenseResponse();
        expenseResponse2.setName("Water");
        expenseResponse2.setQuantity(240000);

        ExpenseResponse expenseResponse3 = new ExpenseResponse();
        expenseResponse3.setName("Internet");
        expenseResponse3.setQuantity(175000);

        expenseResponseList.add(expenseResponse);
        expenseResponseList.add(expenseResponse1);
        expenseResponseList.add(expenseResponse2);
        expenseResponseList.add(expenseResponse3);

        ExpenseResult expenseResult = new ExpenseResult();
        expenseResult.setUsername("thaitd");
        expenseResult.setExpenseResponses(expenseResponseList);

        List<ExpenseResponse> expenseResponseList1 = new ArrayList<>();


        ExpenseResponse expenseResponse6 = new ExpenseResponse();
        expenseResponse6.setName("Other");
        expenseResponse6.setQuantity(120000);

        ExpenseResponse expenseResponse4 = new ExpenseResponse();
        expenseResponse4.setName("Study Fee");
        expenseResponse4.setQuantity(2000000);

        ExpenseResponse expenseResponse5 = new ExpenseResponse();
        expenseResponse5.setName("Food");
        expenseResponse5.setQuantity(157000);


        expenseResponseList1.add(expenseResponse6);
        expenseResponseList1.add(expenseResponse4);
        expenseResponseList1.add(expenseResponse5);


        ExpenseResult expenseResult1 = new ExpenseResult();
        expenseResult1.setUsername("datnt");
        expenseResult1.setExpenseResponses(expenseResponseList1);

        expenseResultList.add(expenseResult);
        expenseResultList.add(expenseResult1);

        ChartAdapter ca = new ChartAdapter(getContext(), expenseResultList);
        lv_BarChart.setAdapter(ca);



    }

    List<ExpenseResult> expenseResultList;

    private void getDateAndDrawChart(String date) {

        UserExpenseClient userExpenseClient = ApiUtil.userExpenseClient();
        Call<List<ExpenseResult>> call = userExpenseClient.getExpenseByDay(groupId, date);
        call.enqueue(new Callback<List<ExpenseResult>>() {
            @Override
            public void onResponse(Call<List<ExpenseResult>> call, Response<List<ExpenseResult>> response) {
                expenseResultList = new ArrayList<>();
                if (response.isSuccessful()) {
                    expenseResultList = response.body();
                    ChartAdapter ca = new ChartAdapter(getContext(), expenseResultList);
                    lv_BarChart.setAdapter(ca);
                    //ca.notifyDataSetChanged();
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

}
