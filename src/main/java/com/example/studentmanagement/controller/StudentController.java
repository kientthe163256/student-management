package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepo;
import com.example.studentmanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private StudentService studentService;

    @GetMapping("")
    public String getHome() {
        return "index";
    }


    @GetMapping("/all")
    public String getAll(Model model, @RequestParam(required = false, defaultValue = "1") int pageNumber) {
        Page<Student> studentPage = studentService.getAllStudent(pageNumber);
        model.addAttribute("studentList", studentPage.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        return "getall";
    }

    @GetMapping("/add")
    public String showAddNew(Model model) {
        model.addAttribute("student", new Student());
        return "addnew";
    }

    @PostMapping("/add")
    public String addNew(@Valid Student student, BindingResult bd, Model model) {
        if (bd.hasErrors()) {
            return "addnew";
        }
        String firstname = student.getFirstname();
        String lastname = student.getLastname();
        int age = student.getAge();

        Student s = Student.builder()
                .firstname(firstname)
                .lastname(lastname)
                .age(age)
                .build();


        studentRepo.save(s);
        return "redirect:/student/all";
    }

    @GetMapping("/edit/{id}")
    public String showStudentInfo(@PathVariable Integer id, Model model) {
        Optional<Student> s = studentRepo.findById(id);
        if (!s.isPresent()) {
            throw new NoSuchElementException("Student is not found");
        } else {
            Student student = s.get();
            model.addAttribute("student", student);
            return "edit";
        }
    }

    @PostMapping("/edit/{id}")
    public String editStudentInfo(@PathVariable Integer id, @Valid Student student, BindingResult bd, Model model) {
        Optional<Student> stu = studentRepo.findById(id);
        if (!stu.isPresent()) {
            throw new NoSuchElementException("Student is not found");
        }
        Student s = stu.get();
        if (bd.hasErrors()) {
            return "edit";
        }
        s.setFirstname(student.getFirstname());
        s.setLastname(student.getLastname());
        s.setAge(student.getAge());

        studentRepo.save(s);
        return "redirect:/student/all";
    }

    @PutMapping("/edit/{id}")
    public String testapi(@PathVariable Integer id, @Valid Student student, BindingResult bd, Model model) {
        Optional<Student> s = studentRepo.findById(id);
        if (!s.isPresent()) {
            throw new NoSuchElementException("Student is not found");
        }
        Student editStudent = s.get();

        if (bd.hasErrors()) {
            return "edit";
        }
        editStudent.setFirstname(student.getFirstname());
        editStudent.setLastname(student.getLastname());
        editStudent.setAge(student.getAge());

        studentRepo.save(editStudent);


        return "redirect:/student/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Integer id, Model model) {
        Optional<Student> s = studentRepo.findById(id);
        if (s.isPresent()) {
            studentRepo.deleteById(id);

            return "redirect:/student/all";
        } else {
            throw new NoSuchElementException("Student is not found");
        }
    }
}
