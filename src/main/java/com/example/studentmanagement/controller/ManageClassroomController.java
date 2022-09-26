package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Classroom;
import com.example.studentmanagement.repository.ClassroomRepo;
import com.example.studentmanagement.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/classroom")
public class ManageClassroomController {
    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private ClassroomRepo classroomRepo;

    @GetMapping("/all")
    public String getAllClass(Model model){
        List<Classroom> classrooms = classroomService.getAllClassroom(false);
        model.addAttribute("classroomList", classrooms);
        return "admin/getAllClass";
    }


    @GetMapping("/deleted")
    public String getAllDeletedClass(Model model){
        List<Classroom> classrooms = classroomService.getAllClassroom(true);
        model.addAttribute("classroomList", classrooms);
        return "admin/allDeletedClassroom";
    }

    @GetMapping("/delete/{id}")
    public String deleteClass(@PathVariable(name = "id") int classId, Model model){
        classroomService.deleteClass(classId);
        model.addAttribute("message", "Classroom deleted successfully!");
        return "redirect:/classroom/all";
    }
}
