package com.mezan.contactdetails;

public class ContactData {
    private String Name,Mobile,Email;
    public ContactData(){

    }
    public ContactData(String Name,String Mobile,String Email){
        this.Name = Name;
        this.Mobile = Mobile;
        this.Email = Email;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


}
