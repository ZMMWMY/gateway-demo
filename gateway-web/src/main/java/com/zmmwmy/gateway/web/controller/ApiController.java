package com.zmmwmy.gateway.web.controller;

import com.zmmwmy.gateway.core.call.invoke.dubbo.DubboService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhouqiming
 * @date 2021-04-12 8:39 下午
 */
@Controller
@RequestMapping("/gw")
public class ApiController {

    @Resource
    private DubboService dubboService;

    @RequestMapping("/api/{method:.*}")
    public void invoke(@PathVariable String apiName, HttpServletRequest request, HttpServletResponse response) {
        try {
            Object o = dubboService.serverCall(apiName,request,response);

            String res = serializeWriter(o);
            byte[] result = res.getBytes();
            response.getOutputStream().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String serializeWriter(Object o) {
        return o.toString();
    }

}
