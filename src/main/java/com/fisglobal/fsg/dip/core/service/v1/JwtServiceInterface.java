package com.fisglobal.fsg.dip.core.service.v1;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface JwtServiceInterface {

	String GenerateToken(String username, HashMap<String, Object> hs);

	String GenerateToken(String username);

	String extractUsername(String token);

	String extractprovider(String token);

	Boolean validateToken(String token, UserDetails userDetails);
	
	Date extractExpiration(String token);
	
	<T> T extractClaim(String token, Function<Claims, T> claimsResolver);
	
	Claims extractAllClaims(String token);
	
	Boolean isTokenExpired(String token);
	
	String createToken(Map<String, Object> claims, String username);
	
	public  Key getSignKey();
	
}
