package com.example.demo.security;

import com.example.demo.jwt.JwtTokenVerifier;
import com.example.demo.jwt.JwtUsernamePasswordAuthentication;
import com.example.demo.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //to tell security that we are also using annotations on methods for authentication
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationUserService userService;

    @Autowired
    public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder){
        this.passwordEncoder= passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http                                                 //with httponlyfalse, this cookie will not be accessible to client side
                                                            //no attack on csrf token possible with JS
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.and()
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernamePasswordAuthentication(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(),JwtUsernamePasswordAuthentication.class)
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers(HttpMethod.GET, "/management/**")
                            .hasAnyRole(ApplicationUserRoles.ADMIN.name(), ApplicationUserRoles.ADMINTRAINEE.name())
                .antMatchers(HttpMethod.POST, "/management/users/**").hasRole(ApplicationUserRoles.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/management/students/**").hasAuthority(ApplicationPermissions.STUDENT_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/management/students/**").hasAuthority(ApplicationPermissions.STUDENT_WRITE.getPermission())
                //using @PreAuthorize on Delete as example
                //.antMatchers(HttpMethod.DELETE, "/management/students/**").hasAuthority(ApplicationPermissions.STUDENT_WRITE.getPermission())
                .anyRequest().authenticated()
                .and()
//                .httpBasic(); //no logout functionality with basic authentication
//                .formLogin() //client sends sessionId, then validates sessionId till id is alive, therefore no need to sign in again and again
//                    .loginPage("/login").permitAll()  //custom login page
//                    .defaultSuccessUrl("/postLogin", true)
//                    .passwordParameter("password") //"name" of password field in the form
//                    .usernameParameter("username")
//                .and()
//                .rememberMe()
//                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
//                    .rememberMeParameter("remember-me")
//                .and()
//                .logout()
//                    .logoutUrl("/logout")// if csrf is enabled, then request must be post
//                    .clearAuthentication(true)
//                    .invalidateHttpSession(true)
//                    .deleteCookies("JSESSIONID","remember-me","XSRF-TOKEN")
//                    .logoutSuccessUrl("/login")
                   ;
    }

//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails anukul = User.builder()
//                .username("anukul")
//                .password(passwordEncoder.encode("anukul"))
//                .roles(ApplicationUserRoles.ADMIN.name())
//                .authorities(ApplicationUserRoles.ADMIN.getGrantedAuthoirities())
//                .build();
//
//        UserDetails aman = User.builder()
//                .username("aman")
//                .password(passwordEncoder.encode("aman"))
//                .roles(ApplicationUserRoles.ADMINTRAINEE.name())
//                .authorities(ApplicationUserRoles.ADMINTRAINEE.getGrantedAuthoirities())
//                .build();
//
//        UserDetails rahul = User.builder()
//                .username("rahul")
//                .password(passwordEncoder.encode("rahul"))
//                .roles(ApplicationUserRoles.STUDENT.name())
//                .authorities(ApplicationUserRoles.STUDENT.getGrantedAuthoirities())
//                .build();
//
//        return new InMemoryUserDetailsManager(
//                anukul,aman,rahul
//        );
//    }

}
