package yearnlune.lab.namingcenter.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import yearnlune.lab.namingcenter.service.LogService;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.16
 * DESCRIPTION :
 */

@Slf4j
public class WebInterceptor extends HandlerInterceptorAdapter {

	ObjectMapper objectMapper = new ObjectMapper();

	final LogService logService;

	public WebInterceptor(LogService logService) {
		this.logService = logService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws IOException {

		final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
		final ContentCachingResponseWrapper cachingResponse =(ContentCachingResponseWrapper) response;

		StringBuilder sb = new StringBuilder();
		String accountId = AuthenticationFilter.getAccountIdByToken(request);

		sb.append(cachingRequest.getMethod());
		sb.append(" | ");
		sb.append(cachingRequest.getRequestURI());
		sb.append(" | ");
		sb.append(accountId);

		logService.saveLog(LogService.LogContent.builder()
			.method(cachingRequest.getMethod())
			.requestBody(objectMapper.readTree(cachingRequest.getContentAsByteArray()))
			.remoteAddr(cachingRequest.getRemoteAddr())
			.requestUri(cachingRequest.getRequestURI())
			.responseBody(objectMapper.readTree(cachingResponse.getContentAsByteArray()))
			.build(), accountId);

		log.info(sb.toString());
	}
}
