//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package top.atstudy.component.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

public class HttpRequestUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpRequestUtil.class);
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String DEFAULT_PARAMS_SEPARATOR = "?";
    private static final String DEFAULT_REQUEST_PARAMS_SEPARATOR = "&";
    private static final String DEFAULT_REQUEST_PARAMS_LINK = "=";

    public static String post(String url, String params) {
        return httpRequest(url, POST, params);
    }

    public static JSONObject postJson(String url, String params){
        return JSONObject.parseObject(post(url, params));
    }

    public static JSONObject getJson(String url, Map<String, Object> params) {
        return JSONObject.parseObject(get(url, params));
    }

    public static String get(String url, Map<String, Object> params) {
        if (params != null) {
            StringBuilder stringBuilder = new StringBuilder("");
            Iterator var3 = params.entrySet().iterator();

            while(var3.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry)var3.next();
                stringBuilder.append(DEFAULT_REQUEST_PARAMS_SEPARATOR)
                        .append(entry.getKey())
                        .append(DEFAULT_REQUEST_PARAMS_LINK)
                        .append(entry.getValue());
            }

            String paramsStr = stringBuilder.toString();
            url = url + DEFAULT_PARAMS_SEPARATOR + paramsStr.substring(1, paramsStr.length());
        }

        log.info(" ===>> request: {}", url);
        return httpRequest(url, GET, (String)null);
    }

    private static String httpRequest(String requestUrl, String method, String outputStr) {
        StringBuffer buffer = new StringBuffer();
        try {
            TrustManager[] tm = new TrustManager[]{new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init((KeyManager[])null, tm, new SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection)url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(method);
            if (GET.equals(method)) {
                httpUrlConn.connect();
            }

            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;

            while((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            httpUrlConn.disconnect();
            log.info(" ===>> https request success ... ");
            log.info(" ===>> {}", buffer.toString());
        } catch (ConnectException var14) {
            log.error(" ===>> https request connection timed out. error: {}", var14);
        } catch (Exception var15) {
            log.error(" ===>> https request error:{}", var15);
        }

        return buffer.toString();
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
