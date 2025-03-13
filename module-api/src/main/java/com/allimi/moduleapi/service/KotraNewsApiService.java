package com.allimi.moduleapi.service;

import com.allimi.moduleapi.config.ConfigurationPropertySource;
import com.allimi.modulecore.dto.KotraNewsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class KotraNewsApiService {

	private final WebClient webClient;
	private final ConfigurationPropertySource configurationPropertySource;

	public KotraNewsApiService(WebClient.Builder webClientBuilder,
							   ConfigurationPropertySource configurationPropertySource) {
		this.webClient = webClientBuilder.build();
		this.configurationPropertySource = configurationPropertySource;
	}

	public Mono<KotraNewsResponse> getKotraNewsByCode(int pageNo, int numOfRows, String code) throws URISyntaxException {

		String encoded = URLEncoder.encode(configurationPropertySource.getServiceKey(), StandardCharsets.UTF_8);
		String url = configurationPropertySource.getUrl() +
				"serviceKey=" + encoded +
				"&pageNo=" + pageNo +
				"&numOfRows=" + numOfRows +
				"&search5=" + code;
		URI uri = new URI(url);

		return webClient.get()
				.uri(uri)
				.accept(MediaType.ALL)
				.retrieve()
				.bodyToMono(KotraNewsResponse.class);
	}
}