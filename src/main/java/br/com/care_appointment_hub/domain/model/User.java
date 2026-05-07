package br.com.care_appointment_hub.domain.model;

import br.com.care_appointment_hub.domain.enums.Role;

public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Role role;

    public User(Long id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public boolean isAdmin(){
        return this.role == Role.ADMIN;
    }

    public boolean isDoctor(){
        return this.role == Role.DOCTOR;
    }

    public boolean isNurse(){
        return this.role == Role.NURSE;
    }

    public boolean isPatient(){
        return this.role == Role.PATIENT;
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}
