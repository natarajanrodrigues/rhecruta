/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.dac.rhecruta.web.beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Pedro Arthur
 */

@Named
@RequestScoped
public class RegisterBean {

    private String registerAs;
    
    @PostConstruct
    private void init() {
        this.registerAs = "candidate";
    }

    public String getRegisterAs() {
        return registerAs;
    }

    public void setRegisterAs(String registerAs) {
        this.registerAs = registerAs;
    }

    public boolean isCandidateSelected() {
        return this.registerAs.equals("candidate");
    }

    public boolean isEmployeeSelected() {
        return this.registerAs.equals("employee");
    }
}
