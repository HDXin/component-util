//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package top.atstudy.component.util;

import com.alibaba.fastjson.JSONObject;
import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;

public class HttpRequestUtil extends BaseHttpUtl {

    private static final HttpRequestUtil instance = new HttpRequestUtil();
    private HttpRequestUtil() {}
    public static HttpRequestUtil getInstance(){
        return instance;
    }

    JSONObject httpRequest(String requestUrl, String method, String outputStr) {
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


}
