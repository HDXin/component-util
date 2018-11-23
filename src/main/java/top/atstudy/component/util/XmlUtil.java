//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package top.atstudy.component.util;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlUtil {
    public XmlUtil() {
    }

    public static String convertToXml(Object obj) {
        StringWriter sw = new StringWriter();

        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            marshaller.setProperty("jaxb.encoding", "UTF-8");
            marshaller.marshal(obj, sw);
        } catch (JAXBException var4) {
            var4.printStackTrace();
        }

        return sw.toString();
    }

    public static void convertToXml(Object obj, String path) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            marshaller.setProperty("jaxb.encoding", "GBK");
            FileWriter fw = null;

            try {
                fw = new FileWriter(path);
            } catch (IOException var6) {
                var6.printStackTrace();
            }

            marshaller.marshal(obj, fw);
        } catch (JAXBException var7) {
            var7.printStackTrace();
        }

    }

    public static Object convertXmlStrToObject(Class<?> clazz, String xmlStr) {
        Object xmlObject = null;

        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshal = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = unmarshal.unmarshal(sr);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return xmlObject;
    }

    public static Object convertXmlFileToObject(Class<?> clazz, String xmlPath) {
        Object xmlObject = null;

        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            InputStreamReader isr = new InputStreamReader(new FileInputStream(xmlPath), "GBK");
            xmlObject = unmarshaller.unmarshal(isr);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return xmlObject;
    }
}
