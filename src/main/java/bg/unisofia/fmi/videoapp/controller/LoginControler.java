package bg.unisofia.fmi.videoapp.controller;


import bg.unisofia.fmi.videoapp.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class LoginControler {
    @Inject
    private FacesContext facesContext;

    @Inject
    private UserService userLogin;

    private String email;
    private String password;

    @Produces
    @Named
    public String getEmail() {
        return email;
    }

    @Produces
    @Named
    public String getPassword() {
        return password;
    }

    public void login() throws Exception {
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful"));
        loginMember();
    }

    @PostConstruct
    public void loginMember() {

    }
}

