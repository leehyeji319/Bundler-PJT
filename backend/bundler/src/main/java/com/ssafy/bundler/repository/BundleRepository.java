package com.ssafy.bundler.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bundler.domain.Bundle;

public interface BundleRepository extends JpaRepository<Bundle, Long> {

	@Query("select new com.ssafy.bundler.dto.feed.UserBundleListSummary"
		+ "(b.bundleId, b.feedTitle) from Bundle b where b.writer.userId = :userId")
	List<UserBundleListSummary> findAllFeedTitleByUserId(@Param("userId") Long userId);

	@Query("select b.bundleId from Bundle b where b.writer.userId =: userId")
	List<Long> findBundleIdsByUserId(@Param("userId") Long userId);
}