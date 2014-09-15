package com.falconraptor.utilities.files;

import com.falconraptor.utilities.logger.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class XML {
    private final String log = "[com.falconraptor.utilities.files.XML.";
    public ArrayList<Element> elements = new ArrayList<>(0);
    private Document document;

    public NodeList readXMLNode(String filename) {
        try {
            File fXmlFile = new File(filename);
            if (!fXmlFile.exists()) {
                Logger.logERROR(filename + " does not exist!");
                return null;
            }
            Logger.logINFO("Reading XML File: " + filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName(doc.getDocumentElement().getNodeName());
            Logger.logINFO("Done Reading XML File: " + filename);
            return nodeList;
        } catch (Exception e) {
            Logger.logERROR(log + "readXML] " + e);
            return null;
        }
    }

    public Document readXMLDoc(String filename) {
        try {
            File fXmlFile = new File(filename);
            if (!fXmlFile.exists()) {
                Logger.logERROR(filename + " does not exist!");
                return null;
            }
            Logger.logINFO("Reading XML File: " + filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            Logger.logINFO("Done Reading XML File: " + filename);
            return doc;
        } catch (Exception e) {
            Logger.logERROR(log + "readXML] " + e);
            return null;
        }
    }

    public void setNewFile() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            document = docBuilder.newDocument();
            Logger.logINFO("New File Set");
        } catch (Exception e) {
            Logger.logERROR(log + "createNewFile] " + e);
        }
    }

    public void addElement(String name) {
        elements.add(document.createElement(name));
        Logger.logINFO("Added Element: " + name);
    }

    public void appendElement(int addTo, int element) {
        try {
            elements.get(addTo).appendChild(elements.get(element));
            Logger.logINFO("Added " + elements.get(element).getTagName() + " to " + elements.get(addTo).getTagName());
        } catch (Exception e) {
            Logger.logERROR(log + "appendElement] " + e);
        }
    }

    public void appendElement(int addTo, Text element) {
        try {
            elements.get(addTo).appendChild(element);
            Logger.logINFO("Added String " + element.getWholeText() + " to " + elements.get(addTo).getTagName());
        } catch (Exception e) {
            Logger.logERROR(log + "appendElement] " + e);
        }
    }

    public void appendToDoc(int element) {
        try {
            document.appendChild(elements.get(element));
            Logger.logINFO("Added " + elements.get(element) + " to Document");
        } catch (Exception e) {
            Logger.logERROR(log + "appendToDoc] " + e);
        }
    }

    public void setAttribute(int element, String attribute, String value) {
        try {
            elements.get(element).setAttribute(attribute, value);
            Logger.logINFO("Added Attribute " + attribute + " with value of " + value + " to element " + elements.get(element).getTagName());
        } catch (Exception e) {
            Logger.logERROR(log + "setAttribute] " + e);
        }
    }

    public void saveFile(String filename) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
            Logger.logINFO("XML File: " + filename + " saved");
            fixOrganization(filename);
        } catch (Exception e) {
            Logger.logERROR(log + "saveFile] " + e);
        }
    }

    public void addTextToElement(int element, String text) {
        try {
            appendElement(element, document.createTextNode(text));
        } catch (Exception e) {
            Logger.logERROR(log + "addTextToElement] " + e);
        }
    }

    public void fixOrganization(String filename) {
        Read.Read(filename);
        String text = Read.read();
        text = text.replaceAll("><", ">!!!!<");
        text = text.replaceAll("↔", "");
        String[] text2 = text.split("!!!!");
        ArrayList<String> textarraylist = new ArrayList<>(0);
        int tabs = -2;
        String temp = " ";
        for (String s : text2) {
            if (s.indexOf("/") == -1 || temp.indexOf("/") == -1) tabs += 1;
            else if (s.substring(0, 2).equals("</")) tabs -= 1;
            for (int i = 0; i < tabs; i++) s = "     " + s;
            textarraylist.add(s);
            temp = s;
        }
        Write.Write(filename, false);
        Write.write(textarraylist);
        Write.close();
        Logger.logINFO("File Organized");
    }
}
