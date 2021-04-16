package com.zmmwmy.gateway.core.domain;

/**
 * @author zhouqiming
 * @date 2021-04-13 7:53 下午
 */
public class ApiDefineDO {

    private String apiName;

    private ParameterDefineDO[] parameter;

    private CallDefineDO callDefine;

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public ParameterDefineDO[] getParameter() {
        return parameter;
    }

    public void setParameter(ParameterDefineDO[] parameter) {
        this.parameter = parameter;
    }

    public CallDefineDO getCallDefine() {
        return callDefine;
    }

    public void setCallDefine(CallDefineDO callDefine) {
        this.callDefine = callDefine;
    }

    public static class CallDefineDO {

        private String interfaceName;

        private String methodName;

        private String version;

        public String getInterfaceName() {
            return interfaceName;
        }

        public void setInterfaceName(String interfaceName) {
            this.interfaceName = interfaceName;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }


    public static class ParameterDefineDO {
        /**
         * 参数名
         */
        private String apiParameterName;

        /**
         * 类型 string
         */
        private String type;

        /**
         * 具体类型 java的class名
         */
        private String className;

        private boolean require = false;

        /**
         * 子类型
         */
        private ParameterDefineDO[] define;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public boolean isRequire() {
            return require;
        }

        public void setRequire(boolean require) {
            this.require = require;
        }

        public String getApiParameterName() {
            return apiParameterName;
        }

        public void setApiParameterName(String apiParameterName) {
            this.apiParameterName = apiParameterName;
        }

        public ParameterDefineDO[] getDefine() {
            return define;
        }

        public void setDefine(ParameterDefineDO[] define) {
            this.define = define;
        }
    }
}
