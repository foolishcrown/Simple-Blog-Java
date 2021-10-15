/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.account;

import java.io.Serializable;

/**
 *
 * @author Shang
 */
public class AccountDTO implements Serializable{
    private String email,
            name,
            password,
            authenCode;
    private int roleId,
            statusId;

    public AccountDTO() {
    }

    public AccountDTO(String email, String name, String password, String authenCode, int roleId, int statusId) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.authenCode = authenCode;
        this.roleId = roleId;
        this.statusId = statusId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthenCode() {
        return authenCode;
    }

    public void setAuthenCode(String authenCode) {
        this.authenCode = authenCode;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
    
    
    
    
}
