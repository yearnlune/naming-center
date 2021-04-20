package yearnlune.lab.namingcenter.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import yearnlune.lab.namingcenter.service.LogService;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.03.20
 * DESCRIPTION :
 */

@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	public static final String HEADER = "Authorization";
	public static final String PREFIX = "Bearer ";
	public static final String SECRET_KEY = "namingCenter";
	public static final int DURATION = 600000;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		ContentCachingRequestWrapper wrapperRequest = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);

		try {
			if (checkJwtToken(request)) {
				Claims claims = validateToken(request);
				if (claims.get("authorities") != null) {
					setUsernamePasswordAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			}
			filterChain.doFilter(wrapperRequest, wrapperResponse);
		} catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			wrapperResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			wrapperResponse.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		} finally {
			wrapperResponse.copyBodyToResponse();
		}
	}

	private static Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	private static void setUsernamePasswordAuthentication(Claims claims) {
		List<String> authorities = (List<String>)claims.get("authorities");
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
			claims.getSubject(),
			null,
			authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private static boolean checkJwtToken(HttpServletRequest request) {
		String authenticationHeader = request.getHeader(HEADER);
		return authenticationHeader != null;
	}

	public static String getAccountIdByToken(HttpServletRequest request) {
		String accountId = null;
		if (checkJwtToken(request)) {
			Claims claims = validateToken(request);
			if (claims.get("authorities") != null) {
				setUsernamePasswordAuthentication(claims);
				accountId = (String)claims.get("id");
			}
		}

		return accountId;
	}
}
