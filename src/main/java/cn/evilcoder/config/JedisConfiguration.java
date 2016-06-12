package cn.evilcoder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisConfiguration {

    @Value("${jedis.maxIdle:-1}")
    private int maxIdle;

    @Value("${jedis.maxActive:-1}")
    private int maxActive;

    @Value("${jedis.maxWait:10}")
    private long maxWait;

    @Value("${jedis.testOnBorrow:true}")
    private boolean testOnBorrow;

    @Value("${jedis.host:127.0.0.1}")
    private String host;

    @Value("${jedis.port:6379}")
    private int port;

    @Autowired
    private JedisPoolConfig poolConfig;

    @Bean
    public JedisPoolConfig poolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxActive);
        config.setTestOnBorrow(testOnBorrow);

        return config;
    }

    /**
     * Initialize one JedisPool
     */
    @Bean
    public JedisPool jedisPool() {
        JedisPool pool = new JedisPool(poolConfig, host, port);
        return pool;
    }
}