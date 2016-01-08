/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Juliana
 */
@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable{
   

    //Login digitado pelo usuário para fazer o acesso
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //Senha digitada pelo usuário para fazer o acesso
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    private boolean loggedIn;

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }


    //Realiza o login caso de tudo certo 
    public void doLogin() {

        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg;
        loggedIn = false;

        this.username = this.username.trim();
        this.password = this.password.trim();

        if ("sueli".equals(username) && "shamanking".equals(password)) {
            loggedIn = true;
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem-vindo(a)!", "");
        } else {
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Credenciais inválidas");
        }
    
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);

    }
    
    public String page() {
        if (loggedIn) {
            return "/index";
        } else {
            return "/login";
        }
    }

    public void isLogado() {

        if (!loggedIn) {
            try {
                
                FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml?faces-redirect=true");
            } catch (IOException ex) {
//                Logger.getLogger(CalendarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        this.loggedIn = true;

    }

    public String doLogout() {

//        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg;
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout efetuado", "");
        loggedIn = false;
        username = "";
        password = "";

        FacesContext.getCurrentInstance().addMessage(null, msg);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(false);
        
        if (session != null) {
        session.invalidate();
    }

        return "/login?faces-redirect=true";

    }

}
