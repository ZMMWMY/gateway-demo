package com.zmmwmy.gateway.core.call.invoke.dubbo;

import com.zmmwmy.gateway.common.Pair;
import com.zmmwmy.gateway.core.call.IClientFactory;
import com.zmmwmy.gateway.core.call.IParameterConvert;
import com.zmmwmy.gateway.core.call.invoke.AbstractInvoke;
import com.zmmwmy.gateway.core.domain.ApiDefineDO;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.List;

/**
 * @author zhouqiming
 * @date 2021-04-13 8:48 下午
 */
public class DubboInvoke extends AbstractInvoke<GenericService> {

    public DubboInvoke(IClientFactory<GenericService> clientFactory) {
        super(clientFactory);
    }

    @Override
    public Pair<String[], Object[]> parseParamList(List<IParameterConvert.ReflectParameter> invokeParas) {
        String[] array1 = new String[invokeParas.size()];
        Object[] array2 = new Object[invokeParas.size()];
        for (int i = 0; i < invokeParas.size(); i++) {
            IParameterConvert.ReflectParameter rp = invokeParas.get(i);
            array1[i] = rp.getClassName();
            array2[i] = rp.getObject();
        }

        return new Pair<>(array1, array2);    }

    @Override
    public Object doInvoke(GenericService client, ApiDefineDO apiDefineDO, Pair<String[], Object[]> keyValueList) {
        return client.$invoke(apiDefineDO.getCallDefine().getMethodName(), keyValueList.getKey(), keyValueList.getValue());
    }
}
