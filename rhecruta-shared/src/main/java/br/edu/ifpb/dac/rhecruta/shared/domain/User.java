/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain;

/**
 * 
 * @author Pedro Arthur
 */
public class User {
    
    private String cpf;
    private String firstname;
    private String lastname;
    private Address address;
    private Credentials credentials;

    public User(String cpf, String firstname, String lastname, Address address, Credentials credentials) {
        this.cpf = cpf;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.credentials = credentials;
    }
    
    public User() {
        
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public String toString() {
        return "User{" + "cpf=" + cpf + ", firstname=" + firstname + ", lastname=" + lastname + ", address=" + address + ", credentials=" + credentials + '}';
    }
}
