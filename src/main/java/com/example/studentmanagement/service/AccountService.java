package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Account;
import com.example.studentmanagement.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepo accountRepo;

    public Account findByEmail(String email){
        return accountRepo.findByEmail(email).orElse(null);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepo.findByEmail(username);
        if (!account.isPresent()){
            throw new UsernameNotFoundException("Email does not exist in system. Please re-enter another email!");
        }
        Account validAccount = account.get();
        UserDetails userDetails = new User(validAccount.getEmail(),
                validAccount.getPassword(),
                mapRoleToAuthorities(validAccount.getRole()));
        return userDetails;
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthorities(String role) {
        Collection<String> roles = new ArrayList<>();
        roles.add(role);

        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r))
                .collect(Collectors.toList());
    }
}
