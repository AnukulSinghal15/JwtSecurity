package com.example.demo.controller;

import com.example.demo.exceptionHandling.StudentNotFoundException;
import com.example.demo.exceptionHandling.StudentParametersException;
import com.example.demo.models.Student;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/management/students")
public class StudentManagementController {

    private static ArrayList<Student> students= new ArrayList<>(){
        {
            add(new Student(1, "James Bond"));
            add(new Student(2, "Maria Jones"));
            add( new Student(3, "Albert Pinto"));
            add( new Student(4, "Raj Gupta"));
        }
    };

    @GetMapping("/allStudents")
    public ResponseEntity<List<Student>> getAllStudents(){
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/getStudentById/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Integer id){

        Student student= students.stream()
                .filter(s-> id.equals(s.getId()))
                .findAny()
                .orElse(null);

        if(student==null){
            throw new StudentNotFoundException("Student with id: "+id+" not found");
        }

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/addStudent")
    public ResponseEntity<Student> addNewStudent(@RequestBody Student student){

        student.setId(students.get(students.size()-1).getId()+1);
        students.add(student);

        if(student.getName()==null){
            throw new StudentParametersException("Please add name");
        }
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

    @DeleteMapping("/deleteStudent/{id}")
    @PreAuthorize("hasAuthority('student:write')")  //Annotation based authentication
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Integer id){

        ResponseEntity<Student> response= getStudentById(id);
        Student studentToDelete= response.getBody();

        if(studentToDelete==null){
            throw new StudentNotFoundException("Student with id: "+id+" not found");
        }
        students.remove(studentToDelete);
        return new ResponseEntity<>("Student: "+studentToDelete.toString()+" is deleted.", HttpStatus.OK);
    }
}
