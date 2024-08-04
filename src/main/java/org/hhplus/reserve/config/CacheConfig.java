package org.hhplus.reserve.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CacheConfig {
    @Autowired
    private ApplicationContext applicationContext;

    public void checkCacheManager() {
        CacheManager cacheManager = applicationContext.getBean(CacheManager.class);
        System.out.println("Cache Manager: " + cacheManager);
    }
}
