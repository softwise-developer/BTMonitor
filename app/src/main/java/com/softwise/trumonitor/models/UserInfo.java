package com.softwise.trumonitor.models;

public class UserInfo {
    private String created_by;
    private String created_date;
    private String deleted_by;
    private String deleted_date;
    private String email;
    private String first_name;
    private String last_name;
    private String level;
    private String mobile;
    private int operator_id;
    private int org_id;
    private String password;
    private String role;
    private int status;
    private String updated_by;
    private String updated_date;
    private int user_id;

    public int getOperator_id() {
        return this.operator_id;
    }

    public void setOperator_id(int i) {
        this.operator_id = i;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String str) {
        this.first_name = str;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String str) {
        this.last_name = str;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String str) {
        this.mobile = str;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String str) {
        this.password = str;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String str) {
        this.role = str;
    }

    public String getCreated_by() {
        return this.created_by;
    }

    public void setCreated_by(String str) {
        this.created_by = str;
    }

    public String getUpdated_by() {
        return this.updated_by;
    }

    public void setUpdated_by(String str) {
        this.updated_by = str;
    }

    public String getDeleted_by() {
        return this.deleted_by;
    }

    public void setDeleted_by(String str) {
        this.deleted_by = str;
    }

    public String getCreated_date() {
        return this.created_date;
    }

    public void setCreated_date(String str) {
        this.created_date = str;
    }

    public String getUpdated_date() {
        return this.updated_date;
    }

    public void setUpdated_date(String str) {
        this.updated_date = str;
    }

    public String getDeleted_date() {
        return this.deleted_date;
    }

    public void setDeleted_date(String str) {
        this.deleted_date = str;
    }

    public int getOrg_id() {
        return this.org_id;
    }

    public void setOrg_id(int i) {
        this.org_id = i;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String str) {
        this.level = str;
    }

    public String toString() {
        return "UserInfo{user_id=" + this.user_id + '\'' + "operator_id=" + this.operator_id + ", first_name='" + this.first_name + '\'' + ", last_name='" + this.last_name + '\'' + ", mobile='" + this.mobile + '\'' + ", email='" + this.email + '\'' + ", password='" + this.password + '\'' + ", role='" + this.role + '\'' + ", created_by='" + this.created_by + '\'' + ", updated_by='" + this.updated_by + '\'' + ", deleted_by='" + this.deleted_by + '\'' + ", created_date='" + this.created_date + '\'' + ", updated_date='" + this.updated_date + '\'' + ", deleted_date='" + this.deleted_date + '\'' + ", org_id=" + this.org_id + ", status=" + this.status + ", level='" + this.level + '\'' + '}';
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int i) {
        this.user_id = i;
    }
}
