package com.fieldmobility.igl.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    Context context;
    SharedPreferences sharedPreference;
    SharedPreferences.Editor spEditor;
    String DB_NAME = "IGL";
    private final String UUID = "uuid";
    private final String Name = "name";
    private final String Email = "email";
    private final String Mobile = "mobile";
    private final String City = "city";
    private final String State = "state";
    private final String LoginStatus = "status";
    private final String Login_User = "login_user";
    private final String Login_Admin = "Login_Admin";
    private final String Token = "token";
    private final String Type_User = "type_user";
    private final String Date = "date";
    private final String LoginDate="login_date";
    private final String Zone_Code="zone_code";
    public SharedPrefs(Context context) {
        this.context = context;
        sharedPreference = this.context.getSharedPreferences(DB_NAME,
                Context.MODE_PRIVATE);
    }

    public String getZone_Code() {
        return sharedPreference.getString(Zone_Code, "");
    }

    public void setZone_Code(String zone_code) {
        spEditor = sharedPreference.edit();
        spEditor.putString(Zone_Code, zone_code);
        spEditor.commit();
    }

    public String getLoginDate() {
        return sharedPreference.getString(LoginDate, "");
    }

    public void setLoginDate(String login_date) {
        spEditor = sharedPreference.edit();
        spEditor.putString(LoginDate, login_date);
        spEditor.commit();
    }

    public String getDate() {
        return sharedPreference.getString(Date, "");
    }

    public void setDate(String date) {
        spEditor = sharedPreference.edit();
        spEditor.putString(Date, date);
        spEditor.commit();
    }
    public String getUUID() {
        return sharedPreference.getString(UUID, "");
    }

    public void setUUID(String uuid) {
        spEditor = sharedPreference.edit();
        spEditor.putString(UUID, uuid);
        spEditor.commit();
    }

    public String getType_User() {
        return sharedPreference.getString(Type_User, "");
    }

    public void setType_User(String type_user) {
        spEditor = sharedPreference.edit();
        spEditor.putString(Type_User, type_user);
        spEditor.commit();
    }

    public String getToken() {
        return sharedPreference.getString(Token, "");
    }

    public void setToken(String token) {
        spEditor = sharedPreference.edit();
        spEditor.putString(Token, token);
        spEditor.commit();
    }

    public String getName() {
        return sharedPreference.getString(Name, "");
    }

    public void setName(String name) {
        spEditor = sharedPreference.edit();
        spEditor.putString(Name, name);
        spEditor.apply();
    }

    public String getEmail() {
        return sharedPreference.getString(Email, "");
    }

    public void setEmail(String email) {
        spEditor = sharedPreference.edit();
        spEditor.putString(Email, email);
        spEditor.apply();
    }

    public String getLogin_User() {
        return sharedPreference.getString(Login_User, "");
    }

    public void setLogin_User(String login_user) {
        spEditor = sharedPreference.edit();
        spEditor.putString(Login_User, login_user);
        spEditor.apply();
    }
    public String getLogin_Admin() {
        return sharedPreference.getString(Login_User, "");
    }

    public void setLogin_Admin(String login_admin) {
        spEditor = sharedPreference.edit();
        spEditor.putString(Login_Admin, "Login_Admin");
        spEditor.apply();
    }
    public String getMobile() {
        return sharedPreference.getString(Mobile, "");
    }

    public void setMobile(String mobile) {
        spEditor = sharedPreference.edit();
        spEditor.putString(Mobile, mobile);
        spEditor.apply();
    }

    public String getCity() {
        return sharedPreference.getString(City, "");
    }

    public void setCity(String city) {
        spEditor = sharedPreference.edit();
        spEditor.putString(City, city);
        spEditor.apply();
    }



    public String getState() {
        return sharedPreference.getString(State, "");
    }

    public void setState(String state) {
        spEditor = sharedPreference.edit();
        spEditor.putString(State, state);
        spEditor.apply();
    }

    public String getLoginStatus() {
        return sharedPreference.getString(LoginStatus, "false");
    }

    public void setLoginStatus(String status) {
        spEditor = sharedPreference.edit();
        spEditor.putString(LoginStatus, status);
        spEditor.apply();
    }

}
