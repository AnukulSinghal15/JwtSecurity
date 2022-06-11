package com.example.demo.dao;

import com.example.demo.entity.ApplicationUser;
import com.example.demo.models.ApplicationUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserDAO extends JpaRepository<com.example.demo.entity.ApplicationUser, Long> {

    ApplicationUser findByUsername(String username);
}
