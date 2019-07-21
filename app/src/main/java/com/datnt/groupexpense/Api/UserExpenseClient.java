package com.datnt.groupexpense.Api;

import com.datnt.groupexpense.ModelRetrofit.ExpenseResult;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserExpenseClient {
    @POST("/userexpense/create")
    Call<ResponseBody> createNewExpense(@Query("group") int groupId, @Query("user") int userId, @Query("expense") int categoryId, @Query("quantity") int quantity);

    @GET("/userexpense/listday")
    Call<List<ExpenseResult>> getExpenseByDay(@Query("group") int groupId, @Query("date") String date);

    @GET("/userexpense/listmonth")
    Call<List<ExpenseResult>> getExpenseByMonth(@Query("group") int groupId, @Query("date_month") String month);
}
