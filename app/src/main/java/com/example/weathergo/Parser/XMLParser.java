package com.example.weathergo.Parser;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {
    // class này có nhiệm vụ chuyển từ xml sang tài kueeyh
    public Document getDocument(String xml) throws IOException {
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
        }
        InputSource inputSource = new InputSource();
        inputSource.setCharacterStream(new StringReader(xml));
        inputSource.setEncoding("UTF-8");
        try {
            document = builder.parse(inputSource);
        } catch (SAXException e) {
            Log.e("Error SAXException: ", e.getMessage());
        }
        return document;
    }
    public String getValue(Element item, String name){
        NodeList nodeList = item.getElementsByTagName(name);
        return this.getTextNodeValue(nodeList.item(0));
    }

    private final String getTextNodeValue(Node node){
        Node child;
        if (node!=null){
            if (node.hasChildNodes()){
                for (child = node.getFirstChild(); child!=null; child = child.getNextSibling()){
                    if (child.getNodeType() == Node.TEXT_NODE){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}
