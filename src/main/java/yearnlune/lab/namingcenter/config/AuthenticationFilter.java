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

	final LogService logService;

	public AuthenticationFilter(LogService logService) {
		this.logService = logService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		String accountId = null;
		try {
			sb.append(request.getMethod());
			sb.append(" | ");
			sb.append(request.getRequestURI());

			if (checkJwtToken(request, response)) {
				Claims claims = validateToken(request);
				if (claims.get("authorities") != null) {
					setUsernamePasswordAuthentication(claims);
					accountId = (String)claims.get("id");
				} else {
					SecurityContextHolder.clearContext();
				}
			}
			filterChain.doFilter(request, response);
		} catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			sb.insert(0, "ERROR ");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		} finally {
			Object sc = SecurityContextHolder.getContext().getAuthentication();
			if (sc != null) {
				sb.append(" | " + sc.toString());
			}

			log.info(sb.toString() + " | " + accountId);

			logService.saveLog(LogService.LogContent.builder()
				.method(request.getMethod())
				.remoteAddr(request.getRemoteAddr())
				.requestUri(request.getRequestURI())
				.build(), accountId);
		}
	}

	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	private void setUsernamePasswordAuthentication(Claims claims) {
		List<String> authorities = (List<String>)claims.get("authorities");
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
			claims.getSubject(),
			null,
			authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private boolean checkJwtToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		return authenticationHeader != null;
	}
}
