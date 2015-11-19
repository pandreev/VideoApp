package bg.unisofia.fmi.videoapp.controller;

import bg.unisofia.fmi.videoapp.model.User;
import bg.unisofia.fmi.videoapp.service.UserService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Model
public class Registration {

    @Inject
    private FacesContext facesContext;

    @Inject
    private UserService userService;

    private User newUser;

    @Produces
    @Named
    public User getNewUser() {
        return newUser;
    }

    public void register() {
        try {
            userService.register(newUser);
            ExternalContext externalContext = facesContext.getExternalContext();
            Authentication auth = (Authentication) externalContext.getSessionMap().get("auth");
            auth.setIsLoggedIn(true);
            auth.setLoggedUser(newUser);
            externalContext.redirect(externalContext.getRequestContextPath() + "/index.jsf");
            initNewMember();
        } catch (Exception e) {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Registration error"));
        }
    }

    @PostConstruct
    public void initNewMember() {
        newUser = new User();
    }
}
