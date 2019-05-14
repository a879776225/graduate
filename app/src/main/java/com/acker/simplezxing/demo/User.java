package com.acker.simplezxing.demo;

public class User {
    public String Username;
    public String CompanyName;
    public String UserType;
    public String Linkman;
    public String MobileTelephone;
    public String Email;
    public String Address;
    public String Bewrite;
    public String AddNow;
    public String balance;



    public void setBewrite(String bewrite) {
        Bewrite = bewrite;
    }

    public void setLinkman(String linkman) {
        Linkman = linkman;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setAddNow(String addNow) {
        AddNow = addNow;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setMobileTelephone(String mobileTelephone) {
        MobileTelephone = mobileTelephone;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getBewrite() {
        return Bewrite;
    }

    public String getLinkman() {
        return Linkman;
    }

    public String getUsername() {
        return Username;
    }

    public String getAddNow() {
        return AddNow;
    }

    public String getAddress() {
        return Address;
    }

    public String getBalance() {
        return balance;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public String getEmail() {
        return Email;
    }

    public String getMobileTelephone() {
        return MobileTelephone;
    }

    public String getUserType() {
        return UserType;
    }
    public String getall(){
        return  "用户名："+Username+"\n"+"余额:"+this.balance+"\n"+"留言："+this.Bewrite+"\n"+"邮箱:"+this.Email+"\n"+"地址："+this.Address+"\n"+"联系人："+this.Linkman;
    }

}
