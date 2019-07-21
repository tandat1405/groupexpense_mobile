package com.datnt.groupexpense.ModelRetrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GroupContainResult {
    @SerializedName("id_group")
    @Expose
    private Integer idGroup;
    @SerializedName("name_group")
    @Expose
    private String nameGroup;
    @SerializedName("ownerGroup")
    @Expose
    private OwnerGroupResult ownerGroup;

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

    public OwnerGroupResult getOwnerGroup() {
        return ownerGroup;
    }

    public void setOwnerGroup(OwnerGroupResult ownerGroup) {
        this.ownerGroup = ownerGroup;
    }
}
