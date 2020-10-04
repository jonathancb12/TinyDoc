package com.tinydoc.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USERID", nullable = false)
    private String userId = "";

    @Column(name = "NAME")
    private String name = null;

    @Column(name = "LASTNAME")
    private String lastName = null;

    @Column(name = "BIRTH")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth = null;

    @Column(name = "ACTIVE")
    private Boolean active = false;

    @Column(name = "EMAIL")
    private String email = null;

    @Column(name = "PASS")
    private String pass = null;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USERS_USERID")
    private List<Roles> roles = null;

    public Usuario() {
        this.pass = String.valueOf(Math.random());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }
}
