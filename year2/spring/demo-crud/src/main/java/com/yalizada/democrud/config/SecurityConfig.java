package com.yalizada.democrud.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
               
                .antMatchers(HttpMethod.GET,"/createUserShowForm").permitAll()
                .antMatchers(HttpMethod.POST,"/createUserProcess").permitAll()
                .antMatchers(HttpMethod.GET,"/").hasAnyRole("DELETOR","ADMIN","EDITOR","CREATOR")
                .antMatchers(HttpMethod.GET,"/delete").hasAnyRole("DELETOR","ADMIN")
                .antMatchers(HttpMethod.GET,"/edit/**").hasAnyRole("EDITOR","ADMIN")
                .antMatchers(HttpMethod.POST,"/adduser").hasAnyRole("CREATOR","ADMIN")
                .antMatchers(HttpMethod.GET,"/signup").hasAnyRole("CREATOR","ADMIN")
                
                
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/showMyLoginPage")
             
                .loginProcessingUrl("/authenticateTheUser").permitAll()
                .and()
                .logout()
                .permitAll()
                 ;
        
        
       
		;
    }
	

	@Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
            
    }
}
