package com.datnt.groupexpense.Api;

import com.datnt.groupexpense.ModelRetrofit.GroupResult;
import com.datnt.groupexpense.ModelRetrofit.LoginResult;
import com.datnt.groupexpense.ModelRetrofit.RegisterResult;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserClient {

    @FormUrlEncoded
    @POST("/user/register")
    Call<ResponseBody> createAccount(@Field("username")String username, @Field("password")String password);

    @GET("/user/login")
    Call<LoginResult> login(@Query("username") String username, @Query("password") String password);

    @GET("/user/grouplist")
    Call<List<GroupResult>> getMyGroup(@Query("id") int userId);

}
