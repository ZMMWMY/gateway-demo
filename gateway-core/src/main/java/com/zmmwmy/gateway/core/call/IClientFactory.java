package com.zmmwmy.gateway.core.call;

import com.zmmwmy.gateway.core.domain.ApiDefineDO;
import org.springframework.context.ApplicationContext;

/**
 * @author zhouqiming
 * @date 2021-04-13 8:05 下午
 */
public interface IClientFactory<T> {

    /**
     * 构建
     *
     * @param apiDefineDO
     * @return
     */
    T buildFactoryInstance(ApiDefineDO apiDefineDO);

    /**
     *
     * @param apiDefineDO
     * @return
     */
    String uniqueKey(ApiDefineDO apiDefineDO);

}
