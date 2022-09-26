package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Account;
import com.example.studentmanagement.entity.Classroom;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.AccountRepo;
import com.example.studentmanagement.repository.StudentRepo;
import com.example.studentmanagement.service.ClassroomService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.service.Response;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentRestController {
    private final StudentRepo studentRepo;

    private final ClassroomService classroomService;

    private final AccountRepo accountRepo;

    public StudentRestController(StudentRepo studentRepo, ClassroomService classroomService, AccountRepo accountRepo) {
        this.studentRepo = studentRepo;
        this.classroomService = classroomService;
        this.accountRepo = accountRepo;
    }

    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable int id){
        Student student = studentRepo.findById(id).get();
        return student;
    }

    @GetMapping("/classroom/{id}")
    public Classroom getClassroomById(@PathVariable int id){
        return classroomService.getClassroomById(id);
    }

    @GetMapping("/account")
    public List<Account> getAllAccount(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "*");
        return accountRepo.findAll();
    }

}
