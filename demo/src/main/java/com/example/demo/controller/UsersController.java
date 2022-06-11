package com.example.demo.controller;

import com.example.demo.entity.ApplicationUser;
import com.example.demo.exceptionHandling.NoUsersExistException;
import com.example.demo.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/users")
public class UsersController {

    @Autowired
    ApplicationUserService applicationUserService;

    @GetMapping("/allUsers")
    public ResponseEntity<List<ApplicationUser>> getAllUsers(){
        List<ApplicationUser> users= applicationUserService.getAllUsers();

        if(users==null || users.size()==0){
            throw new NoUsersExistException("No application users found");
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<String> addNewUser(@RequestBody ApplicationUser user){
        applicationUserService.addNewUser(user);

        return new ResponseEntity<>(user.toString(), HttpStatus.OK);
    }
}
