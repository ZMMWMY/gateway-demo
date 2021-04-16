package com.zmmwmy.gateway.demo.dubbo.provider.impl;

import com.zmmwmy.gateway.demo.dubbo.provider.api.UserProvider;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author zhouqiming
 * @date 2021-04-14 11:23 上午
 */
@DubboService(version = "1.0.0")
public class UserProviderImpl implements UserProvider {

    @Override
    public String getUserName(String userName) {
        return userName + ".provider";
    }
}
