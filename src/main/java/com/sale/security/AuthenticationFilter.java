package com.sale.security;

import com.sale.model.Users;
import com.sale.services.UserServices;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ki.kao on 2/8/2017.
 */
public class AuthenticationFilter implements Filter {

    private UserServices userServices = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userServices = new UserServices();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        if (session != null && session.isNew()) {
            doAuthenticate(request, response, filterChain, session);
        } else {
            if (session != null && session.getAttribute("user") != null && request.getHeader("Authorization") != null) {
                if (compareUserInSession(request.getHeader("Authorization"), (Users) session.getAttribute("user")))
                    filterChain.doFilter(request, response);
                else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(responseData(false, 303, "Bad Credentials"));
                }
            } else
                doAuthenticate(request, response, filterChain, session);
        }
    }

    @Override
    public void destroy() {

    }

    private void doAuthenticate(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, HttpSession session) throws IOException, ServletException {
        final String authorization = request.getHeader("Authorization");
        String username = null;
        String password = null;
        Users user = null;
        if (authorization != null && authorization.startsWith("Basic")) {
            // Authorization: Basic base64credentials
            String base64 = authorization.substring("Basic".length()).trim();
            String credentials = new String(Base64.decodeBase64(base64),
                    Charset.forName("UTF-8"));
            // credentials = username:password
            final String[] values = credentials.split(":", 2);
            username = values[0];
            password = values[1];
            if (username != null && password != null)
                user = userServices.authenticate(username, password);
            else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(responseData(false, 303, "No Basic Authentication found"));
            }
            if (user != null && session.getAttribute("user") == null) {
                session.setAttribute("user", user);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write(responseData(false, 404, "User: " + username + " Not found in database!"));
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(responseData(false, 303, "Unauthorized access request"));
        }
    }

    private String responseData(boolean status, int code, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("STATUS", status);
        map.put("CODE", code);
        map.put("MESSAGE", message);
        return map.toString();
    }

    private boolean compareUserInSession(String header, Users user) {
        if (header != null && header.startsWith("Basic")) {
            String base64 = header.substring("Basic".length()).trim();
            String credentials = new String(Base64.decodeBase64(base64),
                    Charset.forName("UTF-8"));
            final String[] values = credentials.split(":", 2);
            if (values[0].equals(user.getUsername()) && values[1].equals(user.getPassword()))
                return true;
        }
        return false;
    }

}
