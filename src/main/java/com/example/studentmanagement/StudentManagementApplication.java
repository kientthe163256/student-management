package com.example.studentmanagement;

import com.example.studentmanagement.entity.Account;
import com.example.studentmanagement.entity.Classroom;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.ClassroomRepo;
import com.example.studentmanagement.repository.StudentRepo;
import com.example.studentmanagement.service.ClassroomService;
import com.example.studentmanagement.service.StudentService;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootApplication
public class StudentManagementApplication implements CommandLineRunner {

    @Autowired
    private EntityManager em;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassroomService classroomService;

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
    }

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        studentService.recoverDeletedStudent(21);
//        classroomService.recoverClassroom(20);
//        Faker faker = new Faker();
//        String hashedPass = passwordEncoder.encode("12345");
//        Account admin = Account.builder()
//                .email("admin@gmail.com")
//                .password(hashedPass)
//                .role("ROLE_ADMIN")
//                .enabled(1)
//                .build();
//        em.persist(admin);
//        for (int i = 0; i < 7; i++) {
//            Account student = Account.builder()
//                    .email(faker.internet().emailAddress())
//                    .password("$2a$10$O7sFLMUCXpwUyaJJgr0JSu/PdUrIcms9NzRD95HcqeCeej6I9mbD6")
//                    .role("ROLE_STUDENT")
//                    .enabled(1)
//                    .build();
//            em.persist(student);
//        }
//        em.flush();
    }
}