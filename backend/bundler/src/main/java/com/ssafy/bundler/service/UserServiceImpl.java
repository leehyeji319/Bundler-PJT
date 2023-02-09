package com.ssafy.bundler.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bundler.domain.User;
import com.ssafy.bundler.dto.AuthResponseDto;
import com.ssafy.bundler.dto.SignupRequestDto;
import com.ssafy.bundler.dto.UserUpdateRequestDto;
import com.ssafy.bundler.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	// private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User getUserByUserNickname(String userNickname) {
		return userRepository.findByUserNickname(userNickname).orElseThrow();
	}

	@Override
	public AuthResponseDto createUser(SignupRequestDto request) {
		User userEntity = userRepository.save(
			request.toEntity()
				.toBuilder()
				.userRole("ROLE_USER")
				.build());

		return AuthResponseDto.builder()
			.userNickname(userEntity.getUserNickname())
			.build();
	}

	@Override
	public void updateUser(UserUpdateRequestDto user) {
		User u = userRepository.findByUserId(user.getUserId()).orElseThrow();

		userRepository.save(u.toBuilder()
			.userIntroduction(user.getUserIntroduction())
			.userProfileImage(user.getUserProfileImageUrl())
			.userNickname(user.getUserNickname())
			.build()
		);
	}

	@Override
	public void deleteUser(Long userId) {
		User u = userRepository.findByUserId(userId).orElseThrow()
			.toBuilder()
			.isDeleted(false)
			.build();
		userRepository.save(u);
	}

	// @Override
	// public User getUserByUserId(Long userId) {
	// 	return null;
	// }

}