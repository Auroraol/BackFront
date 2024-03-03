package com.github.xiaolyuh.cache;

import com.alibaba.fastjson.JSON;
import com.github.xiaolyuh.redis.serializer.FastJsonRedisSerializer;
import com.github.xiaolyuh.redis.serializer.JacksonRedisSerializer;
import com.github.xiaolyuh.redis.serializer.JdkRedisSerializer;
import com.github.xiaolyuh.redis.serializer.KryoRedisSerializer;
import com.github.xiaolyuh.redis.serializer.ProtostuffRedisSerializer;
import com.github.xiaolyuh.support.NullValue;

import java.util.Arrays;

/**
 * @author olafwang
 * @since 2020/9/25 4:10 下午
 */
public class MainTest {
    public static void main(String[] args) {
        double b = 0.0f / 0.0;
        System.out.println(Double.isNaN(b));
        System.out.println(Double.isInfinite(b));
        System.out.println(Double.isInfinite(1.0));
        System.out.println(JSON.toJSONString(null));
        System.out.println(JSON.toJSONString(new NullValue()));

        KryoRedisSerializer kryoRedisSerializer = new KryoRedisSerializer();
        System.out.println("KryoRedisSerializer:" + Arrays.toString(kryoRedisSerializer.getNullValueBytes()));

        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer();
        System.out.println("FastJsonRedisSerializer:" + Arrays.toString(fastJsonRedisSerializer.getNullValueBytes()));

        JacksonRedisSerializer jacksonRedisSerializer = new JacksonRedisSerializer();
        System.out.println("JacksonRedisSerializer:" + Arrays.toString(jacksonRedisSerializer.getNullValueBytes()));

        JdkRedisSerializer jdkRedisSerializer = new JdkRedisSerializer();
        System.out.println("JdkRedisSerializer:" + Arrays.toString(jdkRedisSerializer.getNullValueBytes()));

        ProtostuffRedisSerializer protostuffRedisSerializer = new ProtostuffRedisSerializer();
        System.out.println("ProtostuffRedisSerializer:" + Arrays.toString(protostuffRedisSerializer.getNullValueBytes()));
    }
}
