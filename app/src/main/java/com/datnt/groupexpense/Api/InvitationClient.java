package com.datnt.groupexpense.Api;

import com.datnt.groupexpense.ModelRetrofit.InvitationResult;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InvitationClient {
    @POST("/invitation/create")
    Call<ResponseBody> invite(@Query("group") int groupId, @Query("invite") int inviterId, @Query("receive") String receiverName);

    @GET("/invitation/listreceive")
    Call<List<InvitationResult>> getListInvitation(@Query("id") int userId);

    @POST("/invitation/check")
    Call<ResponseBody> checkInvitations(@Query("id") int invitationId, @Query("accept") boolean status);

}
