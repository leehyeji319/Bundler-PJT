package com.ssafy.bundler.dto.bundle.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.bundler.domain.FeedType;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 번들 조회시 사용되는 dto
 *
 * @author 이혜지
 * @version 1.0
 */

@Data
@RequiredArgsConstructor
public class BundleResponseDto {

	private Long bundleId;
	private String feedType = FeedType.BUNDLE.toString();

	private LocalDateTime createdAt;

	private Long bundleWriterId;
	private String bundleWriterProfileImage;
	private String bundleWriterNickname;

	private String feedTitle;
	private String feedContent;

	private String bundleThumbnail;
	private String bundleThumbnailText;

	List<CardBundleQueryDto> cardBundleQueryDtoList;

	public BundleResponseDto(Long bundleId, LocalDateTime createdAt, Long bundleWriterId,
		String bundleWriterProfileImage, String bundleWriterNickname, String feedTitle, String feedContent,
		String bundleThumbnail, String bundleThumbnailText) {
		this.bundleId = bundleId;
		this.createdAt = createdAt;
		this.bundleWriterId = bundleWriterId;
		this.bundleWriterProfileImage = bundleWriterProfileImage;
		this.bundleWriterNickname = bundleWriterNickname;
		this.feedTitle = feedTitle;
		this.feedContent = feedContent;
		this.bundleThumbnail = bundleThumbnail;
		this.bundleThumbnailText = bundleThumbnailText;
	}

}
