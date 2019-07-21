package com.datnt.groupexpense.ModelRetrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpenseResult {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("expenseResponses")
    @Expose
    private List<ExpenseResponse> expenseResponses = null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ExpenseResponse> getExpenseResponses() {
        return expenseResponses;
    }

    public void setExpenseResponses(List<ExpenseResponse> expenseResponses) {
        this.expenseResponses = expenseResponses;
    }
}
