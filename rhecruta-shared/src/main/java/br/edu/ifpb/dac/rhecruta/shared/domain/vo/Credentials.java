/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.shared.domain.vo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Pedro Arthur
 */
@Embeddable
public class Credentials implements Serializable {
    
    @Column(nullable = false, unique = true)
    @Email(message= "#{messages['validator.email']}")
    private String email;
    @Column(nullable = false, unique = false)
    private String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Credentials() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean authenticate(Credentials credentials) {
        return this.email.equals(credentials.email) && this.password.equals(credentials.password);
    }

    @Override
    public String toString() {
        return "Credentials{" + "email=" + email + ", password=" + password + '}';
    }
}
