package top.atstudy.component.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.atstudy.component.config.Constants;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

public abstract class BaseHttpUtl {
    protected static final Logger log = LoggerFactory.getLogger(BaseHttpUtl.class);

    abstract JSONObject httpRequest(String requestUrl, String method, String outputStr);

    public JSONObject get(String url, Map<String, Object> params) {
        if (params != null) {
            StringBuilder stringBuilder = new StringBuilder("");
            Iterator var3 = params.entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry)var3.next();
                stringBuilder.append(Constants.UrlLink.DEFAULT_REQUEST_PARAMS_SEPARATOR)
                        .append(entry.getKey())
                        .append(Constants.UrlLink.DEFAULT_REQUEST_PARAMS_LINK)
                        .append(entry.getValue());
            }

            String paramsStr = stringBuilder.toString();
            url = url + Constants.UrlLink.DEFAULT_PARAMS_SEPARATOR + paramsStr.substring(1, paramsStr.length());
        }

        log.info(" ===>> request: {}", url);
        return httpRequest(url, Constants.HttpRequestMethod.GET, (String)null);
    }

    public JSONObject post(String url, String params) {
        return httpRequest(url, Constants.HttpRequestMethod.POST, params);
    }

    static class MyX509TrustManager implements X509TrustManager {
        public MyX509TrustManager() {
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
