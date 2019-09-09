/*     */ package WEB-INF.classes.com.universal.milestone.xml;
/*     */ 
/*     */ import com.universal.milestone.xml.XMLUtil;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLUtil
/*     */ {
/*  37 */   Document document = null;
/*     */   
/*     */   public static boolean VERBOSE = true;
/*     */   public static final String COMPONENT_CODE = "XMLUtil";
/*     */   
/*  42 */   public static void log(String s) { if (VERBOSE) System.out.println("XMLUtil: " + s);
/*     */      }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLUtil(String xmlFile) {
/*  49 */     URL url = ClassLoader.getSystemResource(xmlFile);
/*     */     
/*  51 */     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  57 */       DocumentBuilder builder = factory.newDocumentBuilder();
/*     */       try {
/*  59 */         this.document = builder.parse(new File(url.getPath()));
/*     */       }
/*  61 */       catch (Exception ex) {
/*  62 */         this.document = builder.parse(new File(xmlFile));
/*     */       }
/*     */     
/*     */     }
/*  66 */     catch (SAXException sxe) {
/*     */       
/*  68 */       Exception x = sxe;
/*  69 */       if (sxe.getException() != null)
/*  70 */         x = sxe.getException(); 
/*  71 */       x.printStackTrace();
/*     */     }
/*  73 */     catch (ParserConfigurationException pce) {
/*     */       
/*  75 */       pce.printStackTrace();
/*     */     }
/*  77 */     catch (IOException ioe) {
/*     */       
/*  79 */       ioe.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public Document getDocument() { return this.document; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementValueByParent(String parentName, String elementName) {
/*  94 */     String elementValue = null;
/*     */     
/*  96 */     NodeList nodeList = this.document.getElementsByTagName(parentName);
/*     */     
/*  98 */     for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
/*     */       
/* 100 */       Node node = nodeList.item(i);
/* 101 */       elementValue = getElementValueByName(node, elementName);
/* 102 */       if (elementValue != null)
/*     */         break; 
/*     */     } 
/* 105 */     return elementValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementValueByParentList(String[] parentList, String elementName) {
/* 113 */     String elementValue = null;
/*     */ 
/*     */     
/* 116 */     NodeList nodeList = getNodeListParent(parentList);
/*     */ 
/*     */     
/* 119 */     for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
/*     */       
/* 121 */       Node node = nodeList.item(i);
/* 122 */       elementValue = getElementValueByName(node, elementName);
/* 123 */       if (elementValue != null) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 128 */     if (VERBOSE) {
/* 129 */       log("Element Name: " + elementName + "\t Value: " + elementValue);
/*     */     }
/* 131 */     return elementValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList getElementsValueByParentList(String[] parentList, String elementName) {
/* 140 */     String elementValue = null;
/* 141 */     ArrayList elementValues = new ArrayList();
/*     */ 
/*     */     
/* 144 */     NodeList nodeList = getNodeListParent(parentList);
/*     */ 
/*     */     
/* 147 */     for (int i = 0; nodeList != null && i < nodeList.getLength(); i++) {
/*     */       
/* 149 */       Node node = nodeList.item(i);
/* 150 */       elementValue = getElementValueByName(node, elementName);
/* 151 */       if (elementValue != null) {
/*     */         
/* 153 */         elementValues.add(elementValue);
/*     */ 
/*     */         
/* 156 */         if (VERBOSE) {
/* 157 */           log("Element Name: " + elementName + "\t Value: " + elementValue);
/*     */         }
/*     */       } 
/*     */     } 
/* 161 */     return elementValues;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementValueByParent(String parentName, String elementName, String defaultValue) {
/* 171 */     String retValue = defaultValue;
/*     */     
/* 173 */     String value = null;
/*     */     
/* 175 */     if (parentName == null || parentName.equals("")) {
/* 176 */       value = getElementValueByName(elementName, defaultValue);
/*     */     } else {
/* 178 */       value = getElementValueByParent(parentName, elementName);
/*     */     } 
/*     */     
/* 181 */     if (value != null) {
/* 182 */       retValue = value;
/*     */     }
/* 184 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementValueByParentList(String[] parentList, String elementName, String defaultValue) {
/* 193 */     String retValue = defaultValue;
/*     */     
/* 195 */     String value = null;
/*     */     
/* 197 */     if (parentList == null || parentList[0].equals("")) {
/* 198 */       value = getElementValueByName(elementName, defaultValue);
/*     */     } else {
/* 200 */       value = getElementValueByParentList(parentList, elementName);
/*     */     } 
/*     */     
/* 203 */     if (value != null) {
/* 204 */       retValue = value;
/*     */     }
/* 206 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBooleanValueByParent(String parentName, String elementName, boolean defaultValue) {
/* 215 */     boolean retValue = defaultValue;
/*     */     
/* 217 */     String value = null;
/*     */     
/* 219 */     if (parentName == null || parentName.equals("")) {
/* 220 */       value = getElementValueByName(elementName);
/*     */     } else {
/* 222 */       value = getElementValueByParent(parentName, elementName);
/*     */     } 
/* 224 */     if (value != null)
/*     */     {
/* 226 */       if (value.equalsIgnoreCase("Yes") || value.equalsIgnoreCase("true")) {
/* 227 */         retValue = true;
/* 228 */       } else if (value.equalsIgnoreCase("No") || value.equalsIgnoreCase("false")) {
/* 229 */         retValue = false;
/*     */       } 
/*     */     }
/* 232 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBooleanValueByParentList(String[] parentList, String elementName, boolean defaultValue) {
/* 240 */     boolean retValue = defaultValue;
/*     */     
/* 242 */     String value = null;
/*     */     
/* 244 */     if (parentList == null || parentList[0].equals("")) {
/* 245 */       value = getElementValueByName(elementName);
/*     */     } else {
/* 247 */       value = getElementValueByParentList(parentList, elementName);
/*     */     } 
/* 249 */     if (value != null)
/*     */     {
/* 251 */       if (value.equalsIgnoreCase("Yes") || value.equalsIgnoreCase("true")) {
/* 252 */         retValue = true;
/* 253 */       } else if (value.equalsIgnoreCase("No") || value.equalsIgnoreCase("false")) {
/* 254 */         retValue = false;
/*     */       } 
/*     */     }
/* 257 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIntegerValueByParent(String parentName, String elementName, int defaultValue) {
/* 266 */     int retValue = defaultValue;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 271 */       if (parentName == null || parentName.equals("")) {
/* 272 */         retValue = Integer.parseInt(getElementValueByName(elementName));
/*     */       } else {
/* 274 */         retValue = Integer.parseInt(getElementValueByParent(parentName, elementName));
/*     */       } 
/* 276 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 281 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIntegerValueByParentList(String[] parentList, String elementName, int defaultValue) {
/* 289 */     int retValue = defaultValue;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 294 */       if (parentList == null || parentList[0].equals("")) {
/* 295 */         retValue = Integer.parseInt(getElementValueByName(elementName));
/*     */       } else {
/* 297 */         retValue = Integer.parseInt(getElementValueByParentList(parentList, elementName));
/*     */       } 
/* 299 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementValueByName(String elementName) {
/* 314 */     Element node = this.document.getDocumentElement();
/* 315 */     return getElementValueByName(node, elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementValueByName(String elementName, String defaultValue) {
/* 324 */     String retValue = defaultValue;
/*     */     
/* 326 */     String value = getElementValueByName(elementName);
/* 327 */     if (value != null) {
/* 328 */       retValue = value;
/*     */     }
/* 330 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBooleanValueByName(String elementName, boolean defaultValue) {
/* 338 */     boolean retValue = defaultValue;
/*     */     
/* 340 */     String value = getElementValueByName(elementName);
/* 341 */     if (value != null)
/*     */     {
/* 343 */       if (value.equalsIgnoreCase("Yes") || value.equalsIgnoreCase("true")) {
/* 344 */         retValue = true;
/* 345 */       } else if (value.equalsIgnoreCase("No") || value.equalsIgnoreCase("false")) {
/* 346 */         retValue = false;
/*     */       } 
/*     */     }
/* 349 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIntegerValueByName(String elementName, int defaultValue) {
/* 358 */     int retValue = defaultValue;
/*     */ 
/*     */     
/*     */     try {
/* 362 */       retValue = Integer.parseInt(getElementValueByName(elementName));
/*     */     }
/* 364 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 369 */     return retValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getElementValueByName(Node node, String elementName) {
/* 386 */     String elementValue = null;
/* 387 */     boolean foundElement = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 393 */       if (!node.getNodeName().equals(elementName))
/*     */       {
/* 395 */         if (node.hasChildNodes()) {
/*     */           
/* 397 */           NodeList list = node.getChildNodes();
/* 398 */           for (int n = 0; n < list.getLength(); n++) {
/*     */             
/* 400 */             if (list.item(n).getNodeName().equals(elementName)) {
/* 401 */               node = list.item(n);
/* 402 */               foundElement = true;
/*     */ 
/*     */               
/*     */               break;
/*     */             } 
/*     */ 
/*     */             
/* 409 */             if (node.hasChildNodes()) {
/* 410 */               elementValue = getElementValueByName(list.item(n), elementName);
/* 411 */               if (elementValue != null) {
/*     */                 break;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 418 */       if (elementValue == null)
/*     */       {
/*     */ 
/*     */         
/* 422 */         if (node.getNodeName().equals(elementName)) {
/*     */ 
/*     */ 
/*     */           
/* 426 */           NodeList list = node.getChildNodes();
/*     */           
/* 428 */           for (int n = 0; node != null && node.hasChildNodes() && n < list.getLength(); n++) {
/*     */             
/* 430 */             node = list.item(n);
/*     */ 
/*     */ 
/*     */             
/* 434 */             if (node instanceof org.w3c.dom.Text) {
/* 435 */               elementValue = node.getNodeValue();
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 442 */     } catch (Exception e) {
/* 443 */       System.out.println("Exception in getElementValueByName: " + e);
/*     */     }
/* 445 */     catch (Throwable e) {
/* 446 */       System.out.println("Throwable error in getElementValueByName: " + e);
/*     */     } finally {}
/*     */     
/* 449 */     return elementValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeList getNodeListParent(String[] parentList) {
/* 460 */     int parentLevel = 0;
/* 461 */     NodeList nodeList = this.document.getElementsByTagName(parentList[parentLevel]);
/* 462 */     return getNodeListChildren(nodeList, parentList, parentLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NodeList getNodeListChildren(NodeList nodeList, String[] parentList, int parentLevel) {
/* 474 */     NodeList retNodeList = null;
/* 475 */     for (int i = 0; i < nodeList.getLength() && parentLevel < parentList.length; i++) {
/*     */       
/* 477 */       Node node = nodeList.item(i);
/*     */       
/* 479 */       if (node.getNodeName().equals(parentList[parentLevel])) {
/*     */ 
/*     */         
/* 482 */         if (parentLevel == parentList.length - 1) {
/*     */           
/* 484 */           retNodeList = node.getChildNodes();
/*     */           
/*     */           break;
/*     */         } 
/* 488 */         retNodeList = getNodeListChildren(node.getChildNodes(), parentList, ++parentLevel);
/* 489 */         if (retNodeList != null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/* 494 */     return retNodeList;
/*     */   }
/*     */ 
/*     */   
/* 498 */   public static boolean isVERBOSE() { return VERBOSE; }
/*     */ 
/*     */ 
/*     */   
/* 502 */   public static void setVERBOSE(boolean verbose) { VERBOSE = verbose; }
/*     */ }


/* Location:              D:\Documents\NetBeansProjects\milestone2 Prod.war!\WEB-INF\classes\co\\universal\milestone\xml\XMLUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */