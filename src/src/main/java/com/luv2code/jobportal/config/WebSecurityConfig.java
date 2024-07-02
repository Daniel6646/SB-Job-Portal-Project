package com.luv2code.jobportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.luv2code.jobportal.service.CustomUserDetailServices;

@Configuration
public class WebSecurityConfig {

	@Autowired
	private CustomUserDetailServices customUserDetailServices;

	@Autowired
	CustomAuthenticationSucessHandler customAuthenticationSucessHandler;
	
	// urls spring security will not provide protection support
	//comon use regstring new user, common css, assets etc
	//basclyy acess these resources without having to authenticate
	private final String[] publicUrl = {"/",
            "/global-search/**",
            "/register",
            "/register/**",
            "/webjars/**",
            "/resources/**",
            "/assets/**",
            "/css/**",
            "/summernote/**",
            "/js/**",
            "/*.css",
            "/*.js",
            "/*.js.map",
            "/fonts**", "/favicon.ico", "/resources/**", "/error"};
	
	@Bean // so spring security is aware of this security filterchain
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authenticationProvider(authenticationProvider()); 
		
		http.authorizeHttpRequests( auth ->	{
		auth.requestMatchers(publicUrl).permitAll();// list of public url which will not need to login 
		auth.anyRequest().authenticated();// any other request user will have to give userid and pass to login
		});
		
		
		//setup form login
		//anyone can acess login page without actually having to login
		http.formLogin(form ->form.loginPage("/login").permitAll()
		 .successHandler(customAuthenticationSucessHandler)) 
//config for logout	
		.logout(logout -> {
		logout.logoutUrl("/logout");
//config for sucessfull logout	

		logout.logoutSuccessUrl("/");
		}).cors(Customizer.withDefaults())
		.csrf(csrf -> csrf.disable());
		
		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(customUserDetailServices);//tell spring how to retrieve users from database
		
		return authenticationProvider;
	
	}

	//custom password encoder
	//tells spring security how to authenticate password (plain text or encryption) we will use encryption
	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}






}

