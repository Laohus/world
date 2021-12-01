package com.personal.world.common;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class User {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column
    private String name;

    @Column
    private String passWord;

    @Column
    private String crateTime;

    @Column
    private String email;

    @Column
    private String age;

    @Column
    private String sex;

    //用户初始密码
    private String ADD_PASSWORD="123456";


    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public String getPassword() {
        return passWord;
    }
    public void setPassword(String passWord) { this.passWord = passWord; }

    public String getCratetime() {
        return crateTime;
    }
    public void setCratetime(String crateTime) { this.crateTime = crateTime; }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) { this.email = email; }

    public String getAge() {
        return age;
    }
    public void setAge(String age) { this.age = age; }

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) { this.sex = sex; }

    public String getADD_PASSWORD(){
        return ADD_PASSWORD;
    }
    public void setADD_PASSWORD(String ADD_PASSWORD){
        this.ADD_PASSWORD=ADD_PASSWORD;
    }

}
