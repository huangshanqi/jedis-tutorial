package cn.evilcoder.service;

import cn.evilcoder.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

@Service
public class JedisService {

    @Autowired
    private JedisPool jedisPool;

    public String hget(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hget(key, field);
        } finally {
            jedis.close();
        }
    }

    public void hset(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hset(key, field, value);
        } finally {
            jedis.close();
        }
    }

    public void hdel(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hdel(key, field);
        } finally {
            jedis.close();
        }
    }

    public void hmset(String key, Map<String, String> hash) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hmset(key, hash);
        } finally {
            jedis.close();
        }
    }

    public List<String> hmget(String key, String... fields) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hmget(key, fields);
        } finally {
            jedis.close();
        }
    }

    public void setexString(String key, String value, int timeoutInSecond) {
        if (value == null) {
            return;
        }
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.setex(key, timeoutInSecond, value);
        } finally {
            jedis.close();
        }
    }

    public long getTTL(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.ttl(key);
        } finally {
            jedis.close();
        }
    }

    public String getString(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            if (!jedis.exists(key)) {
                return null;
            }
            return jedis.get(key);
        } finally {
            jedisPool.returnBrokenResource(jedis);
        }
    }

    public void remove(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.del(key);
        } finally {
            jedis.close();
        }

    }

    public <T> void put(String key, T value, int timoutInSecond) {
        if (value == null) {
            return;
        }
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.setex(key.getBytes(), timoutInSecond, SerializeUtil.serialize(value));
        } finally {
            jedis.close();
        }
    }

    public <T> T get(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            if (!jedis.exists(key)) {
                return null;
            }
            byte[] value = jedis.get(key.getBytes());
            return (T) SerializeUtil.deserialize(value);
        } finally {
            jedis.close();
        }
    }

    public boolean isKeyExist(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.exists(key);
        } finally {
            jedis.close();
        }
    }

}
