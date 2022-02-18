package com.sportal.service;

import com.sportal.exceptions.AuthenticationException;
import com.sportal.exceptions.UnauthorizedException;
import com.sportal.model.pojo.User;
import com.sportal.model.pojo.enums.RoleName;
import com.sportal.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class SessionService {
    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";
    public static final String USER_ID = "user_id";
    @Autowired
    private UserRepository userRepository;

    public void loginUser(HttpSession session, long id,HttpServletRequest request) {
        session.setAttribute(LOGGED, true);
        session.setAttribute(LOGGED_FROM,request.getRemoteAddr());
        session.setAttribute(USER_ID,id);
    }
    public boolean userAlreadyLogged(HttpSession ses) {
        return ses.getAttribute(LOGGED) != null;
    }
    public void validateLogin(HttpSession session,HttpServletRequest request) {
        if(session.isNew()){
            throw new UnauthorizedException("You have to login!");
        }
        if(!(Boolean) session.getAttribute(SessionService.LOGGED)&&session.getAttribute(SessionService.LOGGED)!=null){
            throw new UnauthorizedException("You have to login!");
        }
        if((Long) session.getAttribute(SessionService.USER_ID)==null){
            throw new UnauthorizedException("You have to login!");
        }
        if(session.getAttribute(SessionService.LOGGED_FROM)!=request.getRemoteAddr()){
            throw new UnauthorizedException("You have to login!");
        }
    }
    public void validateAdmin(User u){
        Optional<User> opt=userRepository.findUserByUsername(u.getUsername());
        User user=opt.get();
        if(!user.getRole().getRoleName().equals(RoleName.ADMIN)){
            throw new UnauthorizedException("You are not admin");
        }
    }
}