package com.haikarosetz.mikecall.pojo;


public class ContactsItem {

    private String name;
    private String phone;

    public ContactsItem(){}

    public ContactsItem(String name, String phone){
        this.name=name;
        this.phone=phone;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }
}
