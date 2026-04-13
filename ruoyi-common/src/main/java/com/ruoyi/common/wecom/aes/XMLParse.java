package com.ruoyi.common.wecom.aes;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

class XMLParse
{
    static Object[] extract(String xml) throws AesException
    {
        try
        {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
            dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(xml);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);
            Element root = document.getDocumentElement();
            NodeList nodelist1 = root.getElementsByTagName("Encrypt");
            String encrypt = nodelist1.item(0).getTextContent();
            return new Object[] { 0, encrypt };
        }
        catch (Exception e)
        {
            throw new AesException(AesException.ParseXmlError);
        }
    }

    static String generate(String encrypt, String signature, String timestamp, String nonce)
    {
        return "<xml>\n" + "<Encrypt><![CDATA[" + encrypt + "]]></Encrypt>\n"
                + "<MsgSignature><![CDATA[" + signature + "]]></MsgSignature>\n"
                + "<TimeStamp>" + timestamp + "</TimeStamp>\n"
                + "<Nonce><![CDATA[" + nonce + "]]></Nonce>\n" + "</xml>";
    }
}
