package bg.unisofia.fmi.videoapp.controller;

import bg.unisofia.fmi.videoapp.model.User;
import bg.unisofia.fmi.videoapp.service.UserService;
import bg.unisofia.fmi.videoapp.util.Password;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;

@ManagedBean(name = "auth")
@SessionScoped
public class Authentication {

    private String email;
    private String password;
    private boolean isLoggedIn;
    private User loggedUser;

    @Inject
    private FacesContext facesContext;

    @EJB
    private UserService memberService;

    public void login() throws Exception {
        User user = memberService.findUser(email);
        if (checkMember(user, password)) {
            this.isLoggedIn = true;
            this.loggedUser = user;
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.redirect(externalContext.getRequestContextPath() + "/index.jsf");

        } else {
            this.isLoggedIn = false;
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Registration error"));
        }
    }

    public void logout() throws IOException {
        this.isLoggedIn = false;
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect(externalContext.getRequestContextPath() + "/index.jsf");
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

    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    private boolean checkMember(User user, String password) throws Exception {
        return Password.check(password, user.getPassword());
    }
}