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
}
