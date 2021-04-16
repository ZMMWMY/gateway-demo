package com.zmmwmy.gateway.core.call.invoke;

import com.zmmwmy.gateway.core.call.CacheClientFactory;
import com.zmmwmy.gateway.core.call.invoke.dubbo.DubboClientFactory;
import com.zmmwmy.gateway.core.call.invoke.dubbo.DubboInvoke;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.function.Function;

/**
 * @author zhouqiming
 * @date 2021-04-13 8:38 下午
 */
public class InvokeFactory {

    public static final String DUBBO_INVOKE_TYPE = "dubbo";

    private static final DubboInvoke DUBBO_INVOKE = new DubboInvoke(new CacheClientFactory<>(new DubboClientFactory()));

    private static Map<String, Function<ApplicationContext, AbstractInvoke>> invokeCache = ImmutableMap.<String, Function<ApplicationContext, AbstractInvoke>>builder()
            .put(DUBBO_INVOKE_TYPE, application -> DUBBO_INVOKE)
            .build();

    public static AbstractInvoke getInvoke(String type, ApplicationContext applicationContext) {
        if (!invokeCache.containsKey(type)) {
            throw new IllegalArgumentException("");
        }

        return invokeCache.get(type).apply(applicationContext);
    }
}
