package bg.unisofia.fmi.videoapp.util;


import javax.servlet.*;
import java.io.IOException;

public class CharacterEncodingFilter implements javax.servlet.Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void destroy() {

    }


}
