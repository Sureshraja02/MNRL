package com.fisglobal.fsg.dip.core.service.v1;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisglobal.fsg.dip.core.entity.repo.UserRepo;
import com.fisglobal.fsg.dip.entity.User_Master;

@Service
public class UserLoginService implements UserDetailsService {

	@Autowired
	UserRepo loginRepository;

	@Autowired
	ObjectMapper obj;

	@Autowired(required = false)
	PasswordEncoder encoder;

	public static String i4cEncDecKeyStatic = new String();	
	
	public UserLoginService(@Lazy PasswordEncoder keywordService) {
		this.encoder = keywordService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		System.out.println("user name to search : " + username);
		Optional<User_Master> Optuser = loginRepository.findByUserusingUserNameNChannel(username, "SELF");
		
		if (Optuser.isPresent()) {
			System.out.println("User is present");
			i4cEncDecKeyStatic = Optuser.get().getApiEncDecKey();
			try {
				System.out.println(obj.writeValueAsString(Optuser.get()));

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(Optuser.get());
			return new CustomUserDetails(Optuser.get().getUsername(), Optuser.get().getPassword());

		}
		throw new UsernameNotFoundException("could not found user..!!");

	}

}
