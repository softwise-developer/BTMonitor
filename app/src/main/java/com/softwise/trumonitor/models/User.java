package com.softwise.trumonitor.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("deleted_by")
    @Expose
    private Object deletedBy;
    @SerializedName("deleted_date")
    @Expose
    private Object deletedDate;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("operator_id")
    @Expose
    private String operatorId;
    @SerializedName("org_id")
    @Expose
    private Integer orgId;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    @SerializedName("updated_date")
    @Expose
    private Object updatedDate;

    public String getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(String str) {
        this.operatorId = str;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String str) {
        this.firstName = str;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String str) {
        this.lastName = str;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String str) {
        this.mobile = str;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean bool) {
        this.status = bool;
    }

    public Integer getOrgId() {
        return this.orgId;
    }

    public void setOrgId(Integer num) {
        this.orgId = num;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String str) {
        this.level = str;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String str) {
        this.role = str;
    }

    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Integer num) {
        this.createdBy = num;
    }

    public Object getDeletedBy() {
        return this.deletedBy;
    }

    public void setDeletedBy(Object obj) {
        this.deletedBy = obj;
    }

    public Object getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Object obj) {
        this.updatedBy = obj;
    }

    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(String str) {
        this.createdDate = str;
    }

    public Object getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Object obj) {
        this.updatedDate = obj;
    }

    public Object getDeletedDate() {
        return this.deletedDate;
    }

    public void setDeletedDate(Object obj) {
        this.deletedDate = obj;
    }
}
