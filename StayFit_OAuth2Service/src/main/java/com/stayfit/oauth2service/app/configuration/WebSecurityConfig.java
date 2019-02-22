/**
 * 
 */
package com.stayfit.oauth2service.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.stayfit.oauth2service.app.service.UserDetailsServiceImpl;

/**
 * 
 * 
 * The @EnableWebSecurity It allows Spring to find and automatically apply the class to the global WebSecurity.
 * The @EnableWebSecurity annotation and WebSecurityConfigurerAdapter work together to provide security to the application. 
 */

@Configuration
@EnableWebSecurity
@Import(Encoders.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder userPasswordEncoder;

	@Bean
	public UserDetailsService userDetailsService() {
	  return new UserDetailsServiceImpl();
	};

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(userPasswordEncoder);
	}

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    
}
