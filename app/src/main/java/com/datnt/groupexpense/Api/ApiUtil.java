package com.datnt.groupexpense.Api;

public class ApiUtil {
    public static String BASE_URL = "http://192.168.1.35:8080";
    //public static String BASE_URL = "http://192.168.2.84:8080";
    public static UserClient userClient() {
        return RetrofitClient.getClient(BASE_URL).create(UserClient.class);
    }
    public static GroupClient groupClient() {
        return RetrofitClient.getClient(BASE_URL).create(GroupClient.class);
    }
    public static UserExpenseClient userExpenseClient() {
        return RetrofitClient.getClient(BASE_URL).create(UserExpenseClient.class);
    }
    public static InvitationClient invitationClient() {
        return RetrofitClient.getClient(BASE_URL).create(InvitationClient.class);
    }
}
