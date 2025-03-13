package com.allimi.modulebatch.job;

import com.allimi.modulebatch.util.CreateDateJobParameter;
import com.allimi.modulebatch.writer.KotraNewsWriter;
import com.allimi.modulecore.dto.KotraNewsResponse;
import com.allimi.moduleapi.service.KotraNewsApiService;
import com.allimi.modulebatch.processor.KotraNewsProcessor;
import com.allimi.modulebatch.reader.KotraNewsReader;
import com.allimi.modulecore.domain.news.News;
import com.allimi.modulecore.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class KotraNewsBatch {

	private static final String JOB_NAME = "대한무역투자진흥공사_배치서비스";
	private static final String STEP_NAME = "KOTRA 해외 뉴스 수집";
	private static final String BEAN_PREFIX = JOB_NAME + "_";

	private final PlatformTransactionManager platformTransactionManager;
	private final NewsRepository newsRepository;
	private final KotraNewsApiService kotraNewsApiService;
	private final JobRepository jobRepository;
	private final CreateDateJobParameter createDateJobParameter;

	private int chunkSize;

	@Bean(BEAN_PREFIX + "createDateJobParameter")
	@JobScope
	public CreateDateJobParameter createDateJobParameter() {
		return new CreateDateJobParameter();
	}

	@Value("${chunk-size:1000}")
	public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

	@Bean
	public Job newsBatchJob() {
		return new JobBuilder(JOB_NAME, jobRepository)
				.start(newsBatchStep())
				.build();
	}

	@JobScope
	@Bean(BEAN_PREFIX + "_step")
	public Step newsBatchStep() {
		String step = STEP_NAME + "_" + createDateJobParameter.getDate();

		return new StepBuilder(step, jobRepository)
				.<KotraNewsResponse.Item, News> chunk(chunkSize, platformTransactionManager)
				.reader(kotraNewsReader())
				.processor(kotraNewsProcessor())
				.writer(kotraNewsWriter())
				.build();
	}

	@StepScope
	@Bean
	public KotraNewsReader kotraNewsReader() {
        return new KotraNewsReader(kotraNewsApiService);
    }

	@StepScope
	@Bean
	public KotraNewsProcessor kotraNewsProcessor() {
		return new KotraNewsProcessor();
	}

	@StepScope
	@Bean
	public KotraNewsWriter kotraNewsWriter() {
        return new KotraNewsWriter(newsRepository);
    }
}
