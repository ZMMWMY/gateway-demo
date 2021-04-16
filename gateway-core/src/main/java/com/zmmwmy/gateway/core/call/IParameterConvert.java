package com.zmmwmy.gateway.core.call;

import com.zmmwmy.gateway.core.domain.ApiDefineDO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IParameterConvert {

    public List<ReflectParameter> convert(ApiDefineDO apiDefine, HttpServletRequest input);


    public static class ReflectParameter {
        private String className;
        private Object object;

        private String paramName;

        public ReflectParameter() {

        }

        public ReflectParameter(String className, Object object) {
            this.className = className;
            this.object = object;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public String getParamName() {
            return paramName;
        }

        public void setParamName(String paramName) {
            this.paramName = paramName;
        }
    }
}
