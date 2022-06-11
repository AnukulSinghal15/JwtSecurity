package com.example.demo.service;

import com.example.demo.dao.ApplicationUserDAO;
import com.example.demo.entity.ApplicationUser;
import com.example.demo.models.ApplicationUserDetails;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationUserService implements UserDetailsService {

    Logger logger= LoggerFactory.getLogger(ApplicationUserService.class);

    @Autowired
    ApplicationUserDAO applicationUserDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ApplicationUser applicationUser= applicationUserDAO.findByUsername(username);
        logger.info(applicationUser.toString());
        if(applicationUser==null){
            throw new UsernameNotFoundException("User not found");
        }
        ApplicationUserDetails applicationUserDetails= new ApplicationUserDetails(applicationUser);
        logger.info(applicationUserDetails.toString());
        return applicationUserDetails;

    }

    public List<ApplicationUser> getAllUsers() {
        return applicationUserDAO.findAll();
    }

    public void addNewUser(ApplicationUser user) {
        applicationUserDAO.save(user);
    }
}
