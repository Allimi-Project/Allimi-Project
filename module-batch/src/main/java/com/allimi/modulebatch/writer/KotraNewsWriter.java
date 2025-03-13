package com.allimi.modulebatch.writer;

import com.allimi.modulecore.domain.news.News;
import com.allimi.modulecore.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

@RequiredArgsConstructor
public class KotraNewsWriter implements ItemWriter<News> {

	private final NewsRepository newsRepository;

	@Override
	public void write(Chunk<? extends News> chunk) throws Exception {
		newsRepository.saveAll(chunk);
	}
}
