package com.fisglobal.fsg.dip.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fisglobal.fsg.dip.core.config.AuthEntryPoint;
import com.fisglobal.fsg.dip.core.config.CustomUserDetailsAuthenticationProvider;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity(debug = true)
//@EnableMethodSecurity
public class SpringConfig extends WebSecurityConfigurerAdapter {

	public static PasswordEncoder passwordEncoderStatic;
	
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	AuthEntryPoint authEntryPoint;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

//		auth.authenticationProvider(customLdapAuthenticator);
		auth.authenticationProvider(new CustomUserDetailsAuthenticationProvider(userDetailsService, passwordEncoder()));

	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		PasswordEncoder newPass =new BCryptPasswordEncoder();
		passwordEncoderStatic = newPass;
		return newPass;
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService((UserDetailsService) userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		/*
		 * httpSecurity.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
		 * .authorizeRequests(requests -> requests.antMatchers("/", "/static/**",
		 * "/app/v1/login", "/actuator/**") .permitAll().anyRequest().authenticated())
		 * .exceptionHandling(handling ->
		 * handling.authenticationEntryPoint(authEntryPoint))
		 * .sessionManagement(management ->
		 * management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		 */
		/*
		 * httpSecurity.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
		 * .authorizeRequests(requests -> requests.antMatchers("/", "/static/**",
		 * "/app/v1/login", "/actuator/**")
		 * .permitAll().anyRequest().authenticated()).httpBasic();
		 */

		httpSecurity.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
				.authorizeRequests(requests -> requests.antMatchers(HttpMethod.OPTIONS).permitAll())
//				.authorizeRequests(requests -> requests.antMatchers(HttpMethod.POST).permitAll())
				.authorizeRequests(requests -> requests.antMatchers("/", "/static/**", "/app/v1/login", "/actuator/**")
						.permitAll().anyRequest().authenticated())
				.exceptionHandling(handling -> handling.authenticationEntryPoint(authEntryPoint))
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// httpSecurity.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
		httpSecurity.httpBasic(basic -> basic.authenticationEntryPoint(authEntryPoint));
		httpSecurity.headers(headers -> headers.frameOptions().disable());
		httpSecurity.headers();
	}

}
