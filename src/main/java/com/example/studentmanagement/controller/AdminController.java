package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Classroom;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.ClassroomRepo;
import com.example.studentmanagement.repository.StudentRepo;
import com.example.studentmanagement.service.ClassroomService;
import com.example.studentmanagement.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequestMapping("/admin")
@Controller
public class AdminController {
    @Autowired
    private ClassroomRepo classroomRepo;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepo studentRepo;

    @GetMapping("")
    public String getAdminPage(){
        return "admin/adminHomePage";
    }

    @GetMapping("/classroom")
    @ResponseBody
    public List<Classroom> getAllClassroom(){
        return classroomRepo.findAll();
    }


}
