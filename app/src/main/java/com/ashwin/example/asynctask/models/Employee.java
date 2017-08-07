package com.ashwin.example.asynctask.models;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Ashwin on 04-08-2017.
 */

@JsonObject
public class Employee {

    public Employee() { }

    @JsonField(name = "id")
    private String id = "";

    @JsonField(name = "name")
    private String name = "";

    @JsonField(name = "company")
    private String company = "";

    @JsonField(name = "age")
    private int age;

    @JsonField(name = "salary")
    private double salary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
