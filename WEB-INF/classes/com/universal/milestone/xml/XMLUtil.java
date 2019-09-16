package WEB-INF.classes.com.universal.milestone.xml;

import com.universal.milestone.xml.XMLUtil;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLUtil {
  Document document = null;
  
  public static boolean VERBOSE = true;
  
  public static final String COMPONENT_CODE = "XMLUtil";
  
  public static void log(String s) { if (VERBOSE)
      System.out.println("XMLUtil: " + s);  }
  
  public XMLUtil(String xmlFile) {
    URL url = ClassLoader.getSystemResource(xmlFile);
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      try {
        this.document = builder.parse(new File(url.getPath()));
      } catch (Exception ex) {
        this.document = builder.parse(new File(xmlFile));
      } 
    } catch (SAXException sxe) {
      Exception x = sxe;
      if (sxe.getException() != null)
        x = sxe.getException(); 
      x.printStackTrace();
    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } 
  }
  
  public Document getDocument() { return this.document; }
  
  public String getElementValueByParent(String parentName, String elementName) {
    String elementValue = null;
    NodeList nodeList = this.document.getElementsByTagName(parentName);
    for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      elementValue = getElementValueByName(node, elementName);
      if (elementValue != null)
        break; 
    } 
    return elementValue;
  }
  
  public String getElementValueByParentList(String[] parentList, String elementName) {
    String elementValue = null;
    NodeList nodeList = getNodeListParent(parentList);
    for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      elementValue = getElementValueByName(node, elementName);
      if (elementValue != null)
        break; 
    } 
    if (VERBOSE)
      log("Element Name: " + elementName + "\t Value: " + elementValue); 
    return elementValue;
  }
  
  public ArrayList getElementsValueByParentList(String[] parentList, String elementName) {
    String elementValue = null;
    ArrayList elementValues = new ArrayList();
    NodeList nodeList = getNodeListParent(parentList);
    for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      elementValue = getElementValueByName(node, elementName);
      if (elementValue != null) {
        elementValues.add(elementValue);
        if (VERBOSE)
          log("Element Name: " + elementName + "\t Value: " + elementValue); 
      } 
    } 
    return elementValues;
  }
  
  public String getElementValueByParent(String parentName, String elementName, String defaultValue) {
    String retValue = defaultValue;
    String value = null;
    if (parentName == null || parentName.equals("")) {
      value = getElementValueByName(elementName, defaultValue);
    } else {
      value = getElementValueByParent(parentName, elementName);
    } 
    if (value != null)
      retValue = value; 
    return retValue;
  }
  
  public String getElementValueByParentList(String[] parentList, String elementName, String defaultValue) {
    String retValue = defaultValue;
    String value = null;
    if (parentList == null || parentList[0].equals("")) {
      value = getElementValueByName(elementName, defaultValue);
    } else {
      value = getElementValueByParentList(parentList, elementName);
    } 
    if (value != null)
      retValue = value; 
    return retValue;
  }
  
  public boolean getBooleanValueByParent(String parentName, String elementName, boolean defaultValue) {
    boolean retValue = defaultValue;
    String value = null;
    if (parentName == null || parentName.equals("")) {
      value = getElementValueByName(elementName);
    } else {
      value = getElementValueByParent(parentName, elementName);
    } 
    if (value != null)
      if (value.equalsIgnoreCase("Yes") || value.equalsIgnoreCase("true")) {
        retValue = true;
      } else if (value.equalsIgnoreCase("No") || value.equalsIgnoreCase("false")) {
        retValue = false;
      }  
    return retValue;
  }
  
  public boolean getBooleanValueByParentList(String[] parentList, String elementName, boolean defaultValue) {
    boolean retValue = defaultValue;
    String value = null;
    if (parentList == null || parentList[0].equals("")) {
      value = getElementValueByName(elementName);
    } else {
      value = getElementValueByParentList(parentList, elementName);
    } 
    if (value != null)
      if (value.equalsIgnoreCase("Yes") || value.equalsIgnoreCase("true")) {
        retValue = true;
      } else if (value.equalsIgnoreCase("No") || value.equalsIgnoreCase("false")) {
        retValue = false;
      }  
    return retValue;
  }
  
  public int getIntegerValueByParent(String parentName, String elementName, int defaultValue) {
    int retValue = defaultValue;
    try {
      if (parentName == null || parentName.equals("")) {
        retValue = Integer.parseInt(getElementValueByName(elementName));
      } else {
        retValue = Integer.parseInt(getElementValueByParent(parentName, elementName));
      } 
    } catch (Exception exception) {}
    return retValue;
  }
  
  public int getIntegerValueByParentList(String[] parentList, String elementName, int defaultValue) {
    int retValue = defaultValue;
    try {
      if (parentList == null || parentList[0].equals("")) {
        retValue = Integer.parseInt(getElementValueByName(elementName));
      } else {
        retValue = Integer.parseInt(getElementValueByParentList(parentList, elementName));
      } 
    } catch (Exception exception) {}
    return retValue;
  }
  
  public String getElementValueByName(String elementName) {
    Element node = this.document.getDocumentElement();
    return getElementValueByName(node, elementName);
  }
  
  public String getElementValueByName(String elementName, String defaultValue) {
    String retValue = defaultValue;
    String value = getElementValueByName(elementName);
    if (value != null)
      retValue = value; 
    return retValue;
  }
  
  public boolean getBooleanValueByName(String elementName, boolean defaultValue) {
    boolean retValue = defaultValue;
    String value = getElementValueByName(elementName);
    if (value != null)
      if (value.equalsIgnoreCase("Yes") || value.equalsIgnoreCase("true")) {
        retValue = true;
      } else if (value.equalsIgnoreCase("No") || value.equalsIgnoreCase("false")) {
        retValue = false;
      }  
    return retValue;
  }
  
  public int getIntegerValueByName(String elementName, int defaultValue) {
    int retValue = defaultValue;
    try {
      retValue = Integer.parseInt(getElementValueByName(elementName));
    } catch (Exception exception) {}
    return retValue;
  }
  
  public static String getElementValueByName(Node node, String elementName) {
    String elementValue = null;
    boolean foundElement = false;
    try {
      if (!node.getNodeName().equals(elementName))
        if (node.hasChildNodes()) {
          NodeList list = node.getChildNodes();
          for (int n = 0; n < list.getLength(); n++) {
            if (list.item(n).getNodeName().equals(elementName)) {
              node = list.item(n);
              foundElement = true;
              break;
            } 
            if (node.hasChildNodes()) {
              elementValue = getElementValueByName(list.item(n), elementName);
              if (elementValue != null)
                break; 
            } 
          } 
        }  
      if (elementValue == null)
        if (node.getNodeName().equals(elementName)) {
          NodeList list = node.getChildNodes();
          for (int n = 0; node != null && node.hasChildNodes() && n < list.getLength(); n++) {
            node = list.item(n);
            if (node instanceof org.w3c.dom.Text) {
              elementValue = node.getNodeValue();
              break;
            } 
          } 
        }  
    } catch (Exception e) {
      System.out.println("Exception in getElementValueByName: " + e);
    } catch (Throwable e) {
      System.out.println("Throwable error in getElementValueByName: " + e);
    } finally {}
    return elementValue;
  }
  
  public NodeList getNodeListParent(String[] parentList) {
    int parentLevel = 0;
    NodeList nodeList = this.document.getElementsByTagName(parentList[parentLevel]);
    return getNodeListChildren(nodeList, parentList, parentLevel);
  }
  
  public static NodeList getNodeListChildren(NodeList nodeList, String[] parentList, int parentLevel) {
    NodeList retNodeList = null;
    for (int i = 0; i < nodeList.getLength() && parentLevel < parentList.length; i++) {
      Node node = nodeList.item(i);
      if (node.getNodeName().equals(parentList[parentLevel])) {
        if (parentLevel == parentList.length - 1) {
          retNodeList = node.getChildNodes();
          break;
        } 
        retNodeList = getNodeListChildren(node.getChildNodes(), parentList, ++parentLevel);
        if (retNodeList != null)
          break; 
      } 
    } 
    return retNodeList;
  }
  
  public static boolean isVERBOSE() { return VERBOSE; }
  
  public static void setVERBOSE(boolean verbose) { VERBOSE = verbose; }
}


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\xml\XMLUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */