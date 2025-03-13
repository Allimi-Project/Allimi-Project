package com.allimi.modulecore.domain.news;

import com.allimi.modulecore.common.BaseEntity;
import com.allimi.modulecore.util.EnumConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News extends BaseEntity {

	@Id
	@Column(name = "article_id")
	private String articleId;	//게시글 일련번호

	@Column(name = "newsTitl")	//뉴스제목
	private String newsTitl;

	@Column(name = "kotraNewsUrl")	//게시물URL
	private String kotraNewsUrl;

	@Column(name = "regn")	//지역
	private String regn;

	@Column(name = "fileLink")	//파일다운로드LINK
	private String fileLink;

	@Convert(converter = EnumConverter.class)
	@Column(name = "code")  // 분류코드
	private Code code;

	@Column(name = "othbcDt")	//뉴스게시일자
	private LocalDateTime othbcDt;

	@Column(name = "newsWrterNm")	//뉴스작성자명
	private String newsWrterNm;

	@Column(name = "infoCl")	//정보분류
	private String infoCl;

	@Column(name = "natn")	//국가
	private String natn;

	@Builder
	private News(String articleId, String newsTitl, String kotraNewsUrl, String regn, String fileLink, Code code, LocalDateTime othbcDt,
				 String newsWrterNm, String infoCl, String natn) {
		this.articleId = articleId;
		this.newsTitl = newsTitl;
		this.kotraNewsUrl = kotraNewsUrl;
		this.regn = regn;
		this.fileLink = fileLink;
		this.code = code;
		this.othbcDt = othbcDt;
		this.newsWrterNm = newsWrterNm;
		this.infoCl = infoCl;
		this.natn = natn;
	}
}
