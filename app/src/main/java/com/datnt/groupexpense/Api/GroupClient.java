package com.datnt.groupexpense.Api;

import com.datnt.groupexpense.ModelRetrofit.CategoryResult;
import com.datnt.groupexpense.ModelRetrofit.MemberResult;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface GroupClient {
    @FormUrlEncoded
    @POST("/group/create")
    Call<ResponseBody> createGroup(@Field("id") int id, @Field("groupName") String groupName);

    @GET("/group/allexpense")
    Call<List<CategoryResult>> getGroupCategory(@Query("id") int groupId);

    @POST("/expense/create")
    Call<ResponseBody> createNewCategory(@Query("group") int groupId, @Query("name") String categoryName);

    @GET("/groupuser/list")
    Call<List<MemberResult>> getListMember(@Query("group") int groupId);

    @PUT("/group/update")
    Call<ResponseBody> updateGroupName(@Query("group") int groupId, @Query("name") String name);

    @DELETE("/group/delete")
    Call<ResponseBody> deleteGroup(@Query("group") int groupId);
}
