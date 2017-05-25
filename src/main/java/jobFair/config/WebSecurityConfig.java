/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.config;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author justinas
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
    DataSource dataSource;
    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
      auth.jdbcAuthentication().dataSource(dataSource)
        .passwordEncoder(passwordEncoder())
        .usersByUsernameQuery(
         "select username,password, 'true' from users where username=?")
        .authoritiesByUsernameQuery(
         "select username, role from users where username=?");
    } 
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
//        .antMatchers("/hello").access("hasRole('ADMIN')")  
//        .antMatchers("/admin", "/signupadmin", "/deleteadmin", "/setdeadline", "/linkSpot", "/endmail").access("hasRole('ADMIN')") 
//        .antMatchers("/account", "/updatepassword", "/companies", "/companiesByContact", "/companiesByEmail", "/companiesBySpot", "/downloadCompanies").access("hasRole('COMPANY')")  
//        .antMatchers("/updatespot", "/myspot", "/cancelspot", "/confirmspotcancel", "/confirmupdatespot").access("hasRole('COMPANY')")  
//        .antMatchers("/signupcompany", "/saveuserscvs", "/deletecompanies", "/deleteCompany").access("hasRole('ADMIN')") 
//        .antMatchers("/showopt&id={id}", "/spotadminoptions", "/spotoptions").access("hasRole('ADMIN') or hasRole('COMPANY')")               
        .anyRequest().permitAll()
        .and()
          .formLogin().loginPage("/login")
          .usernameParameter("username").passwordParameter("password")
         .and()
         .exceptionHandling().accessDeniedPage("/403")
        .and()
          .csrf();
    }
    

   
}
