package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Classroom;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.ClassroomRepo;
import com.example.studentmanagement.repository.StudentRepo;
import com.example.studentmanagement.service.ClassroomService;
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
@RequestMapping("/admin/student")
public class ManageStudentController {
    @Autowired
    private ClassroomRepo classroomRepo;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepo studentRepo;

    @GetMapping("")
    public String showManageStudentPage(){
        return "admin/manageStudent";
    }

    @GetMapping("/all")
    public String getAll(Model model, @RequestParam(required = false, defaultValue = "1") int pageNumber) {
        Page<Student> studentPage = studentService.getAllStudentWithFilter(pageNumber, false);
        model.addAttribute("studentList", studentPage.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        return "admin/getAll";
    }

//    @GetMapping("/all")
//    public String getByKey(Model model, @RequestParam(required = false, defaultValue = "") String searchKey) {
//        Page<Student> studentPage = studentService.getAllStudentWithFilter(pageNumber, false);
//        model.addAttribute("studentList", studentPage.getContent());
//        model.addAttribute("pageNumber", pageNumber);
//        model.addAttribute("totalPages", studentPage.getTotalPages());
//        return "admin/getAll";
//    }

    @GetMapping("/deleted")
    public String getAllDeletedStudent(Model model, @RequestParam(required = false, defaultValue = "1") int pageNumber) {
        Page<Student> studentPage = studentService.getAllStudentWithFilter(pageNumber, true);
        model.addAttribute("studentList", studentPage.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        return "admin/getAllDeletedStudent";
    }

    @GetMapping("/add")
    public String showAddNew(Model model) {
        model.addAttribute("student", new Student());
        List<Classroom> classrooms = classroomService.getAllClassroom(false);
        model.addAttribute("classroomList", classrooms);
        return "admin/addNew";
    }

    @PostMapping("/add")
    public String addNew(@Valid Student student, BindingResult bd, Model model) {
        if (bd.hasErrors()) {
            return "admin/addNew";
        }
        String firstname = student.getFirstname();
        String lastname = student.getLastname();
        int age = student.getAge();
        Classroom choseClass = student.getClassroom();


        Student s = Student.builder()
                .firstname(firstname)
                .lastname(lastname)
                .age(age)
                .classroom(choseClass)
                .build();


        studentRepo.save(s);
        return "redirect:/admin/student/all";
    }

    @GetMapping("/edit/{id}")
    public String showStudentInfo(@PathVariable Integer id, Model model) {
        Optional<Student> s = studentRepo.findById(id);
        if (!s.isPresent()) {
            throw new NoSuchElementException("Student is not found");
        } else {
            Student student = s.get();
            model.addAttribute("student", student);
            List<Classroom> classrooms = classroomService.getAllClassroom(false);
            model.addAttribute("classroomList", classrooms);
            return "admin/edit";
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
            List<Classroom> classrooms = classroomService.getAllClassroom(false);
            model.addAttribute("classroomList", classrooms);
            return "admin/edit";
        }
        s.setFirstname(student.getFirstname());
        s.setLastname(student.getLastname());
        s.setAge(student.getAge());
        Classroom optionClassroom = student.getClassroom();
        Classroom c = classroomService.getClassroomById(optionClassroom.getId());
        s.setClassroom(c);

        studentRepo.save(s);
        return "redirect:/admin/student/all";
    }



    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Integer id, Model model) {
        Optional<Student> s = studentRepo.findById(id);
        if (s.isPresent()) {
            studentRepo.deleteById(id);

            return "redirect:/admin/student/all";
        } else {
            throw new NoSuchElementException("Student is not found");
        }
    }
}
