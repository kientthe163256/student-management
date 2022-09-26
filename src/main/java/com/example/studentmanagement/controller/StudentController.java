package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Classroom;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepo;
import com.example.studentmanagement.response.StudentResponse;
import com.example.studentmanagement.service.ClassroomService;
import com.example.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassroomService classroomService;

    @GetMapping("")
    public String getHome() {
        return "studentHomePage";
    }

    @GetMapping("/classroom")
    public ResponseEntity<List<StudentResponse>> getMyClassroom(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Student s = studentRepo.findByEmail(email);
        List<Student> students = studentService.getStudentByClassID(s.getClassroom().getId());
        List<StudentResponse> studentResponses = students.stream()
                .map(student -> mapStudentToResponse(student))
                .collect(Collectors.toList());
        return new ResponseEntity<>(studentResponses, HttpStatus.OK);
    }

    @GetMapping("/classroom2")
    public String getClassmate(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Student s = studentRepo.findByEmail(email);
        List<Student> students = studentService.getStudentByClassID(s.getClassroom().getId());
        model.addAttribute("studentList", students);
        return "myclass";
    }

    public StudentResponse mapStudentToResponse(Student student){
        StudentResponse studentResponse = StudentResponse.builder()
                .email(student.getEmail())
                .age(student.getAge())
                .fullname(student.getFirstname() + ' ' + student.getLastname())
                .classroom(student.getClassroom().getClassName())
                .build();
        return studentResponse;
    }



}
