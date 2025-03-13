package com.allimi.modulecore.repository;

import com.allimi.modulecore.domain.news.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NewsRepository extends JpaRepository<News, Long> {

	@Modifying(clearAutomatically = true)
	@Query(value = """
    INSERT INTO News(
        articleId, 
        newsTitl, 
        kotraNewsUrl, 
        regn, 
        fileLink, 
        code, 
        othbcDt, 
        newsWriterNm, 
        infoCl, 
        natn
    ) VALUES(
        :#{#news.articleId}, 
        :#{#news.newsTitl}, 
        :#{#news.kotraNewsUrl}, 
        :#{#news.regn}, 
        :#{#news.fileLink}, 
        :#{#news.code}, 
        :#{#news.othbcDt}, 
        :#{#news.newsWriterNm}, 
        :#{#news.infoCl}, 
        :#{#news.natn}
    ) ON DUPLICATE KEY UPDATE 
        newsTitl = :#{#news.newsTitl},
        kotraNewsUrl = :#{#news.kotraNewsUrl},
        regn = :#{#news.regn},
        fileLink = :#{#news.fileLink},
        code = :#{#news.code},
        othbcDt = :#{#news.othbcDt},
        newsWriterNm = :#{#news.newsWriterNm},
        infoCl = :#{#news.infoCl},
        natn = :#{#news.natn}
    """, nativeQuery = true)
	void upsertNews(@Param("news") News news);
}
