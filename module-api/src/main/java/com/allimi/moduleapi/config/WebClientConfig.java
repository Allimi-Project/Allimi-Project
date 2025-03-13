package com.allimi.moduleapi.config;

import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfig {

	@Value("${spring.open-api.url}")
	private String baseUrl;

	@Bean
	public WebClient webClient() {

		HttpClient client = HttpClient.create()
				.responseTimeout(Duration.ofSeconds(1))
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.option(EpollChannelOption.TCP_KEEPIDLE, 300)
				.option(EpollChannelOption.TCP_KEEPINTVL, 60)
				.option(EpollChannelOption.TCP_KEEPCNT, 8)
			.doOnConnected(conn -> conn
					.addHandler(new ReadTimeoutHandler(10, TimeUnit.SECONDS))
					.addHandler(new WriteTimeoutHandler(10)))
			.secure(spec -> spec.sslContext(SslContextBuilder.forClient())
					.defaultConfiguration(SslProvider.DefaultConfigurationType.TCP)
					.handshakeTimeout(Duration.ofSeconds(30))
					.closeNotifyFlushTimeout(Duration.ofSeconds(10))
					.closeNotifyReadTimeout(Duration.ofSeconds(10)));

		return WebClient.builder()
				.baseUrl(baseUrl)
				.clientConnector(new ReactorClientHttpConnector(client))
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.ALL_VALUE)
				.build();
	}
}