package com.zmmwmy.gateway.core.call.invoke;

import com.zmmwmy.gateway.common.Pair;
import com.zmmwmy.gateway.core.call.IClientFactory;
import com.zmmwmy.gateway.core.call.IParameterConvert;
import com.zmmwmy.gateway.core.domain.ApiDefineDO;

import java.util.List;

/**
 * @author zhouqiming
 * @date 2021-04-13 8:38 下午
 */
public abstract class AbstractInvoke<T> {

    final IClientFactory<T> clientFactory;


    protected AbstractInvoke(IClientFactory<T> clientFactory) {
        this.clientFactory = clientFactory;
    }

    public T getGenericService(ApiDefineDO apiDefineDO) {
        return clientFactory.buildFactoryInstance(apiDefineDO);
    }

    public Object invoke(ApiDefineDO apiDefineDO, List<IParameterConvert.ReflectParameter> paramList) {
        T client = getGenericService(apiDefineDO);
        Pair<String[], Object[]> keyValueList = parseParamList(paramList);
        return doInvoke(client, apiDefineDO, keyValueList);

    }

    /**
     * 参数
     *
     * @return
     */
    public abstract Pair<String[], Object[]> parseParamList(List<IParameterConvert.ReflectParameter> invokeParas);

    /**
     * 实际调用
     *
     * @param client
     * @param apiDefineDO
     * @param keyValueList
     * @return
     */
    public abstract Object doInvoke(T client, ApiDefineDO apiDefineDO, Pair<String[], Object[]> keyValueList);
}
