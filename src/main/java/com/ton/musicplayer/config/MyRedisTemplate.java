package com.ton.musicplayer.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ton.musicplayer.entity.MusicPublicFields;
import com.ton.musicplayer.entity.MusicSearchingRequest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;


@Component
public class MyRedisTemplate extends RedisTemplate<MusicSearchingRequest, MusicPublicFields[]>{

    public MyRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        // 构造函数注入 RedisConnectionFactory，设置到父类
        super.setConnectionFactory(redisConnectionFactory);

        // 使用 Jackson 提供的通用 Serializer
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        serializer.configure(mapper -> {
            // 如果涉及到对 java.time 类型的序列化，反序列化那么需要注册 JavaTimeModule
            mapper.registerModule(new JavaTimeModule());
        });

        // String 类型的 key/value 序列化
        super.setKeySerializer(serializer);
        super.setValueSerializer(serializer);

        // Hash 类型的 key/value 序列化
        super.setHashKeySerializer(serializer);
        super.setHashValueSerializer(serializer);

    }
}
