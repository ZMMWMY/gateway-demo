package com.zmmwmy.gateway.core.call;

import com.alibaba.fastjson.JSON;
import com.zmmwmy.gateway.core.domain.ApiDefineDO;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.dubbo.common.utils.ArrayUtils;
import org.apache.dubbo.common.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author zhouqiming
 * @date 2021-04-14 10:43 上午
 */
public class DefaultParameterConvert implements IParameterConvert {

    public static final String STRING = "string";
    public static final String INT = "int";
    public static final String LONG = "long";
    public static final String DOUBLE = "double";
    public static final String FLOAT = "float";
    public static final String BOOLEAN = "boolean";
    public static final String C_INT = "Integer";
    public static final String C_Long = "Long";
    public static final String C_DOUBLE = "Double";
    public static final String C_FLOAT = "Float";
    public static final String C_BOOLEAN = "Boolean";
    public static final String OBJECT = "object";


    @Override
    public List<ReflectParameter> convert(ApiDefineDO apiDefine, HttpServletRequest input) {
        if (ArrayUtils.isEmpty(apiDefine.getParameter())) {
            return Collections.emptyList();
        }
        List<ReflectParameter> parameter = new ArrayList<>();
        for (ApiDefineDO.ParameterDefineDO define : apiDefine.getParameter()) {
            parameter.add(toReflectParameter(define, input));
        }

        return parameter;
    }

    public ReflectParameter toReflectParameter(ApiDefineDO.ParameterDefineDO define, HttpServletRequest input) {
        if (STRING.equalsIgnoreCase(define.getType())) {
            return new ReflectParameter("java.lang.String", getParameter(input, define.getApiParameterName()));
        }
        if (BOOLEAN.equalsIgnoreCase(define.getType()) || C_BOOLEAN.equalsIgnoreCase(define.getType())) {
            return new ReflectParameter("java.lang.Boolean", BooleanUtils.toBooleanObject(getParameter(input, define.getApiParameterName())));
        } else if (INT.equals(define.getType())) {
            return new ReflectParameter("int", NumberUtils.toInt(getParameter(input, define.getApiParameterName())));
        } else if (C_INT.equals(define.getType())) {
            String paraStr = getParameter(input, define.getApiParameterName());
            Integer integer = StringUtils.isBlank(paraStr) ? null : NumberUtils.toInt(paraStr);
            return new ReflectParameter("java.lang.Integer", integer);
        } else if (LONG.equals(define.getType())) {
            return new ReflectParameter("long", NumberUtils.toLong(getParameter(input, define.getApiParameterName())));
        } else if (C_Long.equals(define.getType())) {
            String paraStr = getParameter(input, define.getApiParameterName());
            Long l = StringUtils.isBlank(paraStr) ? null : NumberUtils.toLong(paraStr);
            return new ReflectParameter("java.lang.Long", l);
        } else if (DOUBLE.equals(define.getType())) {
            return new ReflectParameter("double", NumberUtils.toDouble(getParameter(input, define.getApiParameterName())));
        } else if (C_DOUBLE.equals(define.getType())) {
            String paraStr = getParameter(input, define.getApiParameterName());
            Double d = StringUtils.isBlank(paraStr) ? null : NumberUtils.toDouble(paraStr);
            return new ReflectParameter("java.lang.Double", d);
        } else if (FLOAT.equals(define.getType())) {
            return new ReflectParameter("float", NumberUtils.toFloat(getParameter(input, define.getApiParameterName())));
        } else if (C_FLOAT.equals(define.getType())) {
            String paraStr = getParameter(input, define.getApiParameterName());
            Float f = StringUtils.isBlank(paraStr) ? null : NumberUtils.toFloat(paraStr);
            return new ReflectParameter("java.lang.Double", f);
        } else if (OBJECT.equalsIgnoreCase(define.getType())) {
            if (StringUtils.isNotEmpty(define.getApiParameterName()) && ArrayUtils.isEmpty(define.getDefine())) {
                return new ReflectParameter(define.getClassName(), JSON.parse(getParameter(input, define.getApiParameterName())));
            } else if (!ArrayUtils.isEmpty(define.getDefine())) { //Object子属性一一递归模式
                Map<String, Object> object = new HashMap<>();
                object.put("class", define.getClassName());//添加上class属性（如果后续调用需要）
                for (ApiDefineDO.ParameterDefineDO subDefine : define.getDefine()) {
                    ReflectParameter subReflectParameter = toReflectParameter(subDefine, input);
                    String serviceParameterName = StringUtils.isBlank(subDefine.getApiParameterName()) ? subDefine.getApiParameterName() : subDefine.getApiParameterName();
                    object.put(serviceParameterName, subReflectParameter.getObject());
                }
                return new ReflectParameter(define.getClassName(), object);
            } else {
                throw new RuntimeException("OBJECT Type with no apiParameterName and no sub define");
            }
        }
        throw new RuntimeException("unknow define type" + define.getType());
    }

    public String getParameter(HttpServletRequest input, String name) {
        return StringUtils.trim(input.getParameter(name));
    }
}
