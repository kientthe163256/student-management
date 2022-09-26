package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepo extends JpaRepository<Classroom, Integer> {
}
