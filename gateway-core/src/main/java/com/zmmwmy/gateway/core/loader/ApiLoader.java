package com.zmmwmy.gateway.core.loader;

import com.google.common.collect.Lists;
import com.zmmwmy.gateway.core.domain.ApiDefineDO;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouqiming
 * @date 2021-04-13 9:33 下午
 */
@Component
public class ApiLoader {

    private volatile Map<String, ApiDefineDO> apiCache = new HashMap<>();

    public ApiDefineDO getApi(String apiName) {
        return apiCache.get(apiName);
    }

    private List<ApiDefineDO> loaderAllApi() {
        ApiDefineDO testApi = new ApiDefineDO();
        testApi.setApiName("com.zmmwmy.user.name.query");
        ApiDefineDO.CallDefineDO callDefineDO = new ApiDefineDO.CallDefineDO();
        callDefineDO.setInterfaceName("com.zmmwmy.gateway.demo.dubbo.provider.api.UserProvider");
        callDefineDO.setMethodName("getUserName");
        callDefineDO.setVersion("1.0.0");
        testApi.setCallDefine(callDefineDO);

        ApiDefineDO.ParameterDefineDO parameterDefineDO = new ApiDefineDO.ParameterDefineDO();
        parameterDefineDO.setType("string");
        parameterDefineDO.setApiParameterName("userName");

        testApi.setParameter(new ApiDefineDO.ParameterDefineDO[]{parameterDefineDO});

        return Lists.newArrayList(testApi);
    }

    private void load() {
        List<ApiDefineDO> allApi = loaderAllApi();
        Map<String, ApiDefineDO> apiCache = new HashMap<>();
        for (ApiDefineDO define : allApi) {
            apiCache.put(define.getApiName(), define);
        }
        this.apiCache = apiCache;
    }

    @PostConstruct
    public void init() {
        //定时load api
        load();
    }

}
