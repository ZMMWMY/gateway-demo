package com.zmmwmy.gateway.core.call;

import com.zmmwmy.gateway.core.domain.ApiDefineDO;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhouqiming
 * @date 2021-04-13 8:07 下午
 */
public class CacheClientFactory<T> implements IClientFactory<T> {

    private final IClientFactory<T> origin;

    private ConcurrentHashMap<String, T> instanceCache = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, ReentrantReadWriteLock> initLock = new ConcurrentHashMap<>();

    public CacheClientFactory(IClientFactory<T> origin) {
        this.origin = origin;
    }


    @Override
    public T buildFactoryInstance(ApiDefineDO apiDefineDO) {
        String key = uniqueKey(apiDefineDO);
        T cache = instanceCache.get(key);
        if (cache != null) {
            return cache;
        }

        return initInstance(key, apiDefineDO);
    }

    private T initInstance(String key, ApiDefineDO apiDefineDO) {
        ReentrantReadWriteLock lock = initLock.get(key);
        try {
            if (lock == null) {
                lock = new ReentrantReadWriteLock();
                ReentrantReadWriteLock oldLock = initLock.putIfAbsent(key, new ReentrantReadWriteLock());
                lock = oldLock == null ? lock : oldLock;
            }
            if (lock.writeLock().tryLock()) {
                try {
                    T client = origin.buildFactoryInstance(apiDefineDO);
                    if (null != client) {
                        instanceCache.put(key, client);
                    } else {
                        //
                    }
                    return client;
                } catch (Exception e) {
                    throw e;
                } finally {
                    lock.writeLock().unlock();
                }
            } else {
                try {
                    lock.readLock().lock();
                    return instanceCache.get(key);
                } finally {
                    lock.readLock().unlock();
                }
            }
        } finally {
            initLock.remove(key);
        }
    }

    @Override
    public String uniqueKey(ApiDefineDO apiDefineDO) {
        return origin.uniqueKey(apiDefineDO);
    }

}
