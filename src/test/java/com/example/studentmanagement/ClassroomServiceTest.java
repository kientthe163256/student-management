package com.example.studentmanagement;

import com.example.studentmanagement.entity.Classroom;
import com.example.studentmanagement.repository.ClassroomRepo;
import com.example.studentmanagement.service.ClassroomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ClassroomServiceTest {
    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private ClassroomRepo classroomRepo;

    @Test
    public void deleteClassroom() {
        int classId = 1;
        classroomService.deleteClass(classId);
        Optional<Classroom> deletedClassroom = classroomRepo.findById(classId);
        assertThat(deletedClassroom).isNull();
    }

    @Test
    public void getAllClassroom(){
        List<Classroom> classrooms = classroomRepo.findAll();
        assertThat(classrooms).isNotNull();
    }

    @Test
    public void getDeletedClassroom(){
        List<Classroom> classrooms = classroomService.getAllClassroom(false);
        assertThat(classrooms).isNotNull();
    }

    @Test
    public void recoverClassroom(){
        Classroom c = classroomService.recoverClassroom(20);
        assertThat(c.isDeleted()).isFalse();
    }
}
