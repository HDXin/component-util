package top.atstudy.component.config;

public class Constants {

    /**
     * 系统空值
     */
    public static final String DEFAULT_STRING_NULL = "";
    public static final String DEFAULT_STRING = "-";
    public static final Long DEFAULT_LONG_NULL = -1L;

    /**
     * Http 请求方法
     */
    public static class HttpRequestMethod{
        public static final String POST = "POST";
        public static final String GET = "GET";
    }

    /**
     * url 连接
     */
    public static class UrlLink{
        public static final String DEFAULT_PARAMS_SEPARATOR = "?";
        public static final String DEFAULT_REQUEST_PARAMS_SEPARATOR = "&";
        public static final String DEFAULT_REQUEST_PARAMS_LINK = "=";
    }

    /**
     * 微信支付常量
     */
    public static class WxPay{
        public static final String DEFAULT_ACCESS_TOKEN_KEY = "access_token";
        public static final String DEFAULT_SEPARATOR_CORPID = "CORPID";
        public static final String DEFAULT_SEPARATOR_SECRECT = "SECRECT";
        public static final String DEFAULT_SEPARATOR_ACCESS_TOKEN = "ACCESS_TOKEN";
        public static final String DEFAULT_SEPARATOR_USERID = "USERID";
        public static final String DEFAULT_SEPARATOR_DEPARTMENT_ID = "DEPARTMENT_ID";
        public static final String DEFAULT_SEPARATOR_FETCH_CHILD = "FETCH_CHILD";
        public static final String DEFAULT_SEPARATOR_TAGID = "TAGID";
    }

    public Constants() {
    }

}
