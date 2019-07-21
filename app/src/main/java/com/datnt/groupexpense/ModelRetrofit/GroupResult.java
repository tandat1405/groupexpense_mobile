package com.datnt.groupexpense.ModelRetrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupResult {
    @SerializedName("id_group")
    @Expose
    private Integer idGroup;
    @SerializedName("name_group")
    @Expose
    private String nameGroup;
    @SerializedName("ownerGroup")
    @Expose
    private OwnerGroupResult ownerGroupResult;

    public Integer getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(Integer idGroup) {
        this.idGroup = idGroup;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public OwnerGroupResult getOwnerGroupResult() {
        return ownerGroupResult;
    }

    public void setOwnerGroupResult(OwnerGroupResult ownerGroupResult) {
        this.ownerGroupResult = ownerGroupResult;
    }
}
