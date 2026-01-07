package com.fisglobal.fsg.dip.core.service.v1;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements JwtServiceInterface {
	public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
	public static final long JWT_TOKEN_VALIDITY = 5L * 60 * 60;
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);

	@Override
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	@Override
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	@Override
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	@Override
	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	@Override
	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {
		LOGGER.info("Validating Auth Token");
		final String userid = extractUsername(token);
		LOGGER.info("TokenID[{}] DBID[{}] Expiry[{}]", userid, userDetails.getUsername(), isTokenExpired(token));
		LOGGER.info("Validating Auth Token [{}]", (userid.equals(userDetails.getUsername()) && !isTokenExpired(token)));
		return (userid.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	@Override
	public String GenerateToken(String username) {
		Map<String, Object> claims = new HashMap();
		return createToken(claims, username);
	}

	@Override
	public String createToken(Map<String, Object> claims, String username) {

		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 100))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	@Override
	public Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public String GenerateToken(String username, HashMap<String, Object> hs) {
		return createToken(hs, username);
	}

	@Override
	public String extractprovider(String token) {
		final Claims claims = extractAllClaims(token);
		return (String) claims.get("provider");
	}

}
