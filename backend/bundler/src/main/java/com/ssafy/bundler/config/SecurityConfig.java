package com.ssafy.bundler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.ssafy.bundler.config.jwt.JwtAuthenticationFilter;
import com.ssafy.bundler.config.jwt.JwtAuthorizationFilter;
import com.ssafy.bundler.repository.UserRepository;

// https://github.com/spring-projects/spring-security/issues/10822 참고
@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfig {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CorsConfig corsConfig;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.formLogin().disable()
			.httpBasic().disable()
			.apply(new MyCustomDsl()) // 커스텀 필터 등록
			.and()
			// .authorizeHttpRequests(authroize ->
			// 		authroize
			// 			.requestMatchers("/api/v1/auth/**").hasRole("ROLE_USER")
			// 			.requestMatchers("/api/v1/auth/manager/**").hasRole("ROLE_MANAGER")
			// 			.requestMatchers("/api/v1/auth/admin/**").hasRole("ROLE_ADMIN")
			// )
			.authorizeRequests(authroize -> //deprecated
				authroize.requestMatchers("/api/v1/auth/user/**")
					.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
					.requestMatchers("/api/v1/auth/manager/**")
					.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
					.requestMatchers("/api/v1/auth/admin/**")
					.access("hasRole('ROLE_ADMIN')")
					.anyRequest().permitAll()
			)
			.build();
	}

	public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			http
				.addFilter(corsConfig.corsFilter())
				.addFilter(new JwtAuthenticationFilter(authenticationManager))
				.addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository));
		}
	}

}
