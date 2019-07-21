package com.datnt.groupexpense.ModelRetrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryResult {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("groupContain")
    @Expose
    private GroupContainResult groupContain;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupContainResult getGroupContain() {
        return groupContain;
    }

    public void setGroupContain(GroupContainResult groupContain) {
        this.groupContain = groupContain;
    }

}
