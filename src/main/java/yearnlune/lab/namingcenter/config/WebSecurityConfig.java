package yearnlune.lab.namingcenter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.02.24
 * DESCRIPTION :
 */

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${spring.profiles:}")
    String profile;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        if (profile.contains("develop")) {
            http.csrf().disable();
        } else {
            http.csrf();
        }

        http
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests().anyRequest().permitAll();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder("secretNaming", 10000, 128);
    }

}
