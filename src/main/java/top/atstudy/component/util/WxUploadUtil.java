package top.atstudy.component.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Formatter;

public class WxUploadUtil {
    private static final Logger log = LoggerFactory.getLogger(WxUploadUtil.class);
    public WxUploadUtil() {
    }

    public static String httpRequest(String requestUrl, byte[] data, String fileName) {
        StringBuffer buffer = new StringBuffer();

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
            String BOUNDARY = "----------" + System.currentTimeMillis();
            httpUrlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + data.length + "\";filename=\"" + fileName + ".xlsx\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            OutputStream outputStream = new DataOutputStream(httpUrlConn.getOutputStream());
            outputStream.write(head);
            outputStream.write(data);
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");
            outputStream.write(foot);
            outputStream.flush();
            outputStream.close();
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
        } catch (IOException var15) {
            System.out.println("发送POST请求出现异常！" + var15);
            var15.printStackTrace();
        }

        return buffer.toString();
    }

    private static String byteToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        byte[] var2 = hash;
        int var3 = hash.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            formatter.format("%02x", b);
        }

        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static void main(String[] args) {
        try {
            String url = "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
            String filePath = "E://temp_poi/test.xls";
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[10240];
            boolean var7 = false;

            int len;
            while((len = fis.read(b)) != -1) {
                bos.write(b, 0, len);
            }

            byte[] data = bos.toByteArray();
            String accessToken = "cG-4CICX1pMQ0qLWCP74eUF4Vz-BK721QcUl9dkwBdDinoLzFQPaXAsL7veztjaN94TusIiqFHGEcOQyamjZKPZGtrWyVxAzdEcjMMzG0yotFvengJPPJ0f_HN56MVAD1Ei0IWeaZ6_BhVgbg7W5PWPqMYkyCeoSQKIvT6Jv3nP40QmQihTDZQKMfarD1fJ7DVP-eotwHeAGsVI4nJ53-Q";
            String type = "file";
            String reqUrl = url.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
            String response = httpRequest(reqUrl, data, "临时文件名.xlxs");
            log.info("==>> response jsonObject: {}", response);
            JSONObject jsonObject = JSONObject.parseObject(response);
            if (jsonObject != null && jsonObject.getInteger("errcode") != 0) {
                ;
            }
        } catch (Exception var14) {
            var14.printStackTrace();
        }

    }
}
