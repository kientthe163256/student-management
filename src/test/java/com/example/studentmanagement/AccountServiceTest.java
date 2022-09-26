package com.example.studentmanagement;

import com.example.studentmanagement.entity.Account;
import com.example.studentmanagement.repository.AccountRepo;
import com.example.studentmanagement.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class AccountServiceTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepo accountRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void createAccount(){
        String hashedPass = passwordEncoder.encode("123456");
        Account account = Account.builder()
                .email("admin@gmail.com")
                .password(hashedPass)
                .role("ROLE_ADMIN")
                .enabled(1)
                .build();
        em.persist(account);
        assertThat(account).isNotNull();
    }

    @Test
    public void getAccountByEmail(){
        String email = "admin1@gmail.com";
        Account account = accountService.findByEmail(email);
        assertThat(account).isNotNull();
    }

    @Test
    public void matchPassword(){
        String email = "admin@gmail.com";
        Account account = accountService.findByEmail(email);
        String rawPass = "12345";
        String hashedPass = passwordEncoder.encode(rawPass);
        assertThat(account.getPassword()).isEqualTo(hashedPass);
    }
}
