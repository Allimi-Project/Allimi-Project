package com.allimi.modulebatch.processor;

import com.allimi.modulecore.domain.news.Code;
import com.allimi.modulecore.dto.KotraNewsResponse;
import com.allimi.modulecore.domain.news.News;
import com.allimi.modulecore.util.EnumConverter;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class KotraNewsProcessor implements ItemProcessor<KotraNewsResponse.Item, News> {

	private final EnumConverter enumConverter = new EnumConverter();
	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public News process(KotraNewsResponse.Item item) throws Exception {

		String infoCl = item.getIndstCl();
		Code code = enumConverter.convertToEntityAttribute(infoCl);
		LocalDateTime parseDate = LocalDateTime.parse(item.getOthbcDt(), dateFormatter);

		return News.builder()
				.articleId(item.getBbstxSn())             // bbstxSn → articleId
				.newsTitl(item.getNewsTitl())             // newsTitl → title
				.kotraNewsUrl(item.getKotraNewsUrl())     // kotraNewsUrl → url
				.regn(item.getRegn())                     // regn → region
				.fileLink(item.getFileLink())             // fileLink 그대로 사용
				.code(code)   	  			 			  // Code 객체 사용
				.othbcDt(parseDate)                       // othbcDt → publishDate (파싱된 날짜)
				.newsWrterNm(item.getNewsWrterNm())       // newsWrterNm → writerName
				.infoCl(item.getInfoCl())                 // infoCl → infoCategory
				.natn(item.getNatn())                     // natn → nation
				.build();
	}
}