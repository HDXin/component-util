//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package top.atstudy.component.util;

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
import top.atstudy.component.config.Constants;

public class WxPaymentUtil extends BaseHttpUtl {

    private static final WxPaymentUtil instance = new WxPaymentUtil();
    private WxPaymentUtil() {}
    public static WxPaymentUtil getInstance(){
        return instance;
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
            if ("GET".equals(method)) {
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
        } catch (ConnectException var13) {
            log.error(" ===>> https request connection timed out. error: {}", var13);
        } catch (Exception var14) {
            log.error(" ===>> https request error:{}", var14);
        }

        return buffer.toString();
    }

    public static String get(String url, Map<String, Object> params) {
        if (params != null) {
            StringBuilder stringBuilder = new StringBuilder("");
            Iterator var3 = params.entrySet().iterator();

            while(var3.hasNext()) {
                Entry<String, Object> entry = (Entry)var3.next();
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

    public static String post(String url, String params) {
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
