package com.example.studentmanagement.config;

import com.example.studentmanagement.aspect.TestAspect;
import com.example.studentmanagement.entity.Account;
import com.example.studentmanagement.repository.AccountRepo;
import com.example.studentmanagement.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Configuration
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepo accountRepo;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Account account = accountService.findByEmail(email);
        if (account.getEnabled() == 0) {
//            redirectStrategy.sendRedirect(request, response, "/login?deactivated");
            throw new BadCredentialsException("Your account is deactivated. Contact admin for more information.");
        } else {
            if (account.getRole().equals("ROLE_ADMIN")) {
                redirectStrategy.sendRedirect(request, response, "/admin");
            } else if (account.getRole().equals("ROLE_STUDENT")) {
                redirectStrategy.sendRedirect(request, response, "/student");
            } else {
                throw new IllegalStateException();
            }
        }
    }
}
