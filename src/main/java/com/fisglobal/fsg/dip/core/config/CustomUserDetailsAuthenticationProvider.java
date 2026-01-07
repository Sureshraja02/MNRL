package com.fisglobal.fsg.dip.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsAuthenticationProvider implements AuthenticationProvider {
	
	private final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsAuthenticationProvider.class);
	private final PasswordEncoder passwordEncoder;

	private final UserDetailsService userDetailsService;

	public CustomUserDetailsAuthenticationProvider(UserDetailsService userDetailService,
			@Lazy PasswordEncoder passwordEncoder) {
		this.userDetailsService = userDetailService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String securekeyword = null;
		if (authentication.getCredentials()!=null) {
			securekeyword = authentication.getCredentials().toString();
		}
		//String keyword = authentication.getCredentials().toString();

		System.out.println("username : " + username);
		System.out.println("APP U.Pw : "+securekeyword );
		LOGGER.info("UsrName : [{}]", username);
		LOGGER.info("Usr Secure Term CLear : [{}]", securekeyword);
		LOGGER.info("Usr Secure Term : [{}]", passwordEncoder.encode(securekeyword));
		//System.out.println("password to use : " + passwordEncoder.encode("Admin@123"));
		//System.out.println("password to use Encrypted : " + passwordEncoder.encode(securekeyword));
		UserDetails user = userDetailsService.loadUserByUsername(username);
		
		//System.out.println("db password : " + user.getPassword());
		if (this.matches(securekeyword, user.getPassword())) {
			return new CustomAuthenticationToken(username, securekeyword, user.getAuthorities(), "JDBC", null);
		} else {
			throw new BadCredentialsException("Authentication Failed");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private boolean matches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

}
