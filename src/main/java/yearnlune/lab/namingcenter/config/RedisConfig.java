package yearnlune.lab.namingcenter.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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

    public static final String REDIS_NAME = "NAMES";

    @Bean(destroyMethod = "shutdown")
    @Primary
    ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
        if (redisPassword != null && !redisPassword.isEmpty()) {
            redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));
        }
        return redisStandaloneConfiguration;
    }

    @Bean
    public ClientOptions clientOptions() {
        return ClientOptions.builder()
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .autoReconnect(true)
                .build();
    }

    @Bean
    public LettucePoolingClientConfiguration lettucePoolConfig(ClientOptions options, ClientResources dcr) {
        return LettucePoolingClientConfiguration.builder()
                .poolConfig(new GenericObjectPoolConfig())
                .clientOptions(options)
                .clientResources(dcr)
                .build();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration,
                                                         LettucePoolingClientConfiguration lettucePoolConfig) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolConfig);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    @Primary
    public RedisTemplate<String, Integer> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        final RedisTemplate<String, Integer> template = new RedisTemplate<>();
        template.setEnableDefaultSerializer(false);
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer( new StringRedisSerializer() );
        template.setValueSerializer( new GenericJackson2JsonRedisSerializer() );
        template.setHashValueSerializer( new GenericJackson2JsonRedisSerializer() );
        template.setHashKeySerializer( new GenericJackson2JsonRedisSerializer() );
        template.setHashValueSerializer( new GenericJackson2JsonRedisSerializer());

        return template;
    }


}
