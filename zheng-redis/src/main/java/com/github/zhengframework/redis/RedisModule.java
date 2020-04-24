package com.github.zhengframework.redis;

import static com.google.inject.name.Names.named;

import com.github.zhengframework.configuration.ConfigurationAwareModule;
import com.github.zhengframework.configuration.ConfigurationBeanMapper;
import com.google.inject.Key;
import io.lettuce.core.RedisClient;
import java.util.Map;
import java.util.Map.Entry;

public class RedisModule extends ConfigurationAwareModule {

  @Override
  protected void configure() {
    Map<String, RedisConfig> redisConfigMap = ConfigurationBeanMapper
        .resolve(getConfiguration(), RedisConfig.class);
    for (Entry<String, RedisConfig> entry : redisConfigMap.entrySet()) {
      if (entry.getKey().isEmpty()) {
        RedisConfig redisConfig = entry.getValue();
        bind(RedisConfig.class).toInstance(redisConfig);
        bind(RedisClient.class).toProvider(RedisClientProvider.class);
      } else {
        String name = entry.getKey();
        RedisConfig redisConfig = entry.getValue();
        bind(Key.get(RedisConfig.class, named(name))).toInstance(redisConfig);
        bind(Key.get(RedisClient.class, named(name)))
            .toProvider(new RedisClientProvider(redisConfig));
      }
    }
  }
}
