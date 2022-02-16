package com.sportal.service;

import com.sportal.exceptions.AuthenticationException;
import com.sportal.model.pojo.User;
import com.sportal.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class SessionService {
    private static final String LOGGED = "LOGGED";
    @Autowired
    private UserRepository userRepository;

    public void loginUser(HttpSession session, long id) {
        session.setAttribute(LOGGED, id);
    }
    public boolean userAlreadyLogged(HttpSession ses) {
        return ses.getAttribute(LOGGED) != null;
    }
}
