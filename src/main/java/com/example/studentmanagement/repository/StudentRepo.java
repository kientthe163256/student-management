package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    Student findStudentById(int id);
    @Query(value = "UPDATE student SET deleted = false WHERE id=?1", nativeQuery = true)
    @Modifying
    void recoverStudent(int id);

    Student findByEmail(String email);

    @Query(value = "SELECT * FROM student where concat(firstname, ' ', lastname, ' ', email) like %:key%", nativeQuery = true)
    List<Student> searchStudent(@Param("key") String searchKey);

}
