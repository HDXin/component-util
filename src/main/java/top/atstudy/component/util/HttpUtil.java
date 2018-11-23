//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package top.atstudy.component.util;

import com.alibaba.fastjson.JSONObject;
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
import java.util.Map.Entry;
import javax.net.ssl.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.atstudy.component.enums.EnumRequestMethod;

public class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public HttpUtil() {
    }

    private static JSONObject httpRequest(String requestUrl, EnumRequestMethod method, String outputStr) {
        JSONObject jsonObject = null;
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
            httpUrlConn.setRequestMethod(method.getCode());
            if (EnumRequestMethod.GET == method) {
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
            jsonObject = JSONObject.parseObject(buffer.toString());
            log.info(" ===>> https request success ... ");
            log.info(" ===>> {}", jsonObject.toJSONString());
        } catch (ConnectException var14) {
            log.error(" ===>> https request connection timed out. error: {}", var14);
        } catch (Exception var15) {
            log.error(" ===>> https request error:{}", var15);
        }

        return jsonObject;
    }

    public static JSONObject get(String url, Map<String, Object> params) {
        if (params != null) {
            StringBuilder stringBuilder = new StringBuilder("");
            Iterator var3 = params.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Object> entry = (Entry)var3.next();
                stringBuilder.append("&").append((String)entry.getKey()).append("=").append(entry.getValue());
            }

            String paramsStr = stringBuilder.toString();
            url = url + "?" + paramsStr.substring(1, paramsStr.length());
        }

        log.info(" ===>> request: {}", url);
        return httpRequest(url, EnumRequestMethod.GET, (String)null);
    }

    public static JSONObject post(String url, String params) {
        return httpRequest(url, EnumRequestMethod.POST, params);
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
