package yearnlune.lab.namingcenter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Project : naming-center
 * Created by IntelliJ IDEA
 * Author : DONGHWAN, KIM
 * DATE : 2020.02.13
 * DESCRIPTION :
 */


@Configuration
public class RedisConfig {
    @Value("${spring.redis.host:redis}")
    private String redisHost;
    @Value("${spring.redis.port:6379}")
    private Integer redisPort;
    @Value("${spring.redis.password:}")
    private String redisPassword;
    @Value("${spring.redis.timeout:30}")
    private int redisTimeOut;

    public static final String REDIS_ACCOUNT = "ACCOUNTS";

    public static final String REDIS_LOGIN_FAILED = "LOGIN_FAILED";

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfigurationBuilder;
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
        if(redisPassword != null && !redisPassword.isEmpty())
            redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));

        jedisClientConfigurationBuilder = JedisClientConfiguration.builder();
        jedisClientConfigurationBuilder.connectTimeout(Duration.ofSeconds(redisTimeOut));
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfigurationBuilder.build());
    }

    @Bean(name = "redisTemplate")
    RedisTemplate<String, Integer> redisTemplate() {
        final RedisTemplate<String, Integer> template =  new RedisTemplate<>();
        template.setEnableDefaultSerializer(false);
        template.setConnectionFactory( jedisConnectionFactory() );
        template.setKeySerializer( new StringRedisSerializer() );
        template.setValueSerializer( new GenericJackson2JsonRedisSerializer() );
        template.setHashValueSerializer( new GenericJackson2JsonRedisSerializer() );
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }


}
