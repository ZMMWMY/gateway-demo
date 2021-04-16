package com.zmmwmy.gateway.core.call.invoke.dubbo;

import com.zmmwmy.gateway.core.call.DefaultParameterConvert;
import com.zmmwmy.gateway.core.call.IParameterConvert;
import com.zmmwmy.gateway.core.call.invoke.AbstractInvoke;
import com.zmmwmy.gateway.core.call.invoke.InvokeFactory;
import com.zmmwmy.gateway.core.domain.ApiDefineDO;
import com.zmmwmy.gateway.core.loader.ApiLoader;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouqiming
 * @date 2021-04-13 9:09 下午
 */
@Component
public class DubboService {

    private AbstractInvoke dubboInvoke = InvokeFactory.getInvoke(InvokeFactory.DUBBO_INVOKE_TYPE, null);

    private IParameterConvert parameterConvert = new DefaultParameterConvert();

    @Resource
    private ApiLoader apiLoader;

    public Object serverCall(String apiName, HttpServletRequest request, HttpServletResponse response) {
        ApiDefineDO apiDefineDO = apiLoader.getApi(apiName);

        List<IParameterConvert.ReflectParameter> parameterList = parameterConvert.convert(apiDefineDO, request);
        Object result = dubboInvoke.invoke(apiDefineDO, parameterList);
        return result;
    }
}
