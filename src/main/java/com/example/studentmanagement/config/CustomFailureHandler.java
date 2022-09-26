package com.example.studentmanagement.config;

import com.example.studentmanagement.entity.Account;
import com.example.studentmanagement.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CustomFailureHandler implements AuthenticationFailureHandler {
    @Autowired
    private AccountService accountService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("email");

        Account account = accountService.findByEmail(email);
        if (account == null) {
            System.out.println("account is null");
            throw new BadCredentialsException("Invalid username or password!!!!!");
        }

    }
}
