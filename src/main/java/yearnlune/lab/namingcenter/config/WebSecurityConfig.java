package yearnlune.lab.namingcenter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import yearnlune.lab.namingcenter.constant.AccountRoleEnum;
import yearnlune.lab.namingcenter.service.LogService;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.02.24
 * DESCRIPTION :
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Value("${spring.profiles:}")
	String profile;

	@Autowired
	LogService logService;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		if (profile.contains("develop")) {
			http
				.csrf().disable()
				.cors();
		} else {
			http.csrf();
		}

		http
			.formLogin().disable()
			.addFilterAfter(new AuthenticationFilter(logService), UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests()
			.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
			.antMatchers("/error").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/validate").permitAll()
			.antMatchers("/account").permitAll()
			.antMatchers("/admin").access(AccountRoleEnum.valueOf("ADMIN").getValue())
			.anyRequest().authenticated();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new Pbkdf2PasswordEncoder("secretNaming", 10000, 128);
	}

}
