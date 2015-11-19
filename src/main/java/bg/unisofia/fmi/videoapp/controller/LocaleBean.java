package bg.unisofia.fmi.videoapp.controller;

import bg.unisofia.fmi.videoapp.model.User;
import bg.unisofia.fmi.videoapp.service.UserService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Locale;

@ManagedBean(name = "locale")
@SessionScoped
public class LocaleBean {

    @EJB
    private UserService memberService;

    private Locale locale = new Locale("bg", "BG");

    public void changeLocale() throws IOException {
        locale = locale.equals(Locale.ENGLISH) ? new Locale("bg", "BG") : Locale.ENGLISH;
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(locale);
        if (getLoggedUser(context) != null) {
            memberService.setDefaultLocale(getLoggedUser(context), locale);
        }
        redirectToOriginalUrl();
    }

    private void redirectToOriginalUrl() throws IOException {
        final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String originalURL = externalContext.getRequestHeaderMap().get("referer");
        if (originalURL == null) {
            originalURL = externalContext.getRequestContextPath() + "/index.jsf";
        }
        externalContext.redirect(originalURL);
    }

    public Locale getLocale() {
        return locale;
    }

    public String getDisplayLocale() {
        return locale.equals(Locale.ENGLISH) ? "EN" : "BG";
    }

    private User getLoggedUser(final FacesContext context) {
        ExternalContext externalContext = context.getExternalContext();
        Authentication auth = (Authentication) externalContext.getSessionMap().get("auth");
        return auth == null ? null : auth.getLoggedUser();
    }
}
