package com.project.ems.model;

import java.time.LocalDate;

import jakarta.persistence.*;


@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String middlename;
    private String lastname;
    private String address;
    private LocalDate birthday;
    private String position;
    private String email;
    private String password;
    private String status;
    private String role;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getFirstname(){
        return firstname;
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public String getMiddlename(){
        return middlename;
    }
    public void setMiddlename(String middlename){
        this.middlename = middlename;
    }

    public String getLastname(){
        return lastname;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address =address;
    }

    public LocalDate getBirthday(){
        return birthday;
    }
    public void setBirthday(LocalDate birthday){
        this.birthday = birthday;
    }

    public String getPosition(){
        return position;
    }
    public void setPosition(String position){
        this.position = position;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role = role;
    }

    public Department getDepartment(){
        return department;
    }
    public void setDepartment(Department department){
        this.department = department;
    }
}
