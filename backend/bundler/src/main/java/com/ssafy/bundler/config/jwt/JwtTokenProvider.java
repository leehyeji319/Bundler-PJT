package com.ssafy.bundler.config.jwt;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ssafy.bundler.config.auth.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

	@Value("${app.jwt.secret}")
	private String secret;

	private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	private String base64Key = Encoders.BASE64.encode(key.getEncoded());

	@Value("${app.jwt.accessTokenPeriod}")
	private Long accessTokenPeriod;

	@Value("${app.jwt.refreshTokenPeriod}")
	private Long refreshTokenPeriod;

	public JwtToken createJwtToken(Authentication authentication) {
		UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

		Date now = new Date();
		Date accessTokenExpireDate = new Date(now.getTime() + accessTokenPeriod);
		Date refreshTokenExpireDate = new Date(now.getTime() + refreshTokenPeriod);

		String accessToken = Jwts.builder()
			.setSubject(userPrincipal.getUsername())
			.setIssuedAt(now)
			.setExpiration(accessTokenExpireDate)
			.signWith(key)
			.compact();

		String refreshToken = Jwts.builder()
			.setSubject(userPrincipal.getUsername())
			.setIssuedAt(now)
			.setExpiration(refreshTokenExpireDate)
			.signWith(key)
			.compact();

		return new JwtToken(accessToken, refreshToken);
	}

	public JwtToken createJwtToken(String userId) {
		Date now = new Date();
		Date accessTokenExpireDate = new Date(now.getTime() + accessTokenPeriod);
		Date refreshTokenExpireDate = new Date(now.getTime() + refreshTokenPeriod);

		log.info("createJwtToken() - userId : " + userId);

		String accessToken = Jwts.builder()
			.setSubject(userId)
			.setIssuedAt(now)
			.setExpiration(accessTokenExpireDate)
			.signWith(key)
			.compact();

		log.info("accessToken ?????? ??????");

		String refreshToken = Jwts.builder()
			.setSubject(userId)
			.setIssuedAt(now)
			.setExpiration(refreshTokenExpireDate)
			.signWith(key)
			.compact();

		log.info("refreshToken ?????? ??????");

		return new JwtToken(accessToken, refreshToken);
	}

	public JwtToken createJwtTokenWithDate(String username, int year, int month, int day) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.set(year, month, day);

		Date date = new Date(gregorianCalendar.getTime().getTime());

		Date accessTokenExpireDate = new Date(date.getTime() + accessTokenPeriod);
		Date refreshTokenExpireDate = new Date(date.getTime() + refreshTokenPeriod);

		String accessToken = Jwts.builder()
			.setSubject(username)
			.setIssuedAt(date)
			.setExpiration(accessTokenExpireDate)
			// ????????? ??? ?????? ????????? ?????? ???????????? base64??? ???????????? ????????? ?????? ??????????????? ?????? JavaDoc??? ??? ?????? ?????? ????????? ?????? ???????????? ????????????.
			.signWith(key)
			.compact();

		String refreshToken = Jwts.builder()
			.setSubject(username)
			.setIssuedAt(date)
			.setExpiration(refreshTokenExpireDate)
			.signWith(key)
			.compact();

		return new JwtToken(accessToken, refreshToken);
	}

	private Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

	public Long getUserId(String token) {
		return Long.valueOf(parseClaims(token)
			.getSubject());
	}

	public boolean verifyToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);

			return true;
		} catch (SignatureException e) { // ???????????? ?????? JWT ??????
			//             log.error("???????????? ?????? JWT ??????");
		} catch (MalformedJwtException e) { // ???????????? ?????? JWT
			//             log.error("???????????? ?????? JWT");
		} catch (ExpiredJwtException e) { // ????????? JWT
			//             log.error("????????? JWT");
		} catch (UnsupportedJwtException e) { // ???????????? ?????? JWT
			//             log.error("???????????? ?????? JWT");
		} catch (IllegalArgumentException e) { // ??????
			//             log.error("???????????? ??????");
		}

		return false;
	}

}