package com.zmmwmy.gateway.core.call.invoke.dubbo;

import com.zmmwmy.gateway.core.call.IClientFactory;
import com.zmmwmy.gateway.core.domain.ApiDefineDO;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ConsumerConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.rpc.service.GenericService;
import com.zmmwmy.gateway.core.util.SpringContext;

/**
 * @author zhouqiming
 * @date 2021-04-13 8:15 下午
 */
public class DubboClientFactory implements IClientFactory<GenericService> {


    @Override
    public GenericService buildFactoryInstance(ApiDefineDO apiDefineDO) {
        ApiDefineDO.CallDefineDO callDefine = apiDefineDO.getCallDefine();

        ReferenceBean<GenericService> referenceBean = new ReferenceBean<>();
        referenceBean.setApplication(SpringContext.getBean(ApplicationConfig.class));
        referenceBean.setRegistry(SpringContext.getBean(RegistryConfig.class));
        referenceBean.setInterface(callDefine.getInterfaceName());
        referenceBean.setVersion(callDefine.getVersion());
        referenceBean.setGeneric(Boolean.TRUE);
        referenceBean.setCheck(false);
        referenceBean.setConsumer(SpringContext.getBean(ConsumerConfig.class));
        referenceBean.setGroup(referenceBean.getConsumer().getGroup());
        return referenceBean.get();
    }

    @Override
    public String uniqueKey(ApiDefineDO apiDefineDO) {
        return apiDefineDO.getApiName();
    }


}
